package com.example.alabroormobile.HomeMenu;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alabroormobile.R;
import com.example.alabroormobile.Login2Activity;
import com.example.alabroormobile.model.Pengurus;
import com.example.alabroormobile.model.UserModel;
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

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Aulia Ikvanda on 31,March,2019
 */

public class ProfileActivity extends AppCompatActivity {

    TextView namaUser,emailUser,hpUser,statuss;
    CircleImageView profileUser;
    ImageView editProfileBtn;
    GoogleApiClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profil Pengguna");

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        loading = ProgressDialog.show(ProfileActivity.this,
                null,
                "Mengambil Data...",
                true,
                false);

        //INISIALISASI
        namaUser = findViewById(R.id.tv_name);
        emailUser = findViewById(R.id.emailView);
        hpUser = findViewById(R.id.noHpView);
        statuss = findViewById(R.id.status);

        profileUser =  findViewById(R.id.userProfile);
        editProfileBtn = findViewById(R.id.editProfileBt);

        //CEK USER ADMIN =====================================================================
        String username = currentUser.getEmail().replace(".", "0").split("@")[0];
        DatabaseReference dbuserA = FirebaseDatabase.getInstance().getReference("Pengurus").child(username);
        dbuserA.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pengurus pengurus = dataSnapshot.getValue(Pengurus.class);
                statuss.setText(pengurus.getStatus());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.dismiss();
            }
        });

        //====================================================================================================

        //SET DATA PENGGUNA PENGGUNA
        DatabaseReference dbuser = FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());
        dbuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                namaUser.setText(user.getName());
                emailUser.setText(user.getEmail());
                hpUser.setText(user.getNumberPhone());
                Log.d("lol", "onDataChange: tes gambar " + user.getGambar());
                Picasso.with(getApplicationContext()).load(user.getGambar()).into(profileUser);

                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.dismiss();
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
                            Log.d("LOGD", "onConnectionFailed: Error");
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }


        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfilePopUp();
            }
        });


    }

    private void editProfilePopUp(){
        HashMap<String, Object> hashMap = new HashMap<>();
        Dialog popUp = new Dialog(ProfileActivity.this
                , android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        popUp.setContentView(R.layout.pop_up_edit_profile);
        EditText et_nama = (EditText) popUp.findViewById(R.id.edit_nama);
        EditText et_noHp = (EditText) popUp.findViewById(R.id.edit_no_hp);
        Button btn_cancel = (Button) popUp.findViewById(R.id.btn_cancel);
        Button btn_save = (Button) popUp.findViewById(R.id.btn_save);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        DatabaseReference dbuser = FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());
        dbuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                et_nama.setText(user.getName());
                et_noHp.setText(user.getNumberPhone());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.dismiss();
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nama = et_nama.getText().toString();
                String noHp = et_noHp.getText().toString();

                hashMap.put("name",nama);
                hashMap.put("numberPhone",noHp);

                dbuser.updateChildren(hashMap);

                popUp.dismiss();
                refresh();
            }
        });

        popUp.show();
    }

    public void refresh() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
