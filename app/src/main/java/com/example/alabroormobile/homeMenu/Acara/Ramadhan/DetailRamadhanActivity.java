package com.example.alabroormobile.homeMenu.Acara.Ramadhan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alabroormobile.R;
import com.google.firebase.database.DatabaseReference;


import java.util.ArrayList;

public class DetailRamadhanActivity extends AppCompatActivity {

    private TextView mTextViewHariKe;
    private TextView mTextViewTanggal;
    private TextView mTextViewPenceramah;
    private ImageView save,reset;

    private ProgressDialog loading;
    private DatabaseReference database;

    private String sHariKe = "", sTanggal = "", sPenceramah = "";

    private String ramadhanID;
    ArrayList<RamadhanModel> ramadhanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ramadhan);
        getSupportActionBar().setTitle("Detail Ramadhan");

        ramadhanList = new ArrayList<>();
        Intent intentGetData = getIntent();

        //Inisialitation Variable
        mTextViewHariKe = findViewById(R.id.ramadhanKe);
        mTextViewTanggal = findViewById(R.id.tanggal_view_ram);
        mTextViewPenceramah = findViewById(R.id.input_nama_penceramah);
        save = findViewById(R.id.editRamadhan);
        reset = findViewById(R.id.resetRamadhan);

        //Get Data From Intent
        ramadhanID = intentGetData.getStringExtra("key");
        sHariKe = getIntent().getStringExtra("hariKe");
        sTanggal = getIntent().getStringExtra("tanggal");
        sPenceramah = getIntent().getStringExtra("penceramah");

        //SET DATA
        mTextViewHariKe.setText(sHariKe);
        mTextViewTanggal.setText(sTanggal);
        mTextViewPenceramah.setText(sPenceramah);

        //SAVE EDIT
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sPenceramah = mTextViewPenceramah.getText().toString();

                loading = ProgressDialog.show(DetailRamadhanActivity.this,
                        null,
                        "Sedang Proses...",
                        true,
                        false);

                finish();

            }
        });

    }
}
