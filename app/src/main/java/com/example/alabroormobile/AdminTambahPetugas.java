package com.example.alabroormobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class AdminTambahPetugas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tambah_petugas);

        String[] dkmMasjid = {
                "Dedi Indra"
                ,"Atip"
                ,"Deni H"
                ,"Asmoro"
                ,"Agus"
                ,"Yusuf RT"
                ,"Agus Priatna"
                ,"Eko"
                ,"Irman S"
                ,"Ganjar"
                ,"H. Mahdi"
                ,"Imam A"
                ,"Asep M"};

        String[] hari = {
                "Minggu"
                ,"Senin"
                ,"Selasa"
                ,"Rabu"
                ,"Kamis"
                ,"Jum'at"
                ,"Sabtu"};

        String[] sholat ={
                "Shubuh"
                ,"Dzuhur"
                ,"Ashar"
                ,"Maghrib"
                ,"Isya"};

        //CODE SPINNER
        ArrayAdapter<String> arrayPetugas = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, dkmMasjid);
        ArrayAdapter<String> arrayHari = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, hari);
        ArrayAdapter<String> arraySholat = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, sholat);

        //INISIALISASI
        MaterialBetterSpinner hariSpinner = (MaterialBetterSpinner) findViewById(R.id.sp_hari);
        hariSpinner.setAdapter(arrayHari);
        MaterialBetterSpinner SholatSpinner = (MaterialBetterSpinner) findViewById(R.id.sp_sholat);
        SholatSpinner.setAdapter(arraySholat);
        MaterialBetterSpinner MuadzinSpinner = (MaterialBetterSpinner) findViewById(R.id.sp_muadzin);
        MuadzinSpinner.setAdapter(arrayPetugas);
        MaterialBetterSpinner imamSpinner = (MaterialBetterSpinner) findViewById(R.id.sp_imam);
        imamSpinner.setAdapter(arrayPetugas);
    }
}
