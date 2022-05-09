package com.kenlib.version;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.kenlib.util.KENConfig;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * APP升级
 *
 */
public class CheckVersion {

	private static Context context;
	private final int UPDATA_NONEED = 0;
	private final int UPDATA_CLIENT = 1;
	private final int GET_UNDATAINFO_ERROR = 2;
	private final int SDCARD_NOMOUNTED = 3;
	private final int DOWN_ERROR = 4;
	private final String TAG = this.getClass().getName();
	private UpdataInfo info;
	private static String localVersion;
	private static CheckVersionTask checkVersionTask;
	private static checkjg c;
	private static CheckVersion checkVersion = new CheckVersion();

	/**
	 * 开始检测
	 */
	public void check(Context activityc, checkjg checkjg) {
		try {
			context = activityc;
			localVersion = getVersionName(context);
			c = checkjg;
			checkVersionTask = checkVersion.new CheckVersionTask();
			new Thread(checkVersionTask).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 得到实例
	 */
	public static CheckVersion getInstance() {
		// TODO Auto-generated method stub
		return checkVersion;
	}

	/**
	 * 从服务器获取xml解析并进行比对版本号
	 */
	public class CheckVersionTask implements Runnable {

		public void run() {
			try {
				// 从资源文件获取服务器 地址
				String path = KENConfig.IP + KENConfig.version_SERVER_URL;
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(5000);
				InputStream is = conn.getInputStream();
				info = UpdataInfoParser.getUpdataInfo(is);

				if (info.getVersion().equals(localVersion)) {
					Log.i(TAG, "版本号相同无需升级");
					Message msg = new Message();
					msg.what = UPDATA_NONEED;
					handler.sendMessage(msg);
					// LoginMain();
				} else {
					Log.i(TAG, "版本号不同 ,提示用户升级 ");
					Message msg = new Message();
					msg.what = UPDATA_CLIENT;
					handler.sendMessage(msg);
				}
			} catch (Exception e) {
				// 待处理
				Message msg = new Message();
				msg.what = GET_UNDATAINFO_ERROR;
				handler.sendMessage(msg);
				e.printStackTrace();
			}
		}
	}

	Handler handler = new Handler() {// 消息处理

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case UPDATA_NONEED:

				c.noUpate(1);

				break;
			case UPDATA_CLIENT:

				showUpdataDialog();// 升级

				break;
			case GET_UNDATAINFO_ERROR:
				// 服务器超时

				c.error();
				break;
			case SDCARD_NOMOUNTED:
				// sdcard不可用

				c.error();
				break;
			case DOWN_ERROR:
				// 下载apk失败
				c.error();
				break;
			}
		}
	};

	/**
	 * 获取当前程序的版本号
	 */
	public static String getVersionName(Context context) {
		String versionName = "";
		try {
			// 获取packagemanager的实例
			PackageManager packageManager = context.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo;

			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);

			versionName = packInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * 弹出对话框通知用户更新程序
	 */
	protected void showUpdataDialog() {
		AlertDialog.Builder builer = new Builder(context);
		builer.setTitle("新版本上线啦!");
		String geshi= info.getDescription().replace("#", "\n");
//		Utility.showLogTest("geshi="+geshi);
//		Utility.showLogTest("info.getDescription()="+info.getDescription());
		builer.setMessage(geshi);
		//暂不更新
//		builer.setPositiveButton("暂不更新", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				c.noUpate(0);
//			}
//		});
		// 立即更新
		builer.setNegativeButton("立即更新", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				Log.i(TAG, "下载apk,更新");
				downLoadApk();
			}
		});
		AlertDialog dialog = builer.create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();

	}

	/**
	 * 从服务器中下载APK
	 */
	protected void downLoadApk() {
		final ProgressDialog pd; // 进度条对话框
		pd = new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在下载更新");
		pd.setCanceledOnTouchOutside(false);
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Message msg = new Message();
			msg.what = SDCARD_NOMOUNTED;
			handler.sendMessage(msg);
		} else {
			pd.show();
			new Thread() {
				@Override
				public void run() {
					try {
						File file = DownLoadManager.getFileFromServer(
								info.getUrl(), pd);
						sleep(1000);
						installApk(file);
						pd.dismiss(); // 结束掉进度条对话框

					} catch (Exception e) {
						Message msg = new Message();
						msg.what = DOWN_ERROR;
						handler.sendMessage(msg);
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

	/**
	 * 安装apk
	 * */
	protected void installApk(File file) {
		Intent intent = new Intent();
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 对外接口
	 */
	public static interface checkjg {

		void noUpate(int i);

		void error();

	}

}
