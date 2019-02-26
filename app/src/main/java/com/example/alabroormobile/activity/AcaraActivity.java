package com.example.alabroormobile.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.alabroormobile.JadwalRamadhanActivity;
import com.example.alabroormobile.R;

public class AcaraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acara);
        getSupportActionBar().setTitle("Acara");

        //inisialisasi
        CardView pengajianBt = (CardView) findViewById(R.id.jadwalPengajian);
        CardView ramadhanBt = (CardView) findViewById(R.id.jadwalRamadhan);

        //CardView PENGAJIAN
        pengajianBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcaraActivity.this,JadwalPengajianActivity.class);
                startActivity(intent);
            }
        });

        //CardView RAMADHAN
        ramadhanBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcaraActivity.this,JadwalRamadhanActivity.class);
                startActivity(intent);
            }
        });

    }
}
