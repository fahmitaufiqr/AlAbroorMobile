package com.example.alabroormobile.homeMenu.Ramadhan;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.alabroormobile.R;
import com.example.alabroormobile.homeMenu.DaftarPengurus.Pengurus;
import com.example.alabroormobile.homeMenu.JadwalPetugas.TambahJadwalPetugasActivity;
import com.example.alabroormobile.model.JadwalPetugas;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class TambahJadwalRamadhanActivity extends AppCompatActivity {

    TextView tv_tanggal_jadwal_petugas;
    Button btn_simpan, btn_batal;
    Spinner sp_muazin_isya, sp_imam_isya, sp_qultum_isya;
    private String dataTanggal;
    DatabaseReference databaseReference;
    JadwalPetugas jadwalPetugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jadwal_ramadhan);
        getSupportActionBar().setTitle("Tambah Jadwal Ramadhan");
        //inisialisasi
        btn_batal = (Button) findViewById(R.id.btn_batal_tambah_jadwal_petugas1);
        btn_simpan = (Button) findViewById(R.id.btn_simpan_tambah_jadwal_petugas1);
        tv_tanggal_jadwal_petugas = (TextView) findViewById(R.id.tv_tanggal_jadwal_petugas1);
        sp_muazin_isya = (Spinner) findViewById(R.id.sp_muazin_isya1);
        sp_imam_isya= (Spinner) findViewById(R.id.sp_imam_isya1);
        sp_qultum_isya = (Spinner) findViewById(R.id.sp_qultum_isya);
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
                ArrayAdapter<String> arrayImam = new ArrayAdapter<String>(TambahJadwalRamadhanActivity.this,
                        android.R.layout.simple_dropdown_item_1line, dkmMasjid);
                //set adapter
                sp_muazin_isya.setAdapter(arrayImam);
                sp_imam_isya.setAdapter(arrayImam);
                sp_qultum_isya.setAdapter(arrayImam);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //button simpan
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String muazin_isya = sp_muazin_isya.getSelectedItem().toString();
                String imam_isya = sp_imam_isya.getSelectedItem().toString();
                String qultum_isya = sp_qultum_isya.getSelectedItem().toString();

                simpanJadwal("Isya", new JadwalPetugas(
                        dataTanggal,
                        muazin_isya,
                        imam_isya,
                        qultum_isya
                ));
                finish();
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
    //method untuk menyimpan jadwal petugas ke firebase database
    private void simpanJadwal(String waktuShalat, JadwalPetugas jadwalPetugas) {
        DatabaseReference dr= FirebaseDatabase.getInstance().getReference("JadwalPetugas");
        dr.child(dataTanggal)
                .child(waktuShalat)
                .setValue(jadwalPetugas)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
    }
}
