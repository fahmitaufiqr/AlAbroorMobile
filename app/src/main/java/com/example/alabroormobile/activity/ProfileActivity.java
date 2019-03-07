package com.example.alabroormobile.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alabroormobile.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private TextView tv_status, tv_nama_user, tv_telp;
    private ImageView iv_foto_user, iv_logout;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profil");

        auth = FirebaseAuth.getInstance();

        iv_logout = (ImageView) findViewById(R.id.iv_logout);
        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void signOut() {
        auth.signOut();
        Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ProfileActivity.this, Login2Activity.class);
        startActivity(intent);
        finish();
    }
}
