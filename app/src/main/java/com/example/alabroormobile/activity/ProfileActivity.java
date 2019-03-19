package com.example.alabroormobile.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alabroormobile.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private Button btn_logout;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleSignInClient;
    private TextView Name, Email;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profil");

        mAuth=FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(getBaseContext(), LoginGoogleActivity.class));
                }
            }
        };


        btn_logout = findViewById(R.id.logOutBtn);
        mAuth = FirebaseAuth.getInstance();
        Name = (TextView) findViewById(R.id.name);
        Email = (TextView) findViewById(R.id.email);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setMessage("Klik lagi untuk keluar")
                        .setMessage("Yakin Keluar")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAuth.signOut();
                                finish();
                                if (mGoogleSignInClient != null){
                                    Auth.GoogleSignInApi.signOut(mGoogleSignInClient).setResultCallback(new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(@NonNull Status status) {
                                            Intent i = new Intent(ProfileActivity.this, LoginGoogleActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            finish();
                                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(i);
                                        }
                                    });
                                }
                            }
                        })
                        .setNegativeButton("Tidak", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        if (mGoogleSignInClient == null){
            mGoogleSignInClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(ProfileActivity.this, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Toast.makeText(ProfileActivity.this, "Something Error", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }

//        logout();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
//        if (opr.isDone()){
//            GoogleSignInResult result = opr.get();
//            handleSignInResult (result);
//        } else {
//            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
//                @Override
//                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
//                    handleSignInResult(googleSignInResult);
//                }
//            });
//        }
//    }
//    private void handleSignInResult(GoogleSignInResult result) {
//        if (result.isSuccess()){
//            GoogleSignInAccount account = result.getSignInAccount();
//            Name.setText(account.getDisplayName());
//            Email.setText(account.getEmail());
//        } else {
//
//        }
//    }

//    private void logout() {
//        btn_logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAuth.signOut();
//                finish();
//                if (mGoogleSignInClient != null){
//                    Auth.GoogleSignInApi.signOut(mGoogleSignInClient).setResultCallback(new ResultCallback<Status>() {
//                        @Override
//                        public void onResult(@NonNull Status status) {
//                            Intent i = new Intent(ProfileActivity.this, LoginGoogleActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            finish();
//                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(i);
//                        }
//                    });
//                }
//            }
//        });
//    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
