package com.example.alabroormobile.HomeMenu.PengurusDKM;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.alabroormobile.model.Pengurus;
import com.example.alabroormobile.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TambahPengurusActivity extends AppCompatActivity {

    protected static EditText namaEdtTextPengurus;
    protected static EditText emailEditTextPengurus;
    private RadioGroup rg_status;
    private RadioButton rb_admin, rb_pengurus;
    private String status;
    private ProgressDialog loading;
    private DatabaseReference database;
    private String sPidPengurus,sNamaPengurus, sEmailPengurus, sStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pengurus);

        database = FirebaseDatabase.getInstance().getReference();
        sPidPengurus = getIntent().getStringExtra("id");
        sNamaPengurus = getIntent().getStringExtra("nama");
        sEmailPengurus = getIntent().getStringExtra("email");
        sStatus = getIntent().getStringExtra("status");

        //INISIALISASI
        namaEdtTextPengurus = (EditText) findViewById(R.id.input_nama_pengurus);
        emailEditTextPengurus = (EditText) findViewById(R.id.input_email_pengurus);
        rg_status = (RadioGroup) findViewById(R.id.rg_status);
        rb_admin = (RadioButton) findViewById(R.id.rb_admin);
        rb_pengurus = (RadioButton) findViewById(R.id.rb_pengurus);
        Button submitAcara = (Button)findViewById(R.id.save_acara_pengurus);
        Button cancelSubmit = (Button)findViewById(R.id.cancel_bt_pengurus);

        //SET DATA
        namaEdtTextPengurus.setText(sNamaPengurus);
        emailEditTextPengurus.setText(sEmailPengurus);
        if (sStatus.equalsIgnoreCase("Admin")){
            rb_admin.setChecked(true);
        } else if (sStatus.equalsIgnoreCase("Pengurus")){
            rb_pengurus.setChecked(true);
        }

        status = "Pengurus";

        rg_status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_admin:
                        status = "Admin";
                        break;
                    case R.id.rb_pengurus:
                        status = "Pengurus";
                        break;
                }
            }
        });

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
                                Snama,
                                Semail.toLowerCase(),
                                status));
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
                                Snama,
                                Semail.toLowerCase(),
                                status),
                                sPidPengurus);
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
        String username = emailEditTextPengurus.getText().toString().replace(".", "0").split("@")[0];
        database.child("Pengurus")
                .child(username)
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
