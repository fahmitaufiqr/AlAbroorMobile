package com.example.alabroormobile;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.alabroormobile.model.Acara;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TambahAcaraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_acara);

        FloatingActionButton fabSave = (FloatingActionButton) findViewById(R.id.fab_save_acara);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTodo();
            }
        });
    }

    void saveTodo() {
        // first section
        // get the data to save in our firebase db
        EditText namaEdtText = (EditText) findViewById(R.id.input_nama_acara);
        EditText keteranganEditText = (EditText) findViewById(R.id.input_keterangan);
        DatePicker datePicker = (DatePicker) findViewById(R.id.pilihTanggal);
        Date date = new Date();
        date.setMonth(datePicker.getMonth());
        date.setYear(datePicker.getYear());
        date.setDate(datePicker.getDayOfMonth());

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String dateString = format.format(date);
        //make the modal object and convert it into hasmap

        //second section
        //save it to the firebase db
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String key = database.getReference("acaraList").push().getKey();

        Acara acaraa = new Acara();
        acaraa.setNama(namaEdtText.getText().toString());
        acaraa.setKeterangan(keteranganEditText.getText().toString());
        acaraa.setDate(dateString);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, acaraa.toFirebaseObject());
        database.getReference("acaraList").updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    finish();
                }
            }
        });
    }
}
