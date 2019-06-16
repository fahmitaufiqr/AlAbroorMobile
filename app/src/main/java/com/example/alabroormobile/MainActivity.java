package com.example.alabroormobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alabroormobile.HomeMenu.Kegiatan.KegiatanActivity;
import com.example.alabroormobile.HomeMenu.Ramadhan.RamadhanActivity;
import com.example.alabroormobile.model.UserModel;
import com.example.alabroormobile.HomeMenu.ArahKiblat.ArahKiblatActivity;
import com.example.alabroormobile.HomeMenu.PengurusDKM.PengurusDKMActivity;
import com.example.alabroormobile.HomeMenu.InfoActivity;
import com.example.alabroormobile.HomeMenu.JadwalPetugas.JadwalPetugasActivity;
import com.example.alabroormobile.HomeMenu.ProfileActivity;
import com.example.alabroormobile.HomeMenu.WaktuShalatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private long backPressedTime;
    private ProgressDialog loading;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    private LinearLayout jadwalSholatbt, jadwalPetugasbt, jadwalAcarabt, statistikPetugasbt, arahKiblatbt, strukturDkmbt, profilebt, aboutbt;
    private TextView tv_tanggal, tv_nama_pengguna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Al-Ab'roor Mobile");

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        loading = ProgressDialog.show(MainActivity.this,
                null,
                "Mengambil Data...",
                true,
                false);

        //inisialisasi
        jadwalSholatbt = findViewById(R.id.jadwalSholat);
        jadwalPetugasbt = findViewById(R.id.jadwalPetugas);
        jadwalAcarabt = findViewById(R.id.jadwalAcara);
        statistikPetugasbt = findViewById(R.id.statistikPetugas);
        arahKiblatbt = findViewById(R.id.arahKiblat);
        strukturDkmbt = findViewById(R.id.strukturDkm);
        profilebt = findViewById(R.id.profile);
        aboutbt = findViewById(R.id.about);
        tv_tanggal = findViewById(R.id.tv_tanggal);
        tv_nama_pengguna = (TextView) findViewById(R.id.tv_nama_pengguna);

        //SET NAMA PENGGUNA
        DatabaseReference dbuser = FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());
        dbuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                tv_nama_pengguna.setText(user.getName());

                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.dismiss();
            }
        });

        Date Date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("EEEE, dd MMM yyyy");
        String formattanggal = format.format(Date);
        tv_tanggal.setText(formattanggal);

        intent();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Ketuk lagi untuk keluar", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    private void intent(){
        jadwalSholatbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WaktuShalatActivity.class);
                startActivity(intent);
            }
        });

        jadwalPetugasbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, JadwalPetugasActivity.class);
                startActivity(i);
            }
        });

        jadwalAcarabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, KegiatanActivity.class);
                startActivity(intent);
            }
        });

        statistikPetugasbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RamadhanActivity.class);
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
                Intent intent = new Intent(MainActivity.this, PengurusDKMActivity.class);
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
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            super.onKeyDown(keyCode, event);
            return true;
        }
        return false;
    }
}
