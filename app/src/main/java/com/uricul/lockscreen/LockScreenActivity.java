package com.uricul.lockscreen;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.lang.reflect.Field;


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

        ApplyTransparentStatusBar();
    }

    int ResolveTransparentStatusBarFlag() {
        String[] libs = getPackageManager().getSystemSharedLibraryNames();
        String reflect = null;

        if( libs == null )
            return 0;

        for( String lib : libs ) {
            if( lib.equals("touchwiz") ) {
                Log.d("LockScreenActivity", "ResolveTransparentStatusBarFlag : reflect:" + lib);
                reflect = "SYSTEM_UI_FLAG_TRANSPARENT_BACKGROUND";
            }
            else if( lib.startsWith("com.sonyericsson.navigationbar") ) {
                Log.d("LockScreenActivity", "ResolveTransparentStatusBarFlag : reflect:" + lib);
                reflect = "SYSTEM_UI_FLAG_TRANSPARENT";
            }
        }

        if (reflect == null)
            return 0;

        try {
            Field field = View.class.getField(reflect);
            if (field.getType() == Integer.TYPE) {
                int result = field.getInt(null);
                Log.d("LockScreenActivity", "ResolveTransparentStatusBarFlag : " + result);
                return result;
            }
        } catch( Exception e )
        {
            Log.d("LockScreenActivity", "ResolveTransparentStatusBarFlag Exception:" + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    void ApplyTransparentStatusBar() {
        Window window = getWindow();
        if( window != null) {
            View decor = window.getDecorView();
            if( decor != null)
                decor.setSystemUiVisibility(ResolveTransparentStatusBarFlag());
        }
    }
}

