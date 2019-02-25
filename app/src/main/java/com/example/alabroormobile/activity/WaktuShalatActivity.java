package com.example.alabroormobile.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alabroormobile.R;
import com.example.alabroormobile.api.ApiService;
import com.example.alabroormobile.api.ApiUrl;
import com.example.alabroormobile.model.ModelJadwal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WaktuShalatActivity extends AppCompatActivity {

    private TextView tv_fajr_value, tv_dhuhr_value, tv_asr_value, tv_maghrib_value, tv_isha_value;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waktu_shalat);
        getSupportActionBar().setTitle("Waktu Shalat");

        tv_fajr_value = findViewById(R.id.tv_fajr_value);
        tv_dhuhr_value = findViewById(R.id.tv_dhuhr_value);
        tv_asr_value = findViewById(R.id.tv_asr_value);
        tv_maghrib_value = findViewById(R.id.tv_maghrib_value);
        tv_isha_value = findViewById(R.id.tv_isha_value);

        getJadwal();
    }

    private void getJadwal(){
        progressDialog = new ProgressDialog(WaktuShalatActivity.this);
        progressDialog.setMessage("Silahkan Tunggu...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ModelJadwal> call = apiService.getJadwal();

        call.enqueue(new Callback<ModelJadwal>() {
            @Override
            public void onResponse(Call<ModelJadwal> call, Response<ModelJadwal> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()){
                    tv_fajr_value.setText(response.body().getItems().get(0).getFajr());
                    tv_dhuhr_value.setText(response.body().getItems().get(0).getDhuhr());
                    tv_asr_value.setText(response.body().getItems().get(0).getAsr());
                    tv_maghrib_value.setText(response.body().getItems().get(0).getMaghrib());
                    tv_isha_value.setText(response.body().getItems().get(0).getIsha());
                } else {

                }
            }

            @Override
            public void onFailure(Call<ModelJadwal> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(WaktuShalatActivity.this, "Server sedang bermasalah, coba kembali beberapa saat lagi.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}