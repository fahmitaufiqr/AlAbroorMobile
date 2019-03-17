package com.example.alabroormobile.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TambahAcaraActivity extends AppCompatActivity {

    protected static TextView viewDate;
    protected static TextView viewTime;
    protected static EditText namaEdtText;
    protected static EditText keteranganEditText;
    protected static Calendar myCalendar;
    private TimePickerDialog timePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    private String acaraID;

    //ValidatorForm
    boolean result = true;

    private ArrayList<Acara> mAcara;
    private ArrayList<String> mAcaraId;

    private final ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            mAcara.add(dataSnapshot.getValue(Acara.class));
            mAcaraId.add(dataSnapshot.getKey());
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            int acaras = mAcaraId.indexOf(dataSnapshot.getKey());
            mAcara.set(acaras, dataSnapshot.getValue(Acara.class));
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            int acaras = mAcaraId.indexOf(dataSnapshot.getKey());
            mAcaraId.remove(acaras);
            mAcara.remove(acaras);
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    // variable yang merefers ke Firebase Realtime Database
    private DatabaseReference mDatabase;
    //GET INTENT
    private String sNama = "", sTanggal="", sJam="", sDesk="";
    private Intent intentGetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_acara);
        getSupportActionBar().setTitle("Tambah Pengajian");

        //INISIALISASI
        namaEdtText = (EditText) findViewById(R.id.input_nama_acara);
        keteranganEditText = (EditText) findViewById(R.id.input_keterangan);
        viewDate = (TextView) findViewById(R.id.view_date);
        viewTime = (TextView) findViewById(R.id.view_jam);
        Button pickDate = (Button)findViewById(R.id.pick_date);
        Button pickTime = (Button)findViewById(R.id.pick_time);
        Button submitAcara = (Button)findViewById(R.id.save_acara);
        submitAcara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAcara();
            }
        });

        //Database
        mAcara = new ArrayList<>();
        mAcaraId = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("acaraList");
        mDatabase.addChildEventListener(childEventListener);

        //EditPost
        intentGetData = getIntent();
        acaraID = intentGetData.getStringExtra("acaraId");
        if (intentGetData.hasExtra("acaraId")){
            getEdit(acaraID);
        }

        //DATE PICKER
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar = Calendar.getInstance();
                year = myCalendar.get(Calendar.YEAR);
                month = myCalendar.get(Calendar.MONTH);
                dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(TambahAcaraActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        viewDate.setText(dayOfMonth + " - " + (month + 1) + " - " + year);

                    }
                },year,month,dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


//        //TIME PICKER
        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog();
            }
        });



    }

    private void getEdit(String acara){
        final DatabaseReference mDatabasePost = mDatabase.child(acara);
        mDatabasePost.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Acara acr = dataSnapshot.getValue(Acara.class);
                namaEdtText.setText(acr.getNama());
                keteranganEditText.setText(acr.getKeterangan());
                viewDate.setText(acr.getDate());
                viewTime.setText(acr.getTime());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showTimeDialog() {
        myCalendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                /**
                 * Method ini dipanggil saat kita selesai memilih waktu di DatePicker
                 */
                viewTime.setText(hourOfDay+":"+minute);
            }
        },
                /**
                 * Tampilkan jam saat ini ketika TimePicker pertama kali dibuka
                 */
                myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),

                /**
                 * Cek apakah format waktu menggunakan 24-hour format
                 */
                DateFormat.is24HourFormat(this));

        timePickerDialog.show();
    }

    private boolean validateForm(){

        //Nama Acara
        if (TextUtils.isEmpty(namaEdtText.getText().toString())){
            namaEdtText.setError("Wajib Diisi");
            result = false;
        }else {
            namaEdtText.setError(null);
        }
        //Description
        if (TextUtils.isEmpty(keteranganEditText.getText().toString())){
            keteranganEditText.setError("Wajib Diisi");
            result = false;
        }else {
            keteranganEditText.setError(null);
        }
        //Tanggal
        if (TextUtils.isEmpty(viewDate.getText().toString())){
            viewDate.setError("Wajib Diisi");
            result = false;
        }else {
            viewDate.setError(null);
        }
        //Jam
        if (TextUtils.isEmpty(viewTime.getText().toString())){
            viewTime.setError("Wajib Diisi");
            result = false;
        }else {
            viewTime.setError(null);
        }

        return result;
    }


    void saveAcara() {
        if (!validateForm()){
            Toast.makeText(this, "Semua Wajib Diisi", Toast.LENGTH_SHORT).show();
            return;
        }else {
            if (sNama == null){
                //For Edit
                if (intentGetData.hasExtra("postId")){
                    Toast.makeText(this, "Data Berhasil di Edit", Toast.LENGTH_SHORT).show();
                    mDatabase.child(acaraID).child("nama").setValue(namaEdtText.getText().toString());
                    mDatabase.child(acaraID).child("keterangan").setValue(keteranganEditText.getText().toString());
                    mDatabase.child(acaraID).child("date").setValue(viewDate.getText().toString());
                    mDatabase.child(acaraID).child("time").setValue(viewTime.getText().toString());
                }else {
                    Toast.makeText(this, "Acara tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }else {
                if (intentGetData.hasExtra("postId")){
                    //For Edit
                    mDatabase.child(acaraID).child("nama").setValue(namaEdtText.getText().toString());
                    mDatabase.child(acaraID).child("keterangan").setValue(keteranganEditText.getText().toString());
                    mDatabase.child(acaraID).child("date").setValue(viewDate.getText().toString());
                    mDatabase.child(acaraID).child("time").setValue(viewTime.getText().toString());

                }else {
                    //For Add
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    String key = database.getReference("acaraList").push().getKey();

                    Acara acaraa = new Acara();
                    acaraa.setKey("id",key);
                    acaraa.setNama(namaEdtText.getText().toString());
                    acaraa.setKeterangan(keteranganEditText.getText().toString());
                    acaraa.setDate(viewDate.getText().toString());
                    acaraa.setTime(viewTime.getText().toString());


                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put(key, acaraa.toFirebaseObject());
                    database.getReference("acaraList").updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                Snackbar.make(findViewById(R.id.save_acara), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    });

                        }
                }
            }
        }
//

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TambahAcaraActivity.this, JadwalPengajianActivity.class));
        finish();
        super.onBackPressed();
    }
    }
