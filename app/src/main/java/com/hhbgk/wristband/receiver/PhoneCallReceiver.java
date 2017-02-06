package com.hhbgk.wristband.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneCallReceiver extends BroadcastReceiver {
    private final String tag = getClass().getSimpleName();
    private static boolean incomingFlag = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        //拨打电话
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            incomingFlag = false;
            final String phoneNum = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.i(tag, "phoneNum: " + phoneNum);
        } else {
            Log.e(tag, "incoming call....");
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    final PhoneStateListener listener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                //电话等待接听
                case TelephonyManager.CALL_STATE_RINGING:
                    incomingFlag = true;
                    Log.i(tag, "CALL IN RINGING :" + incomingNumber);
                    break;
                //电话接听
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (incomingFlag) {
                        Log.i(tag, "CALL IN ACCEPT :" + incomingNumber);
                    }
                    break;
                //电话挂机
                case TelephonyManager.CALL_STATE_IDLE:
                    if (incomingFlag) {
                        incomingFlag = false;
                        Log.i(tag, "CALL IDLE");
                    }
                    break;
            }
        }
    };
}
