package com.example.alabroormobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class verifikasi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi);

        //inisialisasi
        Button nextBt = (Button) findViewById(R.id.verifikasibt);

        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(verifikasi.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
