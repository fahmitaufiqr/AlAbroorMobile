package com.example.alabroormobile.homeMenu.Ramadhan;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.alabroormobile.R;
import com.example.alabroormobile.model.JadwalPetugas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class JadwalRamadhanActivity extends AppCompatActivity {

    private TextView tv_muazin5, tv_imam5, tv_qultum5;
    private CalendarView cv_jadwal_petugas;
    private String sendTanggal;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_jadwal_petugas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.tambah_jadwal_petugas) {
            Intent intent = new Intent(JadwalRamadhanActivity.this, TambahJadwalRamadhanActivity.class);
            intent.putExtra("dataTanggal", sendTanggal);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_ramadhan);
        getSupportActionBar().setTitle("Jadwal Ramadhan");

        Calendar cal = java.util.Calendar.getInstance();
        sendTanggal = cal.get(Calendar.DAY_OF_MONTH) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.YEAR);
        showDataPetugass(sendTanggal);

        //inisialisasi
        cv_jadwal_petugas = (CalendarView) findViewById(R.id.cv_jadwal_petugas);
        tv_muazin5 = (TextView) findViewById(R.id.tv_muazin15);
        tv_imam5 = (TextView) findViewById(R.id.tv_imam15);
        tv_qultum5 = (TextView) findViewById(R.id.tv_qultum5);

        cv_jadwal_petugas.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                sendTanggal = dayOfMonth + "-" + (month+1) + "-" + year;
                showDataPetugass(sendTanggal);
            }
        });
    }

    private void showDataPetugass(String date) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("JadwalPetugas").child(date);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<String> muazin = new ArrayList<>();
                    ArrayList<String> imam = new ArrayList<>();
                    ArrayList<String> qultum = new ArrayList<>();

                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        JadwalPetugas jadwalPetugas = d.getValue(JadwalPetugas.class);
                        muazin.add(jadwalPetugas.getMuazin());
                        imam.add(jadwalPetugas.getImam());
                        qultum.add(jadwalPetugas.getQultum());
                    }
                    if (muazin.size() == 1) {
                        tv_muazin5.setText("Muazin\t: " + muazin.get(0));
                        tv_imam5.setText("Imam\t\t: " + imam.get(0));
                        tv_qultum5.setText("Qultum\t: " + qultum.get(0));
                    }
                } else {
                    tv_muazin5.setText("Muazin Belum ditetukan");
                    tv_imam5.setText("Imam Belum ditetukan");
                    tv_qultum5.setText("Qultum Belum ditetukan");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
