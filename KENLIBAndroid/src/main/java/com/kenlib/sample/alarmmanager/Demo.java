package com.kenlib.sample.alarmmanager;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class Demo  extends AppCompatActivity {

    AlarmManagerUtil alarmManagerUtils;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alarmManagerUtils = AlarmManagerUtil.getInstance(this);
        alarmManagerUtils.createGetUpAlarmManager();

        alarmManagerUtils.getUpAlarmManagerStartWork();


    }
}
