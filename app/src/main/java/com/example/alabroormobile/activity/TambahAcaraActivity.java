package com.example.alabroormobile.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alabroormobile.R;
import com.example.alabroormobile.model.Acara;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TambahAcaraActivity extends AppCompatActivity {

    protected static TextView viewDate;
    protected static TextView viewTime;
    protected static EditText namaEdtText;
    protected static EditText keteranganEditText;
    protected static Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_acara);

        //INISIALISASI
        namaEdtText = (EditText) findViewById(R.id.input_nama_acara);
        keteranganEditText = (EditText) findViewById(R.id.input_keterangan);

        viewDate = (TextView) findViewById(R.id.view_date);
        viewTime = (TextView) findViewById(R.id.view_jam);
        Button pickDate = (Button)findViewById(R.id.pick_date);
        Button pickTime = (Button)findViewById(R.id.pick_time);

        FloatingActionButton fabSave = (FloatingActionButton) findViewById(R.id.fab_save_acara);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTodo();
            }
        });

        //DATE PICKER
        myCalendar = Calendar.getInstance();
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TambahAcaraActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String formatTanggal = "dd-MM-yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                        viewDate.setText(sdf.format(myCalendar.getTime()));
                    }
                },
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //TIME PICKER
        assert pickTime != null;
        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePicker mTimePicker = new TimePicker();
                mTimePicker.show(getFragmentManager(), "Select time");
            }
        });
    }

    //PICK TIME 2
    public static class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            viewTime.setText(String.valueOf(hourOfDay) + " : " + String.valueOf(minute));
        }
    }


    void saveTodo() {
        // first section
        // get the data to save in our firebase db

//        Date date = new Date();
//        date.setMonth(myCalendar.getMonth());
//        date.setYear(c.getYear());
//        date.setDate(c.getDayOfMonth());

//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//
//        String dateString = format.format(date);
        //make the modal object and convert it into hasmap

        //second section
        //save it to the firebase db
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String key = database.getReference("acaraList").push().getKey();

        Acara acaraa = new Acara();
        acaraa.setNama(namaEdtText.getText().toString());
        acaraa.setKeterangan(keteranganEditText.getText().toString());
//        acaraa.setDate(dateString);

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
