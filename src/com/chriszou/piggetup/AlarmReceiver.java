package com.chriszou.piggetup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("zyzy", "alarm receiver on receive");
        Intent intent1 = new Intent(context, AlarmActivity_.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        context.startActivity(intent1);
        
        if(intent.getBooleanExtra(Const.EXTRA_BOOL_MORNING_ALARM, false)) {
            long currentAlarm = PrefsHelper.getLong(Const.PREF_LONG_CURRENT_ALARM, 0);
            if(currentAlarm>0) {
                currentAlarm += Const.DAY_DURATION;
            }
            AlarmHelper.setAlarm(context, currentAlarm);
        }
    }

}
