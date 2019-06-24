package com.example.alabroormobile.HomeMenu.Ramadhan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alabroormobile.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DetailRamadhanActivity extends AppCompatActivity {

    private TextView mTextViewHariKe;
    private TextView mTextViewImam;
    private TextView mTextViewQultum;
    private ImageView save,reset;

    private String sHariKe = "", sImam = "", sQultum = "";

    private String ramadhanID= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ramadhan);


        getSupportActionBar().setTitle("Detail Ramadhan");


        //Inisialitation Variable
        mTextViewHariKe = findViewById(R.id.ramadhanKe);
        mTextViewQultum = findViewById(R.id.input_nama_penceramah);
        mTextViewImam = findViewById(R.id.input_nama_imam);
        save = findViewById(R.id.editRamadhan);
        reset = findViewById(R.id.resetRamadhan);

        //Get Data From Intent
        sHariKe = getIntent().getStringExtra("hariKe");
        sImam = getIntent().getStringExtra("imam");
        sQultum = getIntent().getStringExtra("qultum");

        //SET DATA
        mTextViewHariKe.setText(sHariKe);
        mTextViewImam.setText(sImam);
        mTextViewQultum.setText(sQultum);

        HashMap<String, Object> hashMap = new HashMap<>();

        DatabaseReference dbRamadhan = FirebaseDatabase.getInstance().getReference("Ramadhan").child(sHariKe);


        //SAVE EDIT
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String penceramah = mTextViewQultum.getText().toString();
                String imam = mTextViewImam.getText().toString();
                Toast.makeText(DetailRamadhanActivity.this, "Berhasil Di Edit", Toast.LENGTH_SHORT).show();
                hashMap.put("qultum",penceramah);
                hashMap.put("imam",imam);
                dbRamadhan.updateChildren(hashMap);

                Intent intent = new Intent(DetailRamadhanActivity.this, Ramadhan2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        //RESET
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailRamadhanActivity.this, "Berhasil Di Reset", Toast.LENGTH_SHORT).show();
                hashMap.put("qultum","Belum Ditambahkan");
                hashMap.put("imam","Belum Ditambahkan");
                dbRamadhan.updateChildren(hashMap);


                Intent intent = new Intent(DetailRamadhanActivity.this, Ramadhan2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
