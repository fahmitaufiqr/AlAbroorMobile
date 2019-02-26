package com.example.alabroormobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashscreenActivity extends Activity {
    private static int LamaTampilSplash = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent apasih = new Intent(SplashscreenActivity.this, LoginActivity.class);
                startActivity(apasih);

                this.finish();
            }

            private void finish(){

            }
        }, LamaTampilSplash);
    }
}
