package com.example.alabroormobile.HomeMenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alabroormobile.R;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().setTitle("Tentang Aplikasi");
    }
}
