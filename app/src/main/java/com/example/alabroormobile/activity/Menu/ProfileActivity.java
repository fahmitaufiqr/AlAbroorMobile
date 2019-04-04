package com.example.alabroormobile.activity.Menu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alabroormobile.R;
import com.example.alabroormobile.activity.GoogleLogin.Login2Activity;
import com.example.alabroormobile.activity.GoogleLogin.UserModel;
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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Aulia Ikvanda on 31,March,2019
 */

public class ProfileActivity extends AppCompatActivity {

    TextView namaUser,emailUser,umurUser,hpUser;
    CircleImageView profileUser;
    ImageView logoutBtn;
    GoogleApiClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profil Pengguna");

        //INISIALISASI
        namaUser = findViewById(R.id.tv_name);
        emailUser = findViewById(R.id.emailView);
        umurUser = findViewById(R.id.umurView);
        hpUser = findViewById(R.id.noHpView);

        profileUser =  findViewById(R.id.userProfile);
        logoutBtn = findViewById(R.id.LogOutBt);

        //SET NAMA PENGGUNA
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        DatabaseReference dbuser = FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());
        dbuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                namaUser.setText(user.getName());
                emailUser.setText(user.getEmail());
                umurUser.setText(user.getUmur());
                hpUser.setText(user.getNoHp());
                Log.d("lol", "onDataChange: tes gambar " + user.getGambar());
                Picasso.with(getApplicationContext()).load(user.getGambar()).into(profileUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        if (mGoogleSignInClient == null){
            mGoogleSignInClient = new GoogleApiClient.Builder(ProfileActivity.this)
                    .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Toast.makeText(getApplicationContext(), "Something Error", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setMessage("Klik lagi untuk keluar")
                        .setMessage("Yakin Keluar")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               FirebaseAuth.getInstance().signOut();
                                if (mGoogleSignInClient != null){
                                    Auth.GoogleSignInApi.signOut(mGoogleSignInClient).setResultCallback(new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(@NonNull Status status) {

                                        }
                                    });
                                }
                               startActivity(new Intent(ProfileActivity.this, Login2Activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            }
                        })
                        .setNegativeButton("Tidak", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

    }
}
