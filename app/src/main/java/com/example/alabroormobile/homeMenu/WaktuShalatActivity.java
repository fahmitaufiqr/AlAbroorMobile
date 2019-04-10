package com.example.alabroormobile.homeMenu;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.alabroormobile.R;
import com.example.alabroormobile.homeMenu.ArahKiblat.GPSTracker;
import com.example.alabroormobile.homeMenu.DaftarPengurus.Pengurus;
import com.example.alabroormobile.model.PrayTimes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class WaktuShalatActivity extends AppCompatActivity {

    private ProgressDialog loading;
    PrayTimes prayers;
    TextView mSubuh, mZuhur, mAsar, mMagrib, mIsya, mDate,lt,ln,al,date_view;
    RelativeLayout mlayoutDate;
    String address,city,state,country,postalCode,knownName;
    GPSTracker gps;
    Geocoder geocoder;
    List<Address> addresses;
    double timezone;
    DatabaseReference dRJadwalShalat;

    /* Lokasi Daerah Bandung dan Sekitarnya */
    double latitude = -6.974086;
    double longitude = 107.630305;

    int year, month, day;

    public String[] months = { "Januari", "Februari", "March", "April",
            "Mei", "Juni", "Juli", "Augustus", "September", "Oktober",
            "November", "Desember" };

    FirebaseAuth mAuth;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waktu_shalat);
        getSupportActionBar().setTitle("Waktu Shalat");

        loading = ProgressDialog.show(WaktuShalatActivity.this,
                null,
                "Mengambil Data...",
                true,
                false);

        date_view = findViewById(R.id.date_vieww);
        Date Datee = new Date();
        SimpleDateFormat format = new SimpleDateFormat("EEEE, dd MMM yyyy");
        String formattanggal = format.format(Datee);
        date_view.setText(formattanggal);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //CEK USER ADMIN =====================================================================
        String username = currentUser.getEmail().split("@")[0];
        DatabaseReference dbuserA = FirebaseDatabase.getInstance().getReference("Pengurus").child(username);
        dbuserA.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pengurus pengurus = dataSnapshot.getValue(Pengurus.class);

                if (pengurus.getStatus().equals("Admin")){
                    mDate.setVisibility(View.VISIBLE);
                    date_view.setVisibility(View.GONE);

                }else {
                    mDate.setVisibility(View.GONE);
                    date_view.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.dismiss();
            }
        });

        //====================================================================================================

        geocoder = new Geocoder(this, Locale.getDefault());
        gps = new GPSTracker(WaktuShalatActivity.this);

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            postalCode = addresses.get(0).getPostalCode();
            knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
        }
        catch (IOException e) {
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
        countDown();

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWaktuShalatPopUp();
            }
        });
    }

    private void countDown(){
        String givenDateString = "04:32";
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        try {
            Date mDate = sdf.parse(givenDateString);
            long time = mDate.getTime();
        }catch (ParseException e){

        }
    }

    private void ShowPrayTime(int year, int month, int day) {
        ArrayList<String> prayerTimes = prayers.getPrayerTimes(year, month, day, latitude, longitude, timezone);
        mDate.setText("Sesuaikan Waktu Shalat");
        String convert;
        gantihari(day + "-" + months[month] + "-" + year,
                prayerTimes.get(0),
                prayerTimes.get(2),
                prayerTimes.get(3),
                prayerTimes.get(4),
                prayerTimes.get(6));
        showJadwal();
        Log.d("subuh", prayerTimes.get(0));
    }

    private void setJadwal(String tanggal, String subuh, String dzuhur, String ashar, String maghrib, String isya){
        dRJadwalShalat = FirebaseDatabase.getInstance().getReference("WaktuShalat");
        HashMap<String, String> dataWaktuShalat = new HashMap<>();
        dataWaktuShalat.put("tanggal", tanggal);
        dataWaktuShalat.put("subuh", subuh);
        dataWaktuShalat.put("dzuhur", dzuhur);
        dataWaktuShalat.put("ashar", ashar);
        dataWaktuShalat.put("maghrib", maghrib);
        dataWaktuShalat.put("isya", isya);

        dRJadwalShalat.setValue(dataWaktuShalat).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        });
    }

    private void showJadwal(){
        ArrayList<String> waktuSholat = new ArrayList<>();
        dRJadwalShalat = FirebaseDatabase.getInstance().getReference("WaktuShalat");
        dRJadwalShalat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    String jadwalnya = d.getValue(String.class);
                    waktuSholat.add(jadwalnya);
                }
                mSubuh.setText(waktuSholat.get(4));
                mZuhur.setText(waktuSholat.get(1));
                mAsar.setText(waktuSholat.get(0));
                mMagrib.setText(waktuSholat.get(3));
                mIsya.setText(waktuSholat.get(2));

                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.dismiss();
            }
        });
    }

    private boolean gantihari(String currentDate, String subuh, String dzuhur, String ashar, String maghrib, String isya) {
        dRJadwalShalat = FirebaseDatabase.getInstance().getReference("WaktuShalat").child("tanggal");
        dRJadwalShalat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String isiTanggal =  dataSnapshot.getValue(String.class);
                if (!isiTanggal.equals(currentDate)){
                    setJadwal(currentDate,
                            subuh,
                            dzuhur,
                            ashar,
                            maghrib,
                            isya);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return false;
    }

    private void editWaktuShalatPopUp(){
        Dialog popUp = new Dialog(WaktuShalatActivity.this,
                android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth
        );
        popUp.setContentView(R.layout.pop_up_edit_waktu_shalat);
        EditText et_sSubuh = (EditText) popUp.findViewById(R.id.et_sSubuh);
        EditText et_sDzuhur = (EditText) popUp.findViewById(R.id.et_sDzuhur);
        EditText et_sAshar = (EditText) popUp.findViewById(R.id.et_sAshar);
        EditText et_sMaghrib = (EditText) popUp.findViewById(R.id.et_sMaghrib);
        EditText et_sIsya = (EditText) popUp.findViewById(R.id.et_sIsya);
        Button btn_cancel = (Button) popUp.findViewById(R.id.btn_cancel);
        Button btn_reset = (Button) popUp.findViewById(R.id.btn_reset);
        Button btn_save = (Button) popUp.findViewById(R.id.btn_save);
        ArrayList<String> waktuSholat = new ArrayList<>();
        dRJadwalShalat = FirebaseDatabase.getInstance().getReference("WaktuShalat");
        dRJadwalShalat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    String jadwalnya = d.getValue(String.class);
                    waktuSholat.add(jadwalnya);
                }
                et_sSubuh.setText(waktuSholat.get(4));
                et_sDzuhur.setText(waktuSholat.get(1));
                et_sAshar.setText(waktuSholat.get(0));
                et_sMaghrib.setText(waktuSholat.get(3));
                et_sIsya.setText(waktuSholat.get(2));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.dismiss();
            }
        });

        //ngambil data dari api untuk reset
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

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> prayerTimes = prayers.getPrayerTimes(year, month, day, latitude, longitude, timezone);
                setJadwal(day + "-" + months[month] + "-" + year,
                        prayerTimes.get(0),
                        prayerTimes.get(2),
                        prayerTimes.get(3),
                        prayerTimes.get(4),
                        prayerTimes.get(6));
                showJadwal();
                popUp.dismiss();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String waktuSubuh = et_sSubuh.getText().toString();
                String waktuDzuhur = et_sDzuhur.getText().toString();
                String waktuAshar = et_sAshar.getText().toString();
                String waktuMaghrib = et_sMaghrib.getText().toString();
                String waktuIsya = et_sIsya.getText().toString();
                String tanggal = day + "-" + months[month] + "-" + year;

                setJadwal(tanggal, waktuSubuh, waktuDzuhur, waktuAshar, waktuMaghrib, waktuIsya);
                showJadwal();
                popUp.dismiss();
            }
        });

        popUp.show();
    }
}
