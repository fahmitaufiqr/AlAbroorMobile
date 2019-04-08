package com.example.alabroormobile.homeMenu.JadwalPetugas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alabroormobile.R;
import com.example.alabroormobile.model.Pengurus;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Calendar;

public class TambahJadwalPetugasActivity extends AppCompatActivity {

    protected static Calendar myCalendar;
    int year;
    int month;
    int dayOfMonth;
    TextView tv_tanggal_jadwal_petugas;
    Button btn_simpan, btn_batal;
    Spinner spiner;
    private String dataTanggal;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jadwal_petugas);
        getSupportActionBar().setTitle("Tambah Jadwal Petugas");

        btn_batal = (Button) findViewById(R.id.btn_batal_tambah_jadwal_petugas);
        btn_simpan = (Button) findViewById(R.id.btn_simpan_tambah_jadwal_petugas);
        tv_tanggal_jadwal_petugas = (TextView) findViewById(R.id.tv_tanggal_jadwal_petugas);
        spiner = (Spinner) findViewById(R.id.sp_muazin_subuh);
        dataTanggal = getIntent().getExtras().getString("dataTanggal");

        tv_tanggal_jadwal_petugas.setText(dataTanggal);


        databaseReference = FirebaseDatabase.getInstance().getReference("Pengurus");
        Toast.makeText(TambahJadwalPetugasActivity.this, "Pengurus", Toast.LENGTH_SHORT).show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> dkmMasjid = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    Pengurus p = d.getValue(Pengurus.class);
                    dkmMasjid.add(p.getNama());
                    Log.d("namaPengurus",""+dkmMasjid.get(0));
                    Toast.makeText(TambahJadwalPetugasActivity.this, "penguruss", Toast.LENGTH_SHORT).show();
                }
                String[] dkmk = {"fjfy"};
                ArrayAdapter<String> arrayImam = new ArrayAdapter<String>(TambahJadwalPetugasActivity.this,
                        android.R.layout.simple_dropdown_item_1line, dkmMasjid);
                spiner.setAdapter(arrayImam);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void spinner(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Pengurus");
        Toast.makeText(TambahJadwalPetugasActivity.this, "Pengurus", Toast.LENGTH_SHORT).show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> dkmMasjid = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    Pengurus p = d.getValue(Pengurus.class);
                    dkmMasjid.add(p.getNama());
                    Log.d("namaPengurus",""+dkmMasjid.get(0));
                    Toast.makeText(TambahJadwalPetugasActivity.this, "penguruss", Toast.LENGTH_SHORT).show();
                }

                //CODE SPINNER
                ArrayAdapter<String> arrayImam = new ArrayAdapter<String>(TambahJadwalPetugasActivity.this,
                        android.R.layout.simple_dropdown_item_1line, dkmMasjid);

                spiner.setAdapter(arrayImam);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
