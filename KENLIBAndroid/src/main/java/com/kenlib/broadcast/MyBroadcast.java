package com.kenlib.broadcast;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.kenlib.service.GPSService;


import android.content.*;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MyBroadcast extends BroadcastReceiver {

	Intent service = null;
	private final String[] blackKeyWord = new String[] { "ken", "chuxiao",
			"jiangjia" };

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {// 开机启动

			service = new Intent(context, GPSService.class);
			context.startService(service);

		}

		if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
			StringBuilder sb = new StringBuilder();
			// 获取Broadcast传递的数据
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object[] pdus = (Object[]) bundle.get("pdus");
				for (Object p : pdus) {
					byte[] pud = (byte[]) p;
					// 声明一个SmsMessage，用于解析短信的byte[]数组
					SmsMessage message = SmsMessage.createFromPdu(pud);
					boolean flag = false;
					for (String str : blackKeyWord) {
						if (message.getMessageBody().contains(str)) {
							// 发现黑名单关键字，则标记为true
							flag = true;
							break;
						}
					}
					if (flag) {
						sb.append("发件人：\n");
						sb.append(message.getOriginatingAddress());
						sb.append("\n发送时间：\n");
						Date date = new Date(message.getTimestampMillis());
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						sb.append(format.format(date));
						sb.append("\n短信内容：\n");
						sb.append(message.getMessageBody());

						Toast.makeText(context, sb.toString(),
								Toast.LENGTH_SHORT).show();
						// 如果存在黑名单关键字内容，停止Broadcast传播
						abortBroadcast();
					}

				}
			}
		}

	}

}
