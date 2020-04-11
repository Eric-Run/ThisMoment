package com.dgut.moment.Util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;

import com.dgut.moment.MainApplication;

import java.util.Date;

public class ClockManager {
    private static ClockManager instance = new ClockManager();

    public ClockManager() {
    }

    public static ClockManager getInstance() {
        return instance;
    }

    /**
     * 获取系统闹钟服务
     * @return
     */
    private static AlarmManager getAlarmManager() {
        return (AlarmManager) MainApplication.getContext().getSystemService(Context.ALARM_SERVICE);
    }

    /**
     * 取消闹钟
     * @param pendingIntent
     */
    public void cancelAlarm(PendingIntent pendingIntent) {
        getAlarmManager().cancel(pendingIntent);
    }

    /**
     * 添加闹钟
     * @param pendingIntent 执行动作
     * @param performTime  执行时间
     */
    public void addAlarm(PendingIntent pendingIntent, Date performTime) {
        Log.d("Manager","set the alarm");
        cancelAlarm(pendingIntent);
        Log.d("Manager","set the alarm2");

        getAlarmManager().set(AlarmManager.RTC_WAKEUP, performTime.getTime(), pendingIntent);
    }
}
