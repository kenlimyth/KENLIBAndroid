package com.kenlib.log;

import android.app.Application;

//用法
//manifest中使用CrashApplication
//<application
//        android:name=".CrashApplication"
//				android:allowBackup="true"
//				...

public class CrashApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
	}
}