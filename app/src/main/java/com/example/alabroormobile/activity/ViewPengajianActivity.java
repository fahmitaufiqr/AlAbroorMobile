package com.example.alabroormobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.alabroormobile.R;
import com.example.alabroormobile.model.Acara;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewPengajianActivity extends AppCompatActivity {

    private TextView mTextViewNama;
    private TextView mTextViewKeterangan;
    private TextView mTextViewJam;
    private CalendarView mCalenderView;
    ArrayList<Acara> acaraView;
    private String acaraID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pengajian);

        //Get Data From Intent
        Intent intentGetData = getIntent();
        acaraID = intentGetData.getStringExtra("acaraList");

        acaraView = new ArrayList<>();

//        //Get Database Reference
//        mDatabase = FirebaseDatabase.getInstance().getReference("acaraList").child(acaraID);
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();


        //Inisialitation Variable
        mTextViewNama = findViewById(R.id.nama_acara_view);
        mCalenderView = findViewById(R.id.tanggal_view);
        mTextViewKeterangan = findViewById(R.id.keterangan_view);

    }
}
