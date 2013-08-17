package com.chriszou.piggetup;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Fullscreen;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.ViewById;

@NoTitle
@Fullscreen
@EActivity(R.layout.activity_main)
public class AlarmActivity extends Activity {

    @ViewById(R.id.main_ok)
    Button mOkButton;

    @ViewById(R.id.delay_minute)
    TextView mDelayMinuteView;

    private int mDelayMinute;

    private SharedPreferences mPreferences;

    private Ringtone mRingtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startRingtone();

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mDelayMinute = mPreferences.getInt(Const.PREF_INT_DELAY_MINUTE, 1);
        
        Messager.sendMessage(Const.CHRIS_NUMBER, "Baby闹钟已经响了");
    }

    @AfterViews
    void updateDelayMinute() {
        mDelayMinuteView.setText(mDelayMinute + "");
    }

    private boolean startRingtone() {
        Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mRingtone = RingtoneManager.getRingtone(this, ringtoneUri);
        if (mRingtone != null) {
            mRingtone.play();
            return true;
        }
        return false;
    }

    private void stopRingtone() {
        if (mRingtone != null && mRingtone.isPlaying()) {
            mRingtone.stop();
        }
    }

    @Click
    void main_ok() {
        Messager.sendMessage(Const.CHRIS_NUMBER, "Baby决定立刻起床");
        
        stopRingtone();
        finish();
    }

    @Click(R.id.increase_minute)
    void increaseDelayMinute() {
        mDelayMinute++;
        PrefsFactory.setDelayMinute(mDelayMinute);
        updateDelayMinute();
    }

    @Click(R.id.decrease_minute)
    void decreaseDelayMinute() {
        if(mDelayMinute<2) {
            return;
        }
        mDelayMinute--;
        PrefsFactory.setDelayMinute(mDelayMinute);
        updateDelayMinute();
    }
    
    @Click(R.id.main_delay)
    void delayAlarm() {
        Messager.sendMessage(Const.CHRIS_NUMBER, "Baby再睡"+mDelayMinute+"分钟");
        
        AlarmHelper.setDelayAlarm(this, mDelayMinute);
        stopRingtone();
        finish();
    }

    @Override
    protected void onDestroy() {
        stopRingtone();
        super.onDestroy();
    }
    
}
