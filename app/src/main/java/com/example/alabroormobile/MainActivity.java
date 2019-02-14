package com.example.alabroormobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inisialisasi
        CardView jadwalSholatbt = (CardView) findViewById(R.id.jadwalSholat);
        CardView jadwalPetugasbt = (CardView) findViewById(R.id.jadwalPetugas);
        CardView jadwalAcarabt = (CardView) findViewById(R.id.jadwalAcara);
        CardView statistikPetugasbt = (CardView) findViewById(R.id.statistikPetugas);
        CardView arahKiblatbt = (CardView) findViewById(R.id.arahKiblat);
        CardView strukturDkmbt = (CardView) findViewById(R.id.strukturDkm);
        CardView profilebt = (CardView) findViewById(R.id.profile);
        CardView aboutbt = (CardView) findViewById(R.id.about);


        jadwalSholatbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,JadwalSholatActivity.class);
                startActivity(intent);
            }
        });

        jadwalPetugasbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JadwalKesediaanActivity.class);
                startActivity(intent);
            }
        });

        jadwalAcarabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AcaraMasjidActivity.class);
                startActivity(intent);
            }
        });

        statistikPetugasbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AdminStatistikImamActivity.class);
                startActivity(intent);
            }
        });

        arahKiblatbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ArahKiblatActivity.class);
                startActivity(intent);
            }
        });

        strukturDkmbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StrukturDkmActivity.class);
                startActivity(intent);
            }
        });

        profilebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        aboutbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}
