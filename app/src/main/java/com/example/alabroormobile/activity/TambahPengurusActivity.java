package com.example.alabroormobile.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alabroormobile.R;
import com.example.alabroormobile.model.Acara;
import com.example.alabroormobile.model.Pengurus;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class TambahPengurusActivity extends AppCompatActivity {

    private static final String TAG = "pengurus";

    protected static EditText namaEdtTextPengurus;
    protected static EditText emailEditTextPengurus;
    private ProgressDialog loading;

    private DatabaseReference database;
    //GET INTENT
    private String sPidPengurus,sNamaPengurus, sEmailPengurus;
    private Intent intentGetDataPengurus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pengurus);

        database = FirebaseDatabase.getInstance().getReference();

        sPidPengurus = getIntent().getStringExtra("id");
        sNamaPengurus = getIntent().getStringExtra("nama");
        sEmailPengurus = getIntent().getStringExtra("email");

        //INISIALISASI
        namaEdtTextPengurus = (EditText) findViewById(R.id.input_nama_pengurus);
        emailEditTextPengurus = (EditText) findViewById(R.id.input_email_pengurus);
        Button submitAcara = (Button)findViewById(R.id.save_acara_pengurus);
        Button cancelSubmit = (Button)findViewById(R.id.cancel_bt_pengurus);

        //SET DATA
        namaEdtTextPengurus.setText(sNamaPengurus);
        emailEditTextPengurus.setText(sEmailPengurus);

        if (sPidPengurus.equals("")){
            submitAcara.setText("SIMPAN");
            cancelSubmit.setText("BATAL");
        } else {
            submitAcara.setText("EDIT");
            cancelSubmit.setText("HAPUS");
        }

        submitAcara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Snama = namaEdtTextPengurus.getText().toString();
                String Semail = emailEditTextPengurus.getText().toString();

                if (submitAcara.getText().equals("SIMPAN")){
                    // perintah save

                    if (Snama.equals("")) {
                        namaEdtTextPengurus.setError("Silahkan masukkan nama acara");
                        namaEdtTextPengurus.requestFocus();
                    } else if (Semail.equals("")) {
                        emailEditTextPengurus.setError("Silahkan masukkan keterangan");
                        emailEditTextPengurus.requestFocus();
                    } else {
                        loading = ProgressDialog.show(TambahPengurusActivity.this,
                                null,
                                "Sedang Proses...",
                                true,
                                false);

                        simpanPengurus(new Pengurus(
                                Snama.toLowerCase(),
                                Semail.toLowerCase()));
                        finish();
                    }
                } else {
                    // perintah edit
                    if (Snama.equals("")) {
                        namaEdtTextPengurus.setError("Silahkan masukkan nama acara");
                        namaEdtTextPengurus.requestFocus();
                    } else if (Semail.equals("")) {
                        emailEditTextPengurus.setError("Silahkan masukkan keterangan");
                        emailEditTextPengurus.requestFocus();
                    } else {
                        loading = ProgressDialog.show(TambahPengurusActivity.this,
                                null,
                                "Sedang Proses...",
                                true,
                                false);

                        editPengurus(new Pengurus(
                                Snama.toLowerCase(),
                                Semail.toLowerCase()), sPidPengurus);
                        finish();
                    }
                }
            }
        });

        cancelSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cancelSubmit.getText().equals("BATAL")) {
                    //tutup page
                    finish();
                } else {
                    // delete
                    loading = ProgressDialog.show(TambahPengurusActivity.this,
                            null,
                            "Sedang Proses...",
                            true,
                            false);

                    deletePengurus();
                    finish();
                }
            }
        });
    }

    private void deletePengurus(){
        database.child("Pengurus")
                .child(sPidPengurus)
                .removeValue()
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();
                        namaEdtTextPengurus.setText("");
                        emailEditTextPengurus.setText("");

                        Toast.makeText(TambahPengurusActivity.this, "Data Berhasil dihapus", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void simpanPengurus(Pengurus pengurus) {
        database.child("Pengurus")
                .push()
                .setValue(pengurus)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();
                        namaEdtTextPengurus.setText("");
                        emailEditTextPengurus.setText("");

                        Toast.makeText(TambahPengurusActivity.this, "Data Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void editPengurus(Pengurus pengurus, String id) {
        database.child("Pengurus")
                .child(id)
                .setValue(pengurus)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();
                        namaEdtTextPengurus.setText("");
                        emailEditTextPengurus.setText("");

                        Toast.makeText(TambahPengurusActivity.this, "Data Berhasil diedit",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
