package com.chriszou.piggetup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmHelper {
    public static void setAlarm(Context context, long time) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(Const.EXTRA_BOOL_MORNING_ALARM, true);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Const.MORNING_ALARM, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    public static void setDelayAlarm(Context context, int delayMinute) {
        long nextAlarm = delayMinute * 60 * 1000 + System.currentTimeMillis();
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(Const.EXTRA_BOOL_MORNING_ALARM, false);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Const.DELAY_ALARM, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, nextAlarm, pendingIntent);
    }
}
