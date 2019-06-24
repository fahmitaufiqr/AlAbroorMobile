package com.example.alabroormobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alabroormobile.HomeMenu.Kegiatan.KegiatanActivity;
import com.example.alabroormobile.HomeMenu.Ramadhan.Ramadhan2Activity;
import com.example.alabroormobile.model.UserModel;
import com.example.alabroormobile.HomeMenu.ArahKiblat.ArahKiblatActivity;
import com.example.alabroormobile.HomeMenu.PengurusDKM.PengurusDKMActivity;
import com.example.alabroormobile.HomeMenu.InfoActivity;
import com.example.alabroormobile.HomeMenu.JadwalPetugas.JadwalPetugasActivity;
import com.example.alabroormobile.HomeMenu.ProfileActivity;
import com.example.alabroormobile.HomeMenu.WaktuShalatActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
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
    GoogleApiClient mGoogleSignInClient;

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

        //DATA PENGGUNA
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        if (mGoogleSignInClient == null){
            mGoogleSignInClient = new GoogleApiClient.Builder(MainActivity.this)
                    .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Log.d("LOGD", "onConnectionFailed: Error");
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_logout, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_keluar) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Klik lagi untuk keluar")
                    .setMessage("Yakin Keluar")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        if (mGoogleSignInClient != null) {
                            Auth.GoogleSignInApi.signOut(mGoogleSignInClient).setResultCallback(new ResultCallback<Status>() {
                                @Override
                                public void onResult(@NonNull Status status) {
                                    Intent i = new Intent(MainActivity.this, Login2Activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    finish();
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                }
                            });
                        }
                    })
                    .setNegativeButton("Tidak", null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Ketuk lagi untuk keluar", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    private void intent() {
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
                Intent intent = new Intent(MainActivity.this, Ramadhan2Activity.class);
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
