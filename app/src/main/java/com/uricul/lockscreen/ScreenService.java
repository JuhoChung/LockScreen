package com.uricul.lockscreen;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.Date;

public class ScreenService extends Service {
    private static final int LOCKSCREEN_SERVICE_ID = 1;

    private ScreenReceiver mScreenReceiver = null;
    private PackageReceiver mPackageReceiver = null;

    private static final int RESTART_CHECK_PERIOD = 30 * 60 * 1000;     // 30분

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("ScreenService", "onCreate()");

        mScreenReceiver = new ScreenReceiver();
        IntentFilter screenFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mScreenReceiver, screenFilter);

        mPackageReceiver = new PackageReceiver();
        IntentFilter packageFilter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        packageFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        packageFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        packageFilter.addDataScheme("package");
        registerReceiver(mPackageReceiver, packageFilter);

        registerRestartAlarm(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if( intent != null ) {
            if( intent.getAction() == null ) {
                if( mScreenReceiver == null ) {
                    mScreenReceiver = new ScreenReceiver();
                    IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
                    registerReceiver(mScreenReceiver, filter);
                }
            }
        }

        setNotification();

        return  START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if( mScreenReceiver != null ) {
            unregisterReceiver(mScreenReceiver);
        }

        if( mPackageReceiver != null ) {
            unregisterReceiver(mPackageReceiver);
        }

        registerRestartAlarm(false);
    }

    public void registerRestartAlarm(boolean isOn) {
        Intent intent = new Intent(ScreenService.this, RestartReceiver.class);
        intent.setAction(RestartReceiver.ACTION_RESTART_SERVICE);

        PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        if( isOn ) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1000, RESTART_CHECK_PERIOD, sender);
        } else {
            alarmManager.cancel(sender);
        }
    }

    private void setNotification() {
        String contentTitle = getString(R.string.service_name);
        String contentText = getString(R.string.service_foregroung_running);

        Bitmap icon = ((BitmapDrawable) this.getResources().getDrawable(R.drawable.lockscreen)).getBitmap();
        Notification notification = new Notification.Builder(this)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.lockscreen)
                .setLargeIcon(icon)
                .setTicker(getString(R.string.app_name))
                .setAutoCancel(true)
                .setVibrate(null)
                .setNumber(0)
                .setLights(Color.BLUE, 0, 0)
                .setWhen(new Date().getTime())
                .setContentIntent(null)
                .build();

        startForeground(LOCKSCREEN_SERVICE_ID, notification);
    }
}
