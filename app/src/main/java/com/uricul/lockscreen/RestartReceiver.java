package com.uricul.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RestartReceiver extends BroadcastReceiver {
    static public final String ACTION_RESTART_SERVICE = "com.uricul.lockscreen.RESTART_SERVICE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if( intent.getAction().equals(ACTION_RESTART_SERVICE) ) {
            Log.d("RestartReceiver", "Received Intent Action:" + ACTION_RESTART_SERVICE);

            Intent newIntent = new Intent(context, ScreenService.class);
            context.startService(newIntent);
        }
    }
}