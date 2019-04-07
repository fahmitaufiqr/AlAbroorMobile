package com.example.alabroormobile.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alabroormobile.R;
import com.example.alabroormobile.activity.Acara.TambahAcaraActivity;

import java.util.Calendar;

public class TambahJadwalPetugasActivity extends AppCompatActivity {

    protected static Calendar myCalendar;
    int year;
    int month;
    int dayOfMonth;
    TextView tv_tanggal_jadwal_petugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jadwal_petugas);

        Button btn_tanggal_jadwal_petugas = (Button) findViewById(R.id.btn_tanggal_jadwal_petugas);
        tv_tanggal_jadwal_petugas = (TextView) findViewById(R.id.tv_tanggal_jadwal_petugas);

        btn_tanggal_jadwal_petugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar = Calendar.getInstance();
                year = myCalendar.get(Calendar.YEAR);
                month = myCalendar.get(Calendar.MONTH);
                dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(TambahJadwalPetugasActivity.this,
                        (view, year, month, dayOfMonth) -> tv_tanggal_jadwal_petugas.setText(dayOfMonth + " - " + (month + 1) + " - " + year),year,month,dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
    }
}
