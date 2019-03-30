package com.example.alabroormobile.activity.Menu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alabroormobile.R;
import com.example.alabroormobile.compasHelper.GPSTracker;
import com.example.alabroormobile.model.PrayTimes;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WaktuShalatActivity extends AppCompatActivity {

    PrayTimes prayers;

    TextView mSubuh, mZuhur, mAsar, mMagrib, mIsya, mDate,lt,ln,al;
    RelativeLayout mlayoutDate;
    String address,city,state,country,postalCode,knownName;
    GPSTracker gps;
    Geocoder geocoder;
    List<Address> addresses;
    double timezone;

    /* Lokasi Daerah Bandung dan Sekitarnya */
    double latitude = -6.974086;
    double longitude = 107.630305;

    int year, month, day;

    public String[] months = { "Januari", "Februari", "March", "April",
            "Mei", "Juni", "Juli", "Augustus", "September", "Oktober",
            "November", "Desember" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waktu_shalat);
        getSupportActionBar().setTitle("Waktu Sholat");

        geocoder = new Geocoder(this, Locale.getDefault());

        gps = new GPSTracker(WaktuShalatActivity.this);
        // check if GPS enabled
        if(gps.canGetLocation()){
            if(gps.getLatitude() == 0 || gps.getLongitude() == 0){
                gps.showSettingsAlert();
            }else{
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
            }
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            postalCode = addresses.get(0).getPostalCode();
            knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
        } catch (IOException e) {
            e.printStackTrace();
            address = "";
            city = "Bandung";
            state = "Jawa Barat";
            country = "Indonesia";
            postalCode = "";
            knownName = "";
        }

        lt		= (TextView) findViewById(R.id.latitude);
        ln		= (TextView) findViewById(R.id.longitude);
        al		= (TextView) findViewById(R.id.alamat);
        DecimalFormat df = new DecimalFormat("#.#####");
        String alamat = city+", "+state+", "+country;
        al.setText(alamat);
        lt.setText(df.format(latitude));
        ln.setText(df.format(longitude));


        mSubuh		= (TextView) findViewById(R.id.subuh_value);
        mZuhur		= (TextView) findViewById(R.id.zuhur_value);
        mAsar		= (TextView) findViewById(R.id.asar_value);
        mMagrib		= (TextView) findViewById(R.id.magrib_value);
        mIsya		= (TextView) findViewById(R.id.isya_value);
        mDate		= (TextView) findViewById(R.id.date_value);
        mlayoutDate	= (RelativeLayout) findViewById(R.id.layout_date);

        timezone = (java.util.Calendar.getInstance().getTimeZone().getOffset(java.util.Calendar.getInstance().getTimeInMillis())) / (1000 * 60 * 60);

        prayers	 = new PrayTimes();

        prayers.setTimeFormat(prayers.Time24);
        prayers.setCalcMethod(prayers.MWL);
        prayers.setAsrJuristic(prayers.Shafii);
        prayers.setAdjustHighLats(prayers.MidNight);
        prayers.setTimeZone(prayers.getTimeZone());

        prayers.setFajrAngle(20.0);
        prayers.setIshaAngle(18.0);

        int[] offsets = { 2, 2, 2, 2, 2, 2, 2 };
        prayers.tune(offsets);

        java.util.Calendar cal	= java.util.Calendar.getInstance();
        year			= cal.get(java.util.Calendar.YEAR);
        month			= cal.get(java.util.Calendar.MONTH);
        day				= cal.get(java.util.Calendar.DAY_OF_MONTH);

        ShowPrayTime(year, month, day);

        mlayoutDate.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });
    }

    private void ShowPrayTime(int year, int month, int day) {

        ArrayList<String> prayerTimes = prayers.getPrayerTimes(year, month, day, latitude, longitude, timezone);

        mSubuh.setText(prayerTimes.get(0));
        mZuhur.setText(prayerTimes.get(2));
        mAsar.setText(prayerTimes.get(3));
        mMagrib.setText(prayerTimes.get(4));
        mIsya.setText(prayerTimes.get(6));

        mDate.setText(day + " " + months[month] + " " + year);
    }

    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            ShowPrayTime(selectedYear, selectedMonth, selectedDay);

            year	= selectedYear;
            month	= selectedMonth;
            day		= selectedDay;

        }
    };
}
