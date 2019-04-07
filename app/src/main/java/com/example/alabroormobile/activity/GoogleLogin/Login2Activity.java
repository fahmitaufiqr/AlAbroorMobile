package com.example.alabroormobile.activity.GoogleLogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.alabroormobile.R;
import com.example.alabroormobile.activity.MainActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Aulia Ikvanda on 31,March,2019
 */

public class Login2Activity extends AppCompatActivity {

    private final String TAG = Login2Activity.class.getSimpleName();
    private static final int RC_SIGN=9001;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog pDialog;

    String gambar = "";
    String name;
    String umur = "";
    String nohp = "";
    String email = "";

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if (currentUser != null) {
            cekEmail(currentUser);
        }
    }

    private void cekEmail(FirebaseUser currentUser){
        String username = currentUser.getEmail().split("@")[0];
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Pengurus").child(username);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    cekUser();
                } else {
                    mFirebaseAuth.signOut();
                    Auth.GoogleSignInApi.signOut(googleApiClient);
                    Toast.makeText(Login2Activity.this, "Maaf, email anda tidak terdaftar.", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void cekUser(){
        final FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        final HashMap<String, Object> user= new HashMap<>();
        gambar = currentUser.getPhotoUrl().toString();
        name = currentUser.getDisplayName();

        final DatabaseReference dbf = FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());
        dbf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    startActivity(new Intent(Login2Activity.this, MainActivity.class));
                    finish();
                } else {
                    UserModel us = dataSnapshot.getValue(UserModel.class);
                    if (dataSnapshot.child("email").exists()){
                        email = us.getEmail();
                    }
                    if (dataSnapshot.child("nama").exists()){
                        name = us.getName();
                    }
                    if (dataSnapshot.child("gambar").exists()){
                        gambar = us.getGambar();
                    }
                    if (dataSnapshot.child("umur").exists()){
                        umur = us.getUmur();
                    }
                    if (dataSnapshot.child("nohp").exists()){
                        nohp = us.getNoHp();
                    }

                    user.put("numberPhone",nohp);
                    user.put("idEmail",currentUser.getUid());
                    user.put("name",name);
                    user.put("email",currentUser.getEmail());
                    user.put("age",umur);
                    user.put("gambar",gambar);
                    dbf.setValue(user);
                    Intent pindah = new Intent(Login2Activity.this,MainActivity.class);
                    startActivity(pindah);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        SignInButton loginBt = (SignInButton) findViewById(R.id.login_with_google);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(Login2Activity.this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(Login2Activity.this, "You Have An Error", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mFirebaseAuth=FirebaseAuth.getInstance();

        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInwithGoogle();
            }
        });
    }

    protected void signInwithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()){
                GoogleSignInAccount account= result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }else {
                mFirebaseAuth.signOut();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle: "+acct.getId());
        displayLoader();
        AuthCredential credential= GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: success");
                            final FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
                            cekEmail(currentUser);
                        }
                        else Log.w(TAG, "onFailure: ", task.getException() );
                    }
                });
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Verifikasi Akun...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }
}