package com.example.alabroormobile.homeMenu.Acara;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.alabroormobile.R;
import com.example.alabroormobile.homeMenu.Acara.JadwalPengajian.JadwalPengajianActivity;
import com.example.alabroormobile.homeMenu.Acara.Ramadhan.RamadhanActivity;

/**
 * Created by Aulia Ikvanda on 31,March,2019
 */

public class AcaraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acara);
        getSupportActionBar().setTitle("Pengajian");

        //inisialisasi
        CardView pengajianBt = (CardView) findViewById(R.id.jadwalPengajian);
        CardView ramadhanBt = (CardView) findViewById(R.id.jadwalRamadhan);

        //CardView PENGAJIAN
        pengajianBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcaraActivity.this, JadwalPengajianActivity.class);
                startActivity(intent);
            }
        });

        //CardView RAMADHAN
        ramadhanBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcaraActivity.this, RamadhanActivity.class);
                startActivity(intent);
            }
        });
    }
}