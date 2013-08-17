package com.chriszou.piggetup;

public class PrefsFactory {
    
    public static void setDelayMinute(int minute) {
        PrefsHelper.putInt(Const.PREF_INT_DELAY_MINUTE, minute);
    }

    public static int getDelayMinute(int defValue) {
        return PrefsHelper.getInt(Const.PREF_INT_DELAY_MINUTE, defValue);
    }
    
    public static long getCurrentAlarm(long defValue) {
        return PrefsHelper.getLong(Const.PREF_LONG_CURRENT_ALARM, defValue);
    }
    
    public static void setCurrentAlarm(long currentAlarm) {
        PrefsHelper.putLong(Const.PREF_LONG_CURRENT_ALARM, currentAlarm);
    }
}
