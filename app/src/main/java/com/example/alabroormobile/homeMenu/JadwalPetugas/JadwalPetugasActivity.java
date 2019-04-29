package com.example.alabroormobile.homeMenu.JadwalPetugas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.alabroormobile.MainActivity;
import com.example.alabroormobile.R;
import com.example.alabroormobile.homeMenu.DaftarPengurus.Pengurus;
import com.example.alabroormobile.homeMenu.WaktuShalatActivity;
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
import java.util.List;

public class JadwalPetugasActivity extends AppCompatActivity {

    private ProgressDialog loading;
    private TextView tv_muazin1, tv_imam1, tv_muazin2, tv_imam2, tv_muazin3, tv_imam3, tv_muazin4, tv_imam4, tv_muazin5, tv_imam5;
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
            Intent intent = new Intent(JadwalPetugasActivity.this, TambahJadwalPetugasActivity.class);
            intent.putExtra("dataTanggal", sendTanggal);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_petugas);
        getSupportActionBar().setTitle("Jadwal Petugas");

        Calendar cal = java.util.Calendar.getInstance();
        sendTanggal = cal.get(Calendar.DAY_OF_MONTH) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.YEAR);
        showDataPetugas(sendTanggal);

        //inisialisasi
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

        cv_jadwal_petugas.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                sendTanggal = dayOfMonth + "-" + month + "-" + year;
                showDataPetugas(sendTanggal);
            }
        });
    }

    private void showDataPetugas(String date) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("JadwalPetugas").child(date);
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
