package com.example.alabroormobile.AdminController;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alabroormobile.R;
import com.example.alabroormobile.activity.GoogleLogin.Login2Activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity {

    EditText pass,username;
    Button login_btn,userBack;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        //INISIALISASI
        pass=(EditText)findViewById(R.id.password);
        username=(EditText)findViewById(R.id.username);
        login_btn=findViewById(R.id.login);

        //USER BACK
        userBack = (Button) findViewById(R.id.backUser);
        userBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(AdminLoginActivity.this, Login2Activity.class);
                startActivity(back);
            }
        });

        //ADMIN LOGIN
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminlog();
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

    private void adminlog() {
        displayLoader();
        final DatabaseReference dbf = FirebaseDatabase.getInstance().getReference("Admin").child("01");
        dbf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AdminModel adminModel = dataSnapshot.getValue(AdminModel.class);
                if(username.getText().toString().equals(adminModel.getUsername())&& pass.getText().toString().equals(adminModel.getPassword())){
                    Intent back = new Intent(AdminLoginActivity.this, AdminMainActivity.class);
                    pDialog.dismiss();
                    startActivity(back);
                }else {
                    Toast.makeText(AdminLoginActivity.this, "Gagal Masuk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("cek error", "onCancelled: ",databaseError.toException() );
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}