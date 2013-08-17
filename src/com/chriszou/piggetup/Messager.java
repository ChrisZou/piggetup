/**
 * 
 */
package com.chriszou.piggetup;

import android.telephony.SmsManager;

/**
 * 
 * SMS sender
 * 
 * @author ZouYong
 * 
 */
public class Messager {

	public static void sendMessage(String receiver, String message) {
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(receiver, null, message, null, null);
	}
	
}
