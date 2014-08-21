package com.uricul.lockscreen;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class ScreenService extends Service {
    private ScreenReceiver mReceiver = null;

    private static final int RESTART_CHECK_PERIOD = 30 * 60 * 1000;     // 30분

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("ScreenService", "onCreate()");

        mReceiver = new ScreenReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, filter);

        registerRestartAlarm(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if( intent != null ) {
            if( intent.getAction() == null ) {
                if( mReceiver == null ) {
                    mReceiver = new ScreenReceiver();
                    IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
                    registerReceiver(mReceiver, filter);
                }
            }
        }

        return  START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if( mReceiver != null ) {
            unregisterReceiver(mReceiver);
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
}
