package com.uricul.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if( intent.getAction().equals(Intent.ACTION_SCREEN_OFF) ) {
            Log.d("ScreenReceiver", "Received Intent Action:" + Intent.ACTION_SCREEN_OFF);

            Intent  newIntent = new Intent(context, LockScreenActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(newIntent);
        }
    }
}