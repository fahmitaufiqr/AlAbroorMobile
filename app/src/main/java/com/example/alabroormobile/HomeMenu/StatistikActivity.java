package com.example.alabroormobile.HomeMenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.alabroormobile.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class StatistikActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik);
        getSupportActionBar().setTitle("Statistik Keaktifan");

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

        //CODE SPINNER
        ArrayAdapter<String> arrayImam = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, dkmMasjid);

        MaterialBetterSpinner imamSpinner = (MaterialBetterSpinner) findViewById(R.id.sp_imam_stat);
        imamSpinner.setAdapter(arrayImam);
    }
}
