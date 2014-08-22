package com.uricul.lockscreen;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;


public class LockScreenActivity extends Activity {
    private Switch mUnlockSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LockScreenActivity", "onCreate()");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.activity_lock_screen);

        initView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LockScreenActivity", "onPause()");
    }

    @Override
    protected void onResume() {
        super.onPause();
        Log.d("LockScreenActivity", "onResume()");
    }

    private void initView() {
        mUnlockSwitch = (Switch) findViewById(R.id.unlock_switch);
        mUnlockSwitch.setChecked(false);
        mUnlockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("LockScreenActivity", "UnlockSwitch : " + String.valueOf(isChecked));

                if( isChecked ) {
                    //moveTaskToBack(true);
                    finish();
                }
            }
        });
    }

}

