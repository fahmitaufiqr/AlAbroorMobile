package com.example.alabroormobile.activity;

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
import com.example.alabroormobile.model.Pengurus;
import com.example.alabroormobile.model.RecyclerViewAdapterPengurus;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pengurus);
        getSupportActionBar().setTitle("Daftar Pengurus");

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

                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                pengurusArrayList = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    /**
                     * Mapping data pada DataSnapshot ke dalam object Wisata
                     * Dan juga menyimpan primary key pada object Wisata
                     * untuk keperluan Edit dan Delete data
                     */
                    Pengurus pengurus = noteDataSnapshot.getValue(Pengurus.class);
                    pengurus.setKey(noteDataSnapshot.getKey());

                    /**
                     * Menambahkan object Wisata yang sudah dimapping
                     * ke dalam ArrayList
                     */
                    pengurusArrayList.add(pengurus);
                }


                /**
                 * Inisialisasi adapter dan data hotel dalam bentuk ArrayList
                 * dan mengeset Adapter ke dalam RecyclerView
                 */
                recyclerViewAdapterPengurus = new RecyclerViewAdapterPengurus(pengurusArrayList, DaftarPengurusActivity.this);
                rc_list_pengurus.setAdapter(recyclerViewAdapterPengurus);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                /**
                 * Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat
                 */
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
                loading.dismiss();
            }
        });

        fab_tambah_pengurus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DaftarPengurusActivity.this, TambahPengurusActivity.class)
                        .putExtra("id", "")
                        .putExtra("title", "")
                        .putExtra("email", "")
                        .putExtra("desk", ""));
            }
        });
    }
}
