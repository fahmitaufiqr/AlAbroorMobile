package com.example.alabroormobile.homeMenu.DaftarPengurus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.alabroormobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DaftarPengurusActivity extends AppCompatActivity {

    private DatabaseReference database;
    private ProgressDialog loading;
    private ArrayList<Pengurus> pengurusArrayList;
    private RecyclerViewAdapterPengurus recyclerViewAdapterPengurus;
    private RecyclerView rc_list_pengurus;
    private FloatingActionButton fab_tambah_pengurus;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pengurus);
        getSupportActionBar().setTitle("Daftar Petugas");

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //CEK USER ADMIN =====================================================================
        String username = currentUser.getEmail().split("@")[0];
        DatabaseReference dbuserA = FirebaseDatabase.getInstance().getReference("Pengurus").child(username);
        dbuserA.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pengurus pengurus = dataSnapshot.getValue(Pengurus.class);

                if (pengurus.getStatus().equals("Admin")){
                    fab_tambah_pengurus.setVisibility(View.VISIBLE);

                }else {
                    fab_tambah_pengurus.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.dismiss();
            }
        });

        //====================================================================================================

        fab_tambah_pengurus = findViewById(R.id.fab_add_pengurus);
        database = FirebaseDatabase.getInstance().getReference();
        rc_list_pengurus = findViewById(R.id.rv_jadwal_pengurus);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rc_list_pengurus.setLayoutManager(mLayoutManager);
        rc_list_pengurus.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(DaftarPengurusActivity.this,
                null,
                "Mengambil Data...",
                true,
                false);

        database.child("Pengurus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                pengurusArrayList = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Pengurus pengurus = noteDataSnapshot.getValue(Pengurus.class);
                    pengurus.setKey(noteDataSnapshot.getKey());
                    pengurusArrayList.add(pengurus);
                }

                recyclerViewAdapterPengurus = new RecyclerViewAdapterPengurus(pengurusArrayList, DaftarPengurusActivity.this);
                rc_list_pengurus.setAdapter(recyclerViewAdapterPengurus);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
                loading.dismiss();
            }
        });

        fab_tambah_pengurus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DaftarPengurusActivity.this, TambahPengurusActivity.class)
                        .putExtra("id", "")
                        .putExtra("nama", "")
                        .putExtra("email", "")
                        .putExtra("status", ""));
            }
        });
    }
}
