package com.example.alabroormobile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alabroormobile.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView tv_status, tv_nama_user, tv_telp;
    private ImageView iv_foto_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profil");
    }
}
