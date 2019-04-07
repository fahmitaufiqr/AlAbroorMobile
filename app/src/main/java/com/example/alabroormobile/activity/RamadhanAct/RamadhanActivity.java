package com.example.alabroormobile.activity.RamadhanAct;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alabroormobile.R;
import com.example.alabroormobile.activity.Menu.DaftarPengurusActivity;
import com.example.alabroormobile.model.Pengurus;
import com.example.alabroormobile.model.RecyclerViewAdapterPengurus;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RamadhanActivity extends AppCompatActivity {

    private DatabaseReference database;
    private ProgressDialog loading;
    private ArrayList<RamadhanModel> ramadhanArrayList;
    private RamadhanAdapter ramadhanAdapter;
    private RecyclerView rc_list_ramadhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ramadhan);
        getSupportActionBar().setTitle("Jadwal Ramadhan");


        //    INISIALISASI
        database = FirebaseDatabase.getInstance().getReference();
        rc_list_ramadhan = findViewById(R.id.recviewRamadhan);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rc_list_ramadhan.setLayoutManager(mLayoutManager);
        rc_list_ramadhan.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(RamadhanActivity.this,
                null,
                "Mengambil Data...",
                true,
                false);

        //  GET DATA FROM DATABASE
        database.child("Ramadhan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ramadhanArrayList = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    RamadhanModel ramadhan = noteDataSnapshot.getValue(RamadhanModel.class);
                    ramadhan.setKey(noteDataSnapshot.getKey());
                    ramadhanArrayList.add(ramadhan);
                }

                ramadhanAdapter = new RamadhanAdapter(ramadhanArrayList, RamadhanActivity.this);
                rc_list_ramadhan.setAdapter(ramadhanAdapter);
                loading.dismiss();
                Toast.makeText(RamadhanActivity.this, "Data Berhasil Diambil", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
                loading.dismiss();
            }
        });

    }

}
