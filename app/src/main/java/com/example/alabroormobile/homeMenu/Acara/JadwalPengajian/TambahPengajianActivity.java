package com.example.alabroormobile.homeMenu.Acara.JadwalPengajian;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alabroormobile.R;
import com.example.alabroormobile.model.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

/**
 * Created by Aulia Ikvanda on 31,March,2019
 */

public class TambahPengajianActivity extends AppCompatActivity {

    protected static TextView viewDate;
    protected static TextView viewTime;
    protected static EditText namaEdtText;
    protected static EditText keteranganEditText;
    protected static Calendar myCalendar,calendar;
    protected static TextView viewUserSend;
    private TimePickerDialog timePickerDialog;
    private ProgressDialog loading;
    private DatabaseReference database;
    private String sPid,sNama, sTanggal, sJam, sDesk,sPengirim;

    int year;
    int month;
    int dayOfMonth;

    int currentHour;
    int currentMinute;
    String amPm;

    //GET USER
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pengajian);
        getSupportActionBar().setTitle("Tambah Pengajian");

        database = FirebaseDatabase.getInstance().getReference();
        sPid = getIntent().getStringExtra("id");
        sNama = getIntent().getStringExtra("nama");
        sDesk = getIntent().getStringExtra("keterangan");
        sTanggal = getIntent().getStringExtra("date");
        sJam = getIntent().getStringExtra("time");

        //INISIALISASI
        namaEdtText = (EditText) findViewById(R.id.input_nama_acara);
        keteranganEditText = (EditText) findViewById(R.id.input_keterangan);
        viewDate = (TextView) findViewById(R.id.view_date);
        viewTime = (TextView) findViewById(R.id.view_jam);
        viewUserSend = (TextView) findViewById(R.id.nama_pengirim_input);
        Button pickDate = (Button)findViewById(R.id.pick_date);
        Button pickTime = (Button)findViewById(R.id.pick_time);
        Button submitAcara = (Button)findViewById(R.id.save_acara);
        Button cancelSubmit = (Button)findViewById(R.id.cancel_bt);

        //NAMA PENGIRIM
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        DatabaseReference dbuser = FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());
        dbuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                viewUserSend.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.dismiss();
            }
        });
        //SET DATA
        namaEdtText.setText(sNama);
        keteranganEditText.setText(sDesk);
        viewDate.setText(sTanggal);
        viewTime.setText(sJam);

        if (sPid.equals("")){
            submitAcara.setText("SIMPAN");
            cancelSubmit.setText("BATAL");
        } else {
            submitAcara.setText("EDIT");
            cancelSubmit.setText("HAPUS");
        }

        submitAcara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Snama = namaEdtText.getText().toString();
                String Sket = keteranganEditText.getText().toString();
                String Sdate = viewDate.getText().toString();
                String Stime = viewTime.getText().toString();
                String Spengirim = viewUserSend.getText().toString();

                if (submitAcara.getText().equals("SIMPAN")){
                    // perintah save

                    if (Snama.equals("")) {
                        namaEdtText.setError("Silahkan masukkan nama acara");
                        namaEdtText.requestFocus();
                    } else if (Sket.equals("")) {
                        keteranganEditText.setError("Silahkan masukkan keterangan");
                        keteranganEditText.requestFocus();
                    } else if (Sdate.equals("")) {
                        viewDate.setError("Silahkan masukkan tanggal acara");
                        viewDate.requestFocus();
                    }else if (Stime.equals("")) {
                        viewTime.setError("Silahkan masukkan waktu acara");
                        viewTime.requestFocus();
                    } else {
                        loading = ProgressDialog.show(TambahPengajianActivity.this,
                                null,
                                "Sedang Proses...",
                                true,
                                false);

                        simpanPengajian(new Pengajian(
                                Snama.toLowerCase(),
                                Sket.toLowerCase(),
                                Sdate.toLowerCase(),
                                Stime.toLowerCase(),
                                sPid,
                                Spengirim));

                        finish();

                    }
                } else {
                    // perintah edit
                    if (Snama.equals("")) {
                        namaEdtText.setError("Silahkan masukkan nama acara");
                        namaEdtText.requestFocus();
                    } else if (Sket.equals("")) {
                        keteranganEditText.setError("Silahkan masukkan keterangan");
                        keteranganEditText.requestFocus();
                    } else if (Sdate.equals("")) {
                        viewDate.setError("Silahkan masukkan tanggal acara");
                        viewDate.requestFocus();
                    }else if (Stime.equals("")) {
                        viewTime.setError("Silahkan masukkan waktu acara");
                        viewTime.requestFocus();
                    } else {
                        loading = ProgressDialog.show(TambahPengajianActivity.this,
                                null,
                                "Sedang Proses...",
                                true,
                                false);

                        editPengajian(new Pengajian(
                                Snama.toLowerCase(),
                                Sket.toLowerCase(),
                                Sdate.toLowerCase(),
                                Stime.toLowerCase(),
                                        sPid,
                                        Spengirim
                                ),
                                sPid);
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
                    loading = ProgressDialog.show(TambahPengajianActivity.this,
                            null,
                            "Sedang Proses...",
                            true,
                            false);
                    deletePengajian();
                    finish();
                }
            }
        });

        //DATE PICKER
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar = Calendar.getInstance();
                year = myCalendar.get(Calendar.YEAR);
                month = myCalendar.get(Calendar.MONTH);
                dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(TambahPengajianActivity.this,
                        (view, year, month, dayOfMonth) -> viewDate.setText(dayOfMonth + " - " + (month + 1) + " - " + year),year,month,dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        //TIME PICKER
        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showTimeDialog();
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(TambahPengajianActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = " PM";
                        } else {
                            amPm = " AM";
                        }
                        viewTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);
                timePickerDialog.show();
            }
        });
    }

    private void deletePengajian(){
        database.child("acaraList")
                .child(sPid)
                .removeValue()
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        namaEdtText.setText("");
                        keteranganEditText.setText("");
                        viewDate.setText("");
                        viewTime.setText("");

                        Toast.makeText(TambahPengajianActivity.this, "Data Berhasil dihapus", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void simpanPengajian(Pengajian pengajian) {
        database.child("acaraList")
                .push()
                .setValue(pengajian)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        namaEdtText.setText("");
                        keteranganEditText.setText("");
                        viewDate.setText("");
                        viewTime.setText("");

                        Toast.makeText(TambahPengajianActivity.this, "Data Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void editPengajian(Pengajian pengajian, String id) {
        database.child("acaraList")
                .child(id)
                .setValue(pengajian)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        namaEdtText.setText("");
                        keteranganEditText.setText("");
                        viewDate.setText("");
                        viewTime.setText("");

                        Toast.makeText(TambahPengajianActivity.this, "Data Berhasil diedit", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showTimeDialog() {
        myCalendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                viewTime.setText(hourOfDay+":"+minute);
            }
        }, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(this));

        timePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
