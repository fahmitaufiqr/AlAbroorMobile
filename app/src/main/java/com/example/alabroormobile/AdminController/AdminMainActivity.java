package com.example.alabroormobile.AdminController;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.alabroormobile.R;
import com.example.alabroormobile.activity.GoogleLogin.Login2Activity;
import com.example.alabroormobile.activity.GoogleLogin.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {

    private DatabaseReference database;
    private ProgressDialog loading;
    private ArrayList<UserModel> userArrayList;
    private userAdapter adapterUser;

    private RecyclerView rc_list_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        getSupportActionBar().setTitle("Daftar User Al-Abr'roor Mobile");

        database = FirebaseDatabase.getInstance().getReference();
        rc_list_user = findViewById(R.id.rv_all_user);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        rc_list_user.setLayoutManager(mLayoutManager);
        rc_list_user.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(AdminMainActivity.this,
                null,
                "Mengambil Data...",
                true,
                false);

        //GET DATA FROM DATABASE
        database.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                userArrayList = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    UserModel user = noteDataSnapshot.getValue(UserModel.class);

                    userArrayList.add(user);
                }

                adapterUser = new userAdapter(userArrayList, AdminMainActivity.this);
                rc_list_user.setAdapter(adapterUser);
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
        Intent back = new Intent(AdminMainActivity.this, Login2Activity.class);
        startActivity(back);
        finish();

    }
}