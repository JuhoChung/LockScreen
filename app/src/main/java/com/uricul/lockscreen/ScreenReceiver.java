package com.uricul.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if( intent.getAction().equals(Intent.ACTION_SCREEN_OFF) ) {
            Intent  newIntent = new Intent(context, LockScreenActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(newIntent);
        }
    }
}

