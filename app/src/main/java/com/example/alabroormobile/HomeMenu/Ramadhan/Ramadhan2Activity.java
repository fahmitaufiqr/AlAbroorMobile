package com.example.alabroormobile.HomeMenu.Ramadhan;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.alabroormobile.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Ramadhan2Activity extends AppCompatActivity {

    private DatabaseReference database;
    private ProgressDialog loading;
    private ArrayList<Ramadhan2Model> ramadhanArrayList;
    private Ramadhan2Adapter ramadhan2Adapter;
    private RecyclerView rc_list_ramadhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ramadhan2);
        getSupportActionBar().setTitle("Jadwal Ramadhan");

        //    INISIALISASI
        database = FirebaseDatabase.getInstance().getReference();
        rc_list_ramadhan = findViewById(R.id.recview2Ramadhan);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rc_list_ramadhan.setLayoutManager(mLayoutManager);
        rc_list_ramadhan.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(Ramadhan2Activity.this,
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
                    Ramadhan2Model ramadhan = noteDataSnapshot.getValue(Ramadhan2Model.class);
//                    ramadhan.setKey(noteDataSnapshot.getKey());
                    ramadhanArrayList.add(ramadhan);
                }

                ramadhan2Adapter = new Ramadhan2Adapter(ramadhanArrayList, Ramadhan2Activity.this);
                rc_list_ramadhan.setAdapter(ramadhan2Adapter);
                loading.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
                loading.dismiss();
            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
