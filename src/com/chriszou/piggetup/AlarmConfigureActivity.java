package com.chriszou.piggetup;

import java.util.Calendar;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;
import android.widget.TimePicker;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.alarm_configure)
public class AlarmConfigureActivity  extends Activity {
    @ViewById(R.id.alarm_time)
    TextView mAlarmTimeView;
    
    @ViewById(R.id.timePicker1)
    TimePicker mAlarmTimePicker;
    
    @AfterViews
    void initViews() {
        mAlarmTimePicker.setIs24HourView(true);
        updateAlarmView();
    }
    
    private void updateAlarmView() {
        long currentAlarm = PrefsHelper.getLong(Const.PREF_LONG_CURRENT_ALARM, 0);
        if(currentAlarm==0) {
            mAlarmTimeView.setText("baby还没有设置闹钟哦！");
            mAlarmTimePicker.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        } else {
            while(currentAlarm<System.currentTimeMillis()) {
                currentAlarm = currentAlarm + Const.DAY_DURATION;
            }
            Calendar alarmCalendar = Calendar.getInstance();
            alarmCalendar.setTimeInMillis(currentAlarm);
            int hour = alarmCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = alarmCalendar.get(Calendar.MINUTE); 
            
            Calendar todayCalendar = Calendar.getInstance();
            String timeString = String.format("明天 %02d:%02d", hour, minute);
            if(todayCalendar.get(Calendar.DAY_OF_MONTH)==alarmCalendar.get(Calendar.DAY_OF_MONTH)) {
                timeString = String.format("今天 %02d:%02d", hour, minute);
            } else {
                timeString = String.format("明天 %02d:%02d", hour, minute);
            }
            
            mAlarmTimeView.setText(timeString);
            mAlarmTimePicker.setCurrentHour(hour);
            mAlarmTimePicker.setCurrentMinute(minute);
        }
    }
    
    @Click(R.id.alarm_configure_set) 
    void setAlarm(){
        int hour = mAlarmTimePicker.getCurrentHour();
        int minute = mAlarmTimePicker.getCurrentMinute();
        Log.d("zyzy", "current hour: "+hour+", current minute: "+minute);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        while(calendar.getTimeInMillis()<System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        PrefsHelper.putLong(Const.PREF_LONG_CURRENT_ALARM, calendar.getTimeInMillis());
        AlarmHelper.setAlarm(this, calendar.getTimeInMillis());
        
        updateAlarmView();
    }
    
    @Click(R.id.alarm_configure_remove)
    void removeAlarm() {
        PrefsFactory.setCurrentAlarm(0);
        updateAlarmView();
    }
    
    @Click(R.id.alarm_configure_close)
    void close() {
        finish();
    }
}
