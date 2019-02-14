package com.example.alabroormobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class AcaraMasjidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acara_masjid);

        //inisialisasi
        CardView pengajianBt = (CardView) findViewById(R.id.jadwalPengajian);
        CardView ramadhanBt = (CardView) findViewById(R.id.jadwalRamadhan);

        //CardView PENGAJIAN
        pengajianBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcaraMasjidActivity.this,JadwalPengajian.class);
                startActivity(intent);
            }
        });

        //CardView RAMADHAN
        ramadhanBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcaraMasjidActivity.this,JadwalRamadhan.class);
                startActivity(intent);
            }
        });

    }
}
