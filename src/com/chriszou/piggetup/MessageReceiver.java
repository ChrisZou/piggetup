package com.chriszou.piggetup;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

@SuppressLint("DefaultLocale")
public class MessageReceiver extends BroadcastReceiver {

	static final String TAG = "zyzy";
	static final String smsuri = "android.provider.Telephony.SMS_RECEIVED";
	private static final String TRIGGER_KEY = "CUTESTPIGGETUP";
	private static final String FEEDBACK_RECEIVED = "WAKEUP_MESSAGE_RECEIVED";

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Log.i(TAG, "messagereceiver onreceive");
		if (arg1.getAction().equals(smsuri)) {
			Bundle bundle = arg1.getExtras();
			if (null != bundle) {
				Object[] pdus = (Object[]) bundle.get("pdus");
				SmsMessage[] smg = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++) {
					smg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				}
				for (SmsMessage cursmg : smg) {
					String messageBody = cursmg.getMessageBody();
					String senderNumber = cursmg.getOriginatingAddress();
					Log.i(TAG, messageBody);
					Log.i(TAG, senderNumber);
					
					if(messageBody!=null&&messageBody.toLowerCase().contains(TRIGGER_KEY.toLowerCase())) {
						Log.i(TAG, "contains");
						getUpActivity(arg0);
						sendFeedbackMessage(senderNumber);
						break;
					} else {
						Log.i(TAG, "not contains");
					}
				}
			}
		}
		
		abortBroadcast();
	}
	
	public void getUpActivity(Context context) {
		Intent it = new Intent(context, AlarmActivity.class);
		it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(it);
	}
	
	public void sendFeedbackMessage(String receiverNumber) {
		Messager.sendMessage(receiverNumber, FEEDBACK_RECEIVED);
	}

}
