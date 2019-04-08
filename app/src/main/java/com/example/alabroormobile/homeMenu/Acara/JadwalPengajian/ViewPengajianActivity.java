package com.example.alabroormobile.homeMenu.Acara.JadwalPengajian;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alabroormobile.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Aulia Ikvanda on 31,March,2019
 */

public class ViewPengajianActivity extends AppCompatActivity {

    private TextView mTextViewNama;
    private TextView mTextViewKeterangan;
    private TextView mTextViewJam;
    private TextView mDateView;
    private ImageView edit, delete;
    private String sNama = "", sTanggal = "", sJam = "", sDesk = "";
    private String acaraID;
    ArrayList<Pengajian> pengajianView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pengajian);
        getSupportActionBar().setTitle("Detail Pengajian");

        pengajianView = new ArrayList<>();
        Intent intentGetData = getIntent();

        //Inisialitation Variable
        mTextViewNama = findViewById(R.id.nama_acara_view);
        mDateView = findViewById(R.id.tanggal_view);
        mTextViewKeterangan = findViewById(R.id.keterangan_view);
        mTextViewJam = findViewById(R.id.jam_view);
        edit = findViewById(R.id.editAcara);
        delete = findViewById(R.id.deleteAcara);

        //Get Data From Intent
        acaraID = intentGetData.getStringExtra("key");
        sNama = getIntent().getStringExtra("nama");
        sTanggal = getIntent().getStringExtra("date");
        sJam = getIntent().getStringExtra("time");
        sDesk = getIntent().getStringExtra("keterangan");

        //SET DATA
        mTextViewNama.setText(sNama);
        mTextViewKeterangan.setText(sDesk);
        mDateView.setText(sTanggal);
        mTextViewJam.setText(sJam);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(ViewPengajianActivity.this, TambahPengajianActivity.class);
                edit.putExtra("key", acaraID);
                startActivity(edit);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().getRoot().child("key");
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ViewPengajianActivity.this, JadwalPengajianActivity.class));
        finish();
        super.onBackPressed();
    }
}
