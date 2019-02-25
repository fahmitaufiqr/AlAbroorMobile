package com.example.alabroormobile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alabroormobile.R;

public class JadwalPetugasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_petugas);
        getSupportActionBar().setTitle("Jadwal Petugas");
    }
}
