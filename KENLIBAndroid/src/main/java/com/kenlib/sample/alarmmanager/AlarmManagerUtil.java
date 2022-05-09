package com.kenlib.sample.alarmmanager;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

/**
 * AlarmManager
 * <p>
 * Android中的定时任务一般有两种实现方式，
 * 一种是使用 Java API 里提供的 Timer 类，
 * 一种是使用 Android 的 Alarm 机制。
 * 这两种方式在多数情况下都能实现类似的效果，但 Timer 有一个明显的短板，它并不适用于那些需要长期在后台运行的定时任务。
 * 我们都知道，为了能让电池更加耐用，每种手机都会有自己的休眠策略，Android 手机就会在长时间不操作的情况下自动让 CPU 进入到睡眠状态，这
 * 就有可能导致 Timer 中的定时任务无法正常运行。而 Alarm 则具有唤醒 CPU 的功能
 * <p>
 * AlarmManager.RTC_WAKEUP|表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用绝对时间
 */
public class AlarmManagerUtil {

    //闹钟执行任务的时间间隔，经测试最小间隔不可以小于5，如果小于5秒，系统也会按5秒执行。
    private static final long TIME_INTERVAL = 1000 * 6;
    private Context context;
    public static AlarmManager am;
    public static PendingIntent pendingIntent;
    private Calendar calendar;

    private AlarmManagerUtil(Context aContext) {
        this.context = aContext;
    }

    //单例
    private static AlarmManagerUtil instance = null;

    public static AlarmManagerUtil getInstance(Context aContext) {
        if (instance == null) {
            synchronized (AlarmManagerUtil.class) {
                if (instance == null) {
                    instance = new AlarmManagerUtil(aContext);
                }
            }
        }
        return instance;
    }

    public void createGetUpAlarmManager() {
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, LongRunningService.class);
        //每隔N秒 启动一次服务
        pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        // 启动一个广播
        // Intent i = new Intent(this, AlarmReceiver.class);
        // PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
    }

    @SuppressLint("NewApi")
    public void getUpAlarmManagerStartWork() {

        calendar = Calendar.getInstance();

        // 设定开始的时间
//        calendar.set(Calendar.HOUR_OF_DAY,23);
//        calendar.set(Calendar.MINUTE,50);
//        calendar.set(Calendar.SECOND,00);

        // 给当前时间加上若干秒后执行
        calendar.add(Calendar.SECOND, 2);

        //版本适配 System.currentTimeMillis()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// 6.0及以上
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// 4.4及以上
            am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    pendingIntent);
        } else {
            am.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), TIME_INTERVAL, pendingIntent);
        }
    }

    @SuppressLint("NewApi")
    public void getUpAlarmManagerWorkOnOthers() {
        //高版本重复设置闹钟达到低版本中setRepeating相同效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// 6.0及以上
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + TIME_INTERVAL, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// 4.4及以上
            am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + TIME_INTERVAL, pendingIntent);
        }
    }
}
