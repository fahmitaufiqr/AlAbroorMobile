package com.example.alabroormobile.homeMenu.Acara.Ramadhan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private TextView mTextViewTanggal;
    private TextView mTextViewPenceramah;
    private ImageView save,reset;

    private ProgressDialog loading;
    private DatabaseReference database;

    private String sHariKe = "", sTanggal = "", sPenceramah = "--", sBuka = "", sSahur = "";

    private String ramadhanID= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ramadhan);
        getSupportActionBar().setTitle("Detail Ramadhan");


        //Inisialitation Variable
        mTextViewHariKe = findViewById(R.id.ramadhanKe);
        mTextViewTanggal = findViewById(R.id.tanggal_view_ram);
        mTextViewPenceramah = findViewById(R.id.input_nama_penceramah);
        save = findViewById(R.id.editRamadhan);
        reset = findViewById(R.id.resetRamadhan);

        //Get Data From Intent
        ramadhanID = getIntent().getStringExtra("id");
        sHariKe = getIntent().getStringExtra("hariKe");
        sTanggal = getIntent().getStringExtra("tanggal");
        sPenceramah = getIntent().getStringExtra("penceramah");
        sBuka = getIntent().getStringExtra("buka");
        sSahur = getIntent().getStringExtra("sahur");


        //SET DATA
        mTextViewHariKe.setText(sHariKe);
        mTextViewTanggal.setText(sTanggal);
        mTextViewPenceramah.setText(sPenceramah);

        HashMap<String, Object> hashMap = new HashMap<>();

        DatabaseReference dbRamadhan = FirebaseDatabase.getInstance().getReference("Ramadhan").child(sHariKe);


        //SAVE EDIT
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String penceramah = mTextViewPenceramah.getText().toString();
                Toast.makeText(DetailRamadhanActivity.this, "Berhasil Di Edit", Toast.LENGTH_SHORT).show();
                hashMap.put("penceramah",penceramah);
                dbRamadhan.updateChildren(hashMap);

                Intent intent = new Intent(DetailRamadhanActivity.this, RamadhanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        //RESET
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hashMap.put("penceramah","--");
                dbRamadhan.updateChildren(hashMap);
                Toast.makeText(DetailRamadhanActivity.this, "Berhasil Di Reset", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailRamadhanActivity.this, RamadhanActivity.class);
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
