package com.example.alabroormobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.alabroormobile.R;
import com.example.alabroormobile.model.Acara;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewPengajianActivity extends AppCompatActivity {

    private TextView mTextViewNama;
    private TextView mTextViewKeterangan;
    private TextView mTextViewJam;
    private CalendarView mCalenderView;
    ArrayList<Acara> acaraView;
    private String acaraID;

    private static final String TAG = "acaranya";
    private DatabaseReference database;
    private String sNama, sTanggal, sJam, sDesk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pengajian);

        //Inisialitation Variable
        mTextViewNama = findViewById(R.id.nama_acara_view);
        mCalenderView = findViewById(R.id.tanggal_view);
        mTextViewKeterangan = findViewById(R.id.keterangan_view);
        mTextViewJam = findViewById(R.id.view_jam);

        //Get Data From Intent
        Intent intentGetData = getIntent();
        acaraID = intentGetData.getStringExtra("acaraList");
        database = FirebaseDatabase.getInstance().getReference("acaraList").child("nama");

        sNama = getIntent().getStringExtra("nama");
        sTanggal = getIntent().getStringExtra("date");
        sJam = getIntent().getStringExtra("time");
        sDesk = getIntent().getStringExtra("keterangan");

        //SET DATA
        mTextViewNama.setText(sNama);
        mTextViewKeterangan.setText(sDesk);
//        mTextViewJam.setText(sJam);

        //SET DATA DATE
//        mCalenderView.setDate(Long.parseLong(sTanggal));
    }
}
