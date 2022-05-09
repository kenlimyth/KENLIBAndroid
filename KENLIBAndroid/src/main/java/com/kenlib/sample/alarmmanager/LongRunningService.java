package com.kenlib.sample.alarmmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Date;

import androidx.annotation.Nullable;

public class LongRunningService extends Service {
    private static final String TAG = "LongRunningService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: executed at " + new Date().toString());
            }
        }).start();

        System.out.println("执行相关工作---------------------");

        AlarmManagerUtil.getInstance(getApplicationContext()).getUpAlarmManagerWorkOnOthers();
        return super.onStartCommand(intent, flags, startId);
    }
}
