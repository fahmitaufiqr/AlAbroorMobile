package com.example.alabroormobile.activity.RamadhanAct;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alabroormobile.R;
import com.example.alabroormobile.activity.Acara.TambahAcaraActivity;
import com.example.alabroormobile.model.Acara;
import com.google.android.gms.tasks.OnSuccessListener;
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

//                editRamadhan(new RamadhanModel(
//                        sPenceramah.toLowerCase(),
//                        sHariKe.toLowerCase(),
//                        sTanggal.toLowerCase()), ramadhanID);
                finish();

            }
        });

    }

//    private void editRamadhan(RamadhanModel ramadhanModel, String id) {
//        database.child("Ramadhan")
//                .child(id)
//                .setValue(ramadhanModel)
//                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        loading.dismiss();
//
//                        Toast.makeText(DetailRamadhanActivity.this, "Data Berhasil diedit", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

}
