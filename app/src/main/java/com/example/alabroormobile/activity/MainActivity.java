package com.example.alabroormobile.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alabroormobile.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    //~~Notif
    String TAG = MainActivity.class.getSimpleName();
    Button mBtnAddNotif;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;

    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    //Database Notif
    private DatabaseReference mDataNotif;
    //~~Akhirnotif

    private LinearLayout jadwalSholatbt, jadwalPetugasbt, jadwalAcarabt, statistikPetugasbt, arahKiblatbt, strukturDkmbt, profilebt, aboutbt;
    private TextView tv_tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
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
        mBtnAddNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mDataNotif.push().getKey();
                HashMap<String, String> map = new HashMap<>();
                map.put("idUser", mAuth.getCurrentUser().getUid());
                map.put("idNotif",key);
                mDataNotif.child(key).setValue(map);
                Log.d(TAG, "onClick: isi Key "+key);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //

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

        tanggal();
        intent();
    }

    private void subscribe(){
        FirebaseMessaging.getInstance().subscribeToTopic("iqomat")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Yeay";
                        if (!task.isSuccessful()) {
                            msg = "Ney";
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void tanggal(){
        Date Date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        String formattanggal = format.format(Date);
        tv_tanggal.setText(formattanggal);
    }

    private void intent(){
        jadwalSholatbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,WaktuShalat2Activity.class);
                startActivity(intent);
            }
        });

        jadwalPetugasbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JadwalPetugasActivity.class);
                startActivity(intent);
            }
        });

        jadwalAcarabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AcaraActivity.class);
                startActivity(intent);
            }
        });

        statistikPetugasbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,StatistikActivity.class);
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
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });
    }
}
