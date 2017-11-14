package utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import callbacks.SMS_RECEIVER_LISTENER;

/**
 * Created by GT on 5/18/2017.
 */

public class SmsReceiver extends BroadcastReceiver {
    Fragment otp_verification_frg;


    private static SMS_RECEIVER_LISTENER mListener;
    private static String otpApiKey,OTP_Session,mobile;
    private static int TIME_OUT = 4000;


    public SmsReceiver() {
    }
    @Override
    public void onReceive(Context context, final Intent intent) {

        Log.d("jdhjhjh","onReceive");
        if((intent.getAction().equals("android.permission.RECEIVE_SMS"))||(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")))
        {
         //   if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
               /* for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    String messageBody = smsMessage.getMessageBody();
                    Toast.makeText(context, "SOME_ACTION is received"+messageBody, Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, TIME_OUT);
                }*/
           /* }else {
                Log.d("notget","sms not get");
            }*/
            sms_fetch(intent);


        }else{
            Toast.makeText(context, "else  SOME_ACTION is received", Toast.LENGTH_LONG).show();
            sms_fetch(intent);
        }
    }

    private static void sms_fetch(Intent intent) {


        Log.d("hvchbvh", "statusfvfv");

      //  Toast.makeText(, "get intentttttt", Toast.LENGTH_SHORT).show();
        Bundle bundle = intent.getExtras();//
      // smsMessages = null;//

        if (bundle != null) {//
            Object[] pdus = (Object[]) bundle.get("pdus");//
            Log.d("pduslength", ""+pdus.length);
            SmsMessage[] smsMessages = new SmsMessage[pdus.length];
            Log.d("smsmessahegs", String.valueOf(smsMessages[0]));
            Log.d("pdus", String.valueOf(pdus[0]));
            for (int i = 0; i < smsMessages.length; i++) {//
                smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);//
                String sender = smsMessages[i].getOriginatingAddress();//
                Log.d("sender", sender);
                if (sender.contains("TFCTOR") || sender.contains("tfctor")) {//
                    String message = smsMessages[i].getMessageBody();//
                    String otp = extractOTP(message);
                    if (otp != null) {
                        Log.d("oottpp", otp);

                        // shared ptrefrences saved
                        mListener.on_otp_listener(otp);
                    }
                }
            }
        }
        else
        {
            Log.d("byndle","bundle null");
        }
    }

    public static String extractOTP(String sms) {
        String[] nbs = sms.split("\\s+");
        if (nbs.length != 0) {
            for (String number : nbs) {
                if (number.matches("^[0-9]+$")) {
                    return number;
                }
            }
        }
        return null;
    }
    public static void bindListener(SMS_RECEIVER_LISTENER listener) {
        mListener = listener;
        Log.d("bind_listener","bindlistener");
    }

    public interface SmsListener {
        public void messageReceived(String messageText);
    }




}

