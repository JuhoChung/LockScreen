package com.uricul.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if( intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED ) ) {
            Log.d("BootReceiver", "Received Intent Action:" + Intent.ACTION_BOOT_COMPLETED);

            Intent  newIntent = new Intent(context, ScreenService.class);
            context.startService(newIntent);
        }
    }
}

