package com.example.alabroormobile.activity;

import android.app.NotificationManager;
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

import com.example.alabroormobile.R;
import com.example.alabroormobile.activity.GoogleLogin.UserModel;
import com.example.alabroormobile.activity.Menu.AcaraActivity;
import com.example.alabroormobile.activity.Menu.ArahKiblatActivity;
import com.example.alabroormobile.activity.Menu.DaftarPengurusActivity;
import com.example.alabroormobile.activity.Menu.InfoActivity;
import com.example.alabroormobile.activity.Menu.JadwalPetugasActivity;
import com.example.alabroormobile.activity.Menu.ProfileActivity;
import com.example.alabroormobile.activity.Menu.StatistikActivity;
import com.example.alabroormobile.activity.Menu.WaktuShalatActivity;
import com.example.alabroormobile.helpers.MethodHelper;
import com.example.alabroormobile.helpers.VarConstants;
import com.example.alabroormobile.helpers.WaktuShalatHelper;
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
import java.util.Date;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private long backPressedTime;


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

    // ---------------------------------------------------------------------------------------------
    private int countTime;
    // ---------------------------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        DatabaseReference dbuser = FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());
        dbuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                tv_nama_pengguna.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
        //Notif

        Date Date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("EEEE, dd MMM yyyy");
        String formattanggal = format.format(Date);
        tv_tanggal.setText(formattanggal);

        intent();

        //CODE untuk WAKTU MUNDUR
        //
        //
        TextView mTextViewShalatMendatang = findViewById(R.id.jadwal_textview_shalat);
        TextView mTextViewCoundown = findViewById(R.id.jadwal_textview_hitungmundur);
        // Deklarasi Class helper
        WaktuShalatHelper mWaktuShalatHelper = new WaktuShalatHelper();
        MethodHelper mMethodHelper = new MethodHelper();
        // -----------------------------------------------------------------------------------------
        int jumlahDetikSaatIni = mMethodHelper.getSumWaktuDetik();
        String mJadwal = mWaktuShalatHelper.getJadwalShalat();
        // -----------------------------------------------------------------------------------------
        // -----------------------------------------------------------------------------------------
        int detikShubuh = mWaktuShalatHelper.getJmlWaktuShubuh();
        int detikDzuhur = mWaktuShalatHelper.getJmlWaktuDzuhur();
        int detikAshar = mWaktuShalatHelper.getJmlWaktuAshar();
        int detikMaghrib = mWaktuShalatHelper.getJmlWaktuMaghrib();
        int detikIsya = mWaktuShalatHelper.getJmlWaktuIsya();
        int detikAfterMid = mWaktuShalatHelper.getJmlAftMidnight();
        int detikBeforeMid = mWaktuShalatHelper.getJmlBeMidnight();
        // -----------------------------------------------------------------------------------------

        switch (mJadwal) {
            case VarConstants.Constants.SHUBUH:
                mTextViewShalatMendatang.setText(VarConstants.Constants.DZUHUR.substring(7));
                countTime = (detikDzuhur - jumlahDetikSaatIni) * VarConstants.Constants.DETIK_KE_MILI;
                break;
            case VarConstants.Constants.DZUHUR:
                mTextViewShalatMendatang.setText(VarConstants.Constants.ASHAR.substring(7));
                countTime = (detikAshar - jumlahDetikSaatIni) * VarConstants.Constants.DETIK_KE_MILI;
                break;
            case VarConstants.Constants.ASHAR:
                mTextViewShalatMendatang.setText(VarConstants.Constants.MAGHRIB.substring(7));
                countTime = (detikMaghrib - jumlahDetikSaatIni) * VarConstants.Constants.DETIK_KE_MILI;
                break;
            case VarConstants.Constants.MAGHRIB:
                mTextViewShalatMendatang.setText(VarConstants.Constants.ISYA.substring(7));
                countTime = (detikIsya - jumlahDetikSaatIni) * VarConstants.Constants.DETIK_KE_MILI;
                break;
            case VarConstants.Constants.ISYA:
                mTextViewShalatMendatang.setText(VarConstants.Constants.SHUBUH.substring(7));
                if ((jumlahDetikSaatIni == detikAfterMid) || (jumlahDetikSaatIni < detikShubuh)) {
                    countTime = (detikShubuh - jumlahDetikSaatIni) * VarConstants.Constants.DETIK_KE_MILI;
                } else if ((jumlahDetikSaatIni == detikIsya) || (jumlahDetikSaatIni <= detikBeforeMid)) {
                    countTime =  (detikShubuh + detikBeforeMid - jumlahDetikSaatIni) * VarConstants.Constants.DETIK_KE_MILI;
                }
                break;
            default:
                mTextViewShalatMendatang.setText(VarConstants.Constants.DZUHUR.substring(7));
                countTime = (detikDzuhur - jumlahDetikSaatIni) * VarConstants.Constants.DETIK_KE_MILI;
                break;
        }
        // -----------------------------------------------------------------------------------------
        mWaktuShalatHelper.CoundownTime(countTime, mTextViewCoundown);
        // -----------------------------------------------------------------------------------------
        //
        //
        //CODE untuk WAKTU MUNDUR
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
                        String msg = "Yeay";
                        if (!task.isSuccessful()) {
                            msg = "Ney";
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
                Intent intent = new Intent(MainActivity.this, JadwalPetugasActivity.class);
                startActivity(intent);
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
                Intent intent = new Intent(MainActivity.this, StatistikActivity.class);
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
