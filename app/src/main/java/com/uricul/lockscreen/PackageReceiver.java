package com.uricul.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PackageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if( action.equals(Intent.ACTION_PACKAGE_ADDED) ) {
            Log.d("PackageReceiver", "Received Intent Action:" + Intent.ACTION_PACKAGE_ADDED);

            Intent newIntent = new Intent(context, ScreenService.class);
            context.startService(newIntent);
        } else if( action.equals(Intent.ACTION_PACKAGE_REMOVED) ) {
            Log.d("PackageReceiver", "Received Intent Action:" + Intent.ACTION_PACKAGE_REMOVED);
        } else if( action.equals(Intent.ACTION_PACKAGE_REPLACED) ) {
            Log.d("PackageReceiver", "Received Intent Action:" + Intent.ACTION_PACKAGE_REPLACED);

            Intent newIntent = new Intent(context, ScreenService.class);
            context.startService(newIntent);
        }
    }
}