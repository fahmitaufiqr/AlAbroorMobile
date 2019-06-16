package com.example.alabroormobile.HomeMenu.JadwalPetugas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.alabroormobile.model.Pengurus;
import com.example.alabroormobile.R;
import com.example.alabroormobile.model.JadwalPetugas;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class JadwalPetugasActivity extends AppCompatActivity {
    
    private TextView tv_muazin1, tv_imam1, tv_muazin2, tv_imam2, tv_muazin3, tv_imam3, tv_muazin4, tv_imam4, tv_muazin5, tv_imam5, tv_subuh, tv_dzuhur, tv_ashar, tv_maghrib, tv_isya;
    private CalendarView cv_jadwal_petugas;
    private String sendTanggal;
    private FloatingActionButton fab;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_petugas);
        getSupportActionBar().setTitle("Jadwal Petugas");

        //inisialisasi
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        cv_jadwal_petugas = (CalendarView) findViewById(R.id.cv_jadwal_petugas);
        tv_muazin1 = (TextView) findViewById(R.id.tv_muazin1);
        tv_muazin2 = (TextView) findViewById(R.id.tv_muazin2);
        tv_muazin3 = (TextView) findViewById(R.id.tv_muazin3);
        tv_muazin4 = (TextView) findViewById(R.id.tv_muazin4);
        tv_muazin5 = (TextView) findViewById(R.id.tv_muazin5);
        tv_imam1 = (TextView) findViewById(R.id.tv_imam1);
        tv_imam2 = (TextView) findViewById(R.id.tv_imam2);
        tv_imam3 = (TextView) findViewById(R.id.tv_imam3);
        tv_imam4 = (TextView) findViewById(R.id.tv_imam4);
        tv_imam5 = (TextView) findViewById(R.id.tv_imam5);
        tv_subuh = (TextView) findViewById(R.id.tv_subuh);
        tv_dzuhur = (TextView) findViewById(R.id.tv_dzuhur);
        tv_ashar = (TextView) findViewById(R.id.tv_ashar);
        tv_maghrib = (TextView) findViewById(R.id.tv_maghrib);
        tv_isya = (TextView) findViewById(R.id.tv_isya);
        fab = (FloatingActionButton)findViewById(R.id.fab_add_jadwal_petugas);

        //CEK USER ADMIN
        String username = currentUser.getEmail().replace(".", "0").split("@")[0];
        DatabaseReference dbuserA = FirebaseDatabase.getInstance().getReference("Pengurus").child(username);
        dbuserA.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pengurus pengurus = dataSnapshot.getValue(Pengurus.class);

                if (pengurus.getStatus().equals("Admin")){
                    fab.setVisibility(View.VISIBLE);

                }else {
                    fab.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Calendar cal = java.util.Calendar.getInstance();
        sendTanggal = cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH)+ 1) + "-" + cal.get(Calendar.YEAR);
        showDataPetugas(sendTanggal);

        tv_subuh.setText("Subuh");
        tv_dzuhur.setText("Dzuhur");
        tv_ashar.setText("Ashar");
        tv_maghrib.setText("Maghrib");
        tv_isya.setText("Isya");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JadwalPetugasActivity.this, TambahJadwalPetugasActivity.class);
                intent.putExtra("dataTanggal", sendTanggal);
                startActivity(intent);
            }
        });

        cv_jadwal_petugas.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                sendTanggal = dayOfMonth + "-" + (month+1) + "-" + year;
                showDataPetugas(sendTanggal);
            }
        });
    }

    private void showDataPetugas(String date) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("JadwalPetugasBaru").child(date);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<String> muazin = new ArrayList<>();
                    ArrayList<String> imam = new ArrayList<>();
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        JadwalPetugas jadwalPetugas = d.getValue(JadwalPetugas.class);
                        muazin.add(jadwalPetugas.getMuazin());
                        imam.add(jadwalPetugas.getImam());
                    }
                    if (muazin.size() == 5) {
                        tv_muazin3.setText("Muazin\t: " + muazin.get(0));
                        tv_muazin1.setText("Muazin\t: " + muazin.get(4));
                        tv_muazin2.setText("Muazin\t: " + muazin.get(1));
                        tv_muazin4.setText("Muazin\t: " + muazin.get(3));
                        tv_muazin5.setText("Muazin\t: " + muazin.get(2));
                        tv_imam1.setText("Imam\t\t: " + imam.get(4));
                        tv_imam2.setText("Imam\t\t: " + imam.get(1));
                        tv_imam3.setText("Imam\t\t: " + imam.get(0));
                        tv_imam4.setText("Imam\t\t: " + imam.get(3));
                        tv_imam5.setText("Imam\t\t: " + imam.get(2));
                        fab.setImageResource(R.drawable.ic_edit_white_24dp);
                    }
                } else {
                    tv_muazin1.setText("Muazin Belum ditetukan");
                    tv_muazin2.setText("Muazin Belum ditetukan");
                    tv_muazin3.setText("Muazin Belum ditetukan");
                    tv_muazin4.setText("Muazin Belum ditetukan");
                    tv_muazin5.setText("Muazin Belum ditetukan");
                    tv_imam1.setText("Imam Belum ditetukan");
                    tv_imam2.setText("Imam Belum ditetukan");
                    tv_imam3.setText("Imam Belum ditetukan");
                    tv_imam4.setText("Imam Belum ditetukan");
                    tv_imam5.setText("Imam Belum ditetukan");
                    fab.setImageResource(R.drawable.ic_add_black_24dp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
