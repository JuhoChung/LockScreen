package com.uricul.lockscreen;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ConfigActivity extends Activity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ConfigActivity", "onCreate()");

        mContext = this;
        setContentView(R.layout.activity_config);

        Button startButton = (Button) findViewById(R.id.start_btn);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ConfigActivity", "startService");
                Intent intent = new Intent(mContext, ScreenService.class);
                startService(intent);
                finish();
            }
        });

        Button stopButton = (Button) findViewById(R.id.stop_btn);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ConfigActivity", "stopService");
                Intent intent = new Intent(mContext, ScreenService.class);
                stopService(intent);
            }
        });

        if( isMyServiceRunning(mContext) ) {
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        } else {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ConfigActivity", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ConfigActivity", "onPause()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isMyServiceRunning(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.uricul.lockscreen.ScreenService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
