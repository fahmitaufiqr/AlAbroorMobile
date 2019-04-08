package com.example.alabroormobile.homeMenu.Acara.JadwalPengajian;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.alabroormobile.R;
import com.example.alabroormobile.model.Acara;
import com.example.alabroormobile.model.RecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Aulia Ikvanda on 31,March,2019
 */

public class JadwalPengajianActivity extends AppCompatActivity {

    private DatabaseReference database;
    private ProgressDialog loading;
    private ArrayList<Acara> acaraArrayList;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView rc_list_acara;
    private FloatingActionButton fab_tambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_pengajian);
        getSupportActionBar().setTitle("Jadwal Pengajian");

        fab_tambah = findViewById(R.id.fab_add_acara);
        database = FirebaseDatabase.getInstance().getReference();
        rc_list_acara = findViewById(R.id.rv_jadwal_pengajian);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rc_list_acara.setLayoutManager(mLayoutManager);
        rc_list_acara.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(JadwalPengajianActivity.this,
                null,
                "Mengambil Data...",
                true,
                false);

        database.child("acaraList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                acaraArrayList = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Acara acara = noteDataSnapshot.getValue(Acara.class);
                    acara.setKey(noteDataSnapshot.getKey());
                    acaraArrayList.add(acara);
                }
                recyclerViewAdapter = new RecyclerViewAdapter(acaraArrayList, JadwalPengajianActivity.this);
                rc_list_acara.setAdapter(recyclerViewAdapter);
                loading.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
                loading.dismiss();
            }
        });

        fab_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JadwalPengajianActivity.this, TambahAcaraActivity.class)
                        .putExtra("id", "")
                        .putExtra("title", "")
                        .putExtra("email", "")
                        .putExtra("desk", ""));
            }
        });
    }
}