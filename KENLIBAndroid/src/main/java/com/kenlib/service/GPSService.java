package com.kenlib.service;

import org.json.JSONException;
import org.json.JSONObject;

//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.baidu.location.LocationClientOption.LocationMode;
import com.kenlib.http.HttpUtil;
import com.kenlib.util.KENConfig;
import com.kenlib.util.Util;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 定位
 *
 */
public class GPSService extends Service {

//	public LocationClient mLocationClient = null;
//	public BDLocationListener myListener = new MyLocationListener();
	public double latitude = 0;
	public double longitude = 0;
	public String naddr = "";

	@Override
	public IBinder onBind(Intent arg0) {
		// 仅通过startService()启动服务，所以这个方法返回null即可。
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// getLocation();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Log.i(TAG, "count ====" + count);
		getLocation();
		up();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// 当service关闭时
		Util.startAM(this, GPSService.class, 1000 * 60 * 5);// 时钟

	}

	private void up() {
		// TODO Auto-generated method stub
		if (latitude == 0 || longitude == 0) {
			Util.showLogDebug("经纬度==0----------------");
			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				upLoad();
			}
		}).start();
	}

	public void upLoad() {// 上传

		try {

			try {
				// 封装json
				JSONObject jsonObject = new JSONObject();

				jsonObject.put("jd", longitude);
				jsonObject.put("wd", latitude);
				jsonObject.put("naddr", naddr);
				jsonObject.put("Tel", Util.getUseridFromSQLiteDB(this));
				String urlString = KENConfig.IP
						+ KENConfig.KI;
				HttpUtil.sendPost(urlString, jsonObject);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getLocation() {// 定位


//		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
//		mLocationClient.registerLocationListener(myListener); // 注册监听函数
//		LocationClientOption option = new LocationClientOption();
//		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
//		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
//		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
//		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
//		option.setProdName("TY");
//		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
//		option.setLocationNotify(true);
//
//		mLocationClient.setLocOption(option);
//		mLocationClient.start();
//
//		if (mLocationClient != null && mLocationClient.isStarted()) {
//			mLocationClient.requestLocation();
//		}

	}

//	public class MyLocationListener implements BDLocationListener {
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//
//			if (location == null)
//				return;
//
//			StringBuffer sb = new StringBuffer(256);
//			sb.append("\ntime : ");
//			sb.append(location.getTime());
//			sb.append("\nerror code : ");
//			sb.append(location.getLocType());
//			sb.append("\nlatitude : ");
//			sb.append(location.getLatitude());
//			sb.append("\nlontitude : ");
//			sb.append(location.getLongitude());
//			sb.append("\nradius : ");
//			sb.append(location.getRadius());
//			if (location.getLocType() == BDLocation.TypeGpsLocation) {
//				sb.append("\nspeed : ");
//				sb.append(location.getSpeed());
//				sb.append("\nsatellite : ");
//				sb.append(location.getSatelliteNumber());
//			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
//				sb.append("\naddr : ");
//				sb.append(location.getAddrStr());
//			}
//
//			//Log.i("ken", "****at onReceiveLocation------------" + sb.toString());
//
//			latitude = location.getLatitude();
//			longitude = location.getLongitude();
//			naddr = location.getAddrStr();
//
//			
//
//		}

//	}

}
