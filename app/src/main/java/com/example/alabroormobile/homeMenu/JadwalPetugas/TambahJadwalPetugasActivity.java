package com.example.alabroormobile.homeMenu.JadwalPetugas;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.alabroormobile.R;
import com.example.alabroormobile.model.JadwalPetugas;
import com.example.alabroormobile.model.Pengurus;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class TambahJadwalPetugasActivity extends AppCompatActivity {

    protected static Calendar myCalendar;
    int year;
    int month;
    int dayOfMonth;
    TextView tv_tanggal_jadwal_petugas;
    Button btn_simpan, btn_batal;
    Spinner sp_muazin_subuh, sp_muazin_dzuhur, sp_muazin_ashar, sp_muazin_maghrib, sp_muazin_isya, sp_imam_subuh, sp_imam_dzuhur, sp_imam_ashar, sp_imam_maghrib, sp_imam_isya;
    private String dataTanggal;
    DatabaseReference databaseReference;
    JadwalPetugas jadwalPetugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jadwal_petugas);
        getSupportActionBar().setTitle("Tambah Jadwal Petugas");

        //inisialisasi
        btn_batal = (Button) findViewById(R.id.btn_batal_tambah_jadwal_petugas);
        btn_simpan = (Button) findViewById(R.id.btn_simpan_tambah_jadwal_petugas);
        tv_tanggal_jadwal_petugas = (TextView) findViewById(R.id.tv_tanggal_jadwal_petugas);
        sp_muazin_subuh = (Spinner) findViewById(R.id.sp_muazin_subuh);
        sp_muazin_dzuhur = (Spinner) findViewById(R.id.sp_muazin_dzuhur);
        sp_muazin_ashar = (Spinner) findViewById(R.id.sp_muazin_ashar);
        sp_muazin_maghrib = (Spinner) findViewById(R.id.sp_muazin_maghrib);
        sp_muazin_isya = (Spinner) findViewById(R.id.sp_muazin_isya);
        sp_imam_subuh = (Spinner) findViewById(R.id.sp_imam_subuh);
        sp_imam_dzuhur= (Spinner) findViewById(R.id.sp_imam_dzuhur);
        sp_imam_ashar = (Spinner) findViewById(R.id.sp_imam_ashar);
        sp_imam_maghrib = (Spinner) findViewById(R.id.sp_imam_maghrib);
        sp_imam_isya= (Spinner) findViewById(R.id.sp_imam_isya);
        jadwalPetugas = new JadwalPetugas();

        //set tanggal
        dataTanggal = getIntent().getExtras().getString("dataTanggal");
        tv_tanggal_jadwal_petugas.setText(dataTanggal);

        //ngambil data untuk spinner
        databaseReference = FirebaseDatabase.getInstance().getReference("Pengurus");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> dkmMasjid = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    Pengurus p = d.getValue(Pengurus.class);
                    dkmMasjid.add(p.getNama());
                }
                ArrayAdapter<String> arrayImam = new ArrayAdapter<String>(TambahJadwalPetugasActivity.this,
                        android.R.layout.simple_dropdown_item_1line, dkmMasjid);
                //set adapter
                sp_muazin_subuh.setAdapter(arrayImam);
                sp_muazin_dzuhur.setAdapter(arrayImam);
                sp_muazin_ashar.setAdapter(arrayImam);
                sp_muazin_maghrib.setAdapter(arrayImam);
                sp_muazin_isya.setAdapter(arrayImam);
                sp_imam_subuh.setAdapter(arrayImam);
                sp_imam_dzuhur.setAdapter(arrayImam);
                sp_imam_ashar.setAdapter(arrayImam);
                sp_imam_maghrib.setAdapter(arrayImam);
                sp_imam_isya.setAdapter(arrayImam);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //button simpan
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference("JadwalPetugas");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        jadwalPetugas.setTanggal(dataTanggal);
                        databaseReference.child(dataTanggal).setValue(jadwalPetugas);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        //button batal
        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}