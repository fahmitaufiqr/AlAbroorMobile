package com.example.alabroormobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.alabroormobile.R;
import com.example.alabroormobile.Login2Activity;

/**
 * Created by Aulia Ikvanda on 31,March,2019
 */

public class SplashscreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashscreenActivity.this, Login2Activity.class));
                finish();
            }
        }, 800);
    }
}
