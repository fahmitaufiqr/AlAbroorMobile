package com.example.alabroormobile.activity.Menu;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alabroormobile.compasHelper.Compass;
import com.example.alabroormobile.compasHelper.GPSTracker;
import com.example.alabroormobile.R;

import static android.view.View.INVISIBLE;

/**
 * Created by Aulia Ikvanda on 31,March,2019
 */


public class ArahKiblatActivity extends AppCompatActivity {

    // ---------------------------------------------------------------------------------------------
    private static final String TAG = "ArahKiblatActivity";
    private float currentAzimuth;
    private Compass compass;
    private ImageView mImageView_Kompas_Jarum, mImageView_Kompas;
    private TextView mTextView_ArahKiblat, mTextview_Lokasi;
    private SharedPreferences prefs;
    private GPSTracker gps;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arah_kiblat);
        getSupportActionBar().setTitle("Arah Kiblat");

        // -----------------------------------------------------------------------------------------
        compass = new Compass(ArahKiblatActivity.this);
        prefs = this.getSharedPreferences("", MODE_PRIVATE);
        gps = new GPSTracker(ArahKiblatActivity.this);
        // -----------------------------------------------------------------------------------------
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        mImageView_Kompas_Jarum = findViewById(R.id.imageview_kompas_jarum);
        mImageView_Kompas = findViewById(R.id.imageview_kompas);
        mTextView_ArahKiblat = findViewById(R.id.textview_arah_kabah);
        mTextview_Lokasi = findViewById(R.id.textview_lokasi);
        // -----------------------------------------------------------------------------------------
        setupCompass();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetch_GPS(gps, mImageView_Kompas_Jarum, mTextView_ArahKiblat, mTextview_Lokasi);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setupCompass() {
        getBearing(mTextView_ArahKiblat, mTextview_Lokasi);
        Compass.CompassListener cl = azimuth -> {
            adjustGambarDial(azimuth, mImageView_Kompas);
            adjustArrowQiblat(azimuth);
        };
        compass.setListener(cl);
    }


    public void adjustGambarDial(float azimuth, ImageView kompas) {
        Animation an = new RotateAnimation(-currentAzimuth, -azimuth,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        currentAzimuth = (azimuth);
        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);
        kompas.startAnimation(an);
    }

    public void adjustArrowQiblat(float azimuth) {
        float kiblat_derajat = GetFloat("kiblat_derajat");
        Animation an = new RotateAnimation(-(currentAzimuth)+kiblat_derajat, -azimuth,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        currentAzimuth = (azimuth);
        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);
        mImageView_Kompas_Jarum.startAnimation(an);
    }

    @SuppressLint("MissingPermission")
    public void getBearing(TextView arahKiblat, TextView lokasi){
        // Get the location manager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        }
        float kiblat_derajat = GetFloat("kiblat_derajat");
        if(kiblat_derajat > 0.0001){
            lokasi.setText("Lokasi anda : \nmenggunakan lokasi terakhir ");
            arahKiblat.setText("Arah Ka'bah : \n" + kiblat_derajat + " derajat dari utara");
        }else {
            fetch_GPS(gps, mImageView_Kompas_Jarum, arahKiblat, lokasi);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "This app requires Access Location", Toast.LENGTH_LONG).show();
                    this.finish();
                }
                return;
            }
        }
    }

    public void SaveFloat(String Judul, Float bbb){
        SharedPreferences.Editor edit = prefs.edit();
        edit.putFloat(Judul, bbb);
        edit.apply();
    }

    public Float GetFloat(String Judul){
        return prefs.getFloat(Judul, 0);
    }

    public void fetch_GPS(GPSTracker gps, ImageView jarum, TextView arahKiblat, TextView lokasi){
        double result = 0;
        if(gps.canGetLocation()){
            // -------------------------------------------------------------------------------------
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            lokasi.setText("Lokasi anda : \nLatitude : " + latitude + " & Longitude : " + longitude);
            Log.e("TAG", "GPS is on");
            double lat_saya = gps.getLatitude ();
            double lon_saya = gps.getLongitude ();
            // -------------------------------------------------------------------------------------
            if(lat_saya < 0.001 && lon_saya < 0.001) {
                jarum.setVisibility(INVISIBLE);
                jarum.setVisibility(View.GONE);
                arahKiblat.setText(this.getResources().getText(R.string.location_not_found));
                lokasi.setText(this.getResources().getText(R.string.location_not_found));

            }else{
                // ---------------------------------------------------------------------------------
                double longitude2 = 39.826206;
                double longitude1 = lon_saya;
                double latitude2 = Math.toRadians(21.422487);
                double latitude1 = Math.toRadians(lat_saya);
                double longDiff= Math.toRadians(longitude2-longitude1);
                double y = Math.sin(longDiff)*Math.cos(latitude2);
                double x = Math.cos(latitude1)*Math.sin(latitude2)-Math.sin(latitude1)*Math.cos(latitude2)*Math.cos(longDiff);
                // ---------------------------------------------------------------------------------
                result = (Math.toDegrees(Math.atan2(y, x))+360)%360;
                float result2 = (float)result;
                SaveFloat("kiblat_derajat", result2);
                String tempArahKiblat = "Arah Ka'bah : \n" + result2 + " derajat dari utara";
                arahKiblat.setText(tempArahKiblat);
                // ---------------------------------------------------------------------------------
            }
        }else{
            gps.showSettingsAlert();
            jarum.setVisibility(View.GONE);
            arahKiblat.setText(this.getResources().getText(R.string.enable_location_please));
            lokasi.setText(this.getResources().getText(R.string.enable_location_please));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "start compass");
        compass.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        compass.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        compass.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "stop compass");
        compass.stop();
    }
}
