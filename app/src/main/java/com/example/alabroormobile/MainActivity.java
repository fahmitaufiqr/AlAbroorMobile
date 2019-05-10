package com.example.alabroormobile;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alabroormobile.homeMenu.Acara.JadwalPengajian.JadwalPengajianActivity;
import com.example.alabroormobile.homeMenu.DaftarPengurus.Pengurus;
import com.example.alabroormobile.homeMenu.Ramadhan.JadwalRamadhanActivity;
import com.example.alabroormobile.model.UserModel;
import com.example.alabroormobile.homeMenu.Acara.AcaraActivity;
import com.example.alabroormobile.homeMenu.ArahKiblat.ArahKiblatActivity;
import com.example.alabroormobile.homeMenu.DaftarPengurus.DaftarPengurusActivity;
import com.example.alabroormobile.homeMenu.InfoActivity;
import com.example.alabroormobile.homeMenu.JadwalPetugas.JadwalPetugasActivity;
import com.example.alabroormobile.homeMenu.ProfileActivity;
import com.example.alabroormobile.homeMenu.WaktuShalatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private long backPressedTime;
    private ProgressDialog loading;
    //~~Notif
    String TAG = MainActivity.class.getSimpleName();
    Button mBtnAddNotif;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    //Database Notif
    private DatabaseReference mDataNotif;
    //~~Akhirnotif


    private LinearLayout jadwalSholatbt, jadwalPetugasbt, jadwalAcarabt, statistikPetugasbt, arahKiblatbt, strukturDkmbt, profilebt, aboutbt;
    private TextView tv_tanggal, tv_nama_pengguna;
    private Button btnJadwalRamadhan;

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
        btnJadwalRamadhan = (Button) findViewById(R.id.jadwalRamadhan);

        //CEK USER ADMIN =====================================================================
        String username = currentUser.getEmail().split("@")[0];
        //String username2 = "asdas.das".replace(".","");
        DatabaseReference dbuserA = FirebaseDatabase.getInstance().getReference("Pengurus").child(username);
        dbuserA.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pengurus pengurus = dataSnapshot.getValue(Pengurus.class);

                if (pengurus.getStatus().equals("Admin")){
//                    mBtnAddNotif.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Masuk Sebagai Admin", Toast.LENGTH_SHORT).show();
                }else {
//                    mBtnAddNotif.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.dismiss();
            }
        });

        //====================================================================================================

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

        //Notif
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                Log.d(TAG, "onSuccess: "+instanceIdResult.getToken());
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Token").child(mAuth.getCurrentUser().getUid());
                databaseReference.child("tokenUser").setValue(instanceIdResult.getToken());
            }
        });

        mBtnAddNotif = findViewById(R.id.btn_notif_iqamat);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Call Database Notif
        mDataNotif = FirebaseDatabase.getInstance().getReference("notifs");
        subscribe();
//        mBtnAddNotif.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String key = mDataNotif.push().getKey();
//                HashMap<String, String> map = new HashMap<>();
//                map.put("idUser", mAuth.getCurrentUser().getUid());
//                map.put("idNotif",key);
//                mDataNotif.child(key).setValue(map);
//                Log.d(TAG, "onClick: isi Key "+key);
//            }
//        });

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //Notif

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

    private void subscribe(){
        FirebaseMessaging.getInstance().subscribeToTopic("iqomat")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Assalamualaikum Wr. Wb";
                        if (!task.isSuccessful()) {
                            msg = "Assalamualaikum Wr. Wb";
                        }
                        Log.d(TAG, msg);
                    }
                });
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
                Intent intent = new Intent(MainActivity.this, AcaraActivity.class);
                startActivity(intent);
            }
        });

        statistikPetugasbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, StatistikActivity.class);
//                startActivity(intent);

                Toast.makeText(MainActivity.this, "Fitur Dalam Pengembangan", Toast.LENGTH_SHORT).show();

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
                Intent intent = new Intent(MainActivity.this, DaftarPengurusActivity.class);
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

        btnJadwalRamadhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JadwalRamadhanActivity.class);
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
