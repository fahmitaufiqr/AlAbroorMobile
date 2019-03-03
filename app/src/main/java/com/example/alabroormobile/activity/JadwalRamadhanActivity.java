package com.example.alabroormobile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alabroormobile.R;
import com.example.alabroormobile.calendar.HomeCollection;
import com.example.alabroormobile.calendar.HwAdapter;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class JadwalRamadhanActivity extends AppCompatActivity {
    public GregorianCalendar cal_month2, cal_month_copy2;
    private HwAdapter hwAdapter;
    private TextView tv_month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_ramadhan);
        getSupportActionBar().setTitle("Jadwal Ramadhan");

        HomeCollection.date_collection_arr=new ArrayList<HomeCollection>();
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-07" ,"Puasa","Hari ke - 1"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-08" ,"Puasa","Hari ke - 2"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-09" ,"Puasa","Hari ke - 3"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-10" ,"Puasa","Hari ke - 4"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-11" ,"Puasa","Hari ke - 5"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-12" ,"Puasa","Hari ke - 6"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-13" ,"Puasa","Hari ke - 7"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-14" ,"Puasa","Hari ke - 8"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-15" ,"Puasa","Hari ke - 9"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-16" ,"Puasa","Hari ke - 10"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-17" ,"Puasa","Hari ke - 11"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-18" ,"Puasa","Hari ke - 12"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-19" ,"Puasa","Hari ke - 13"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-20" ,"Puasa","Hari ke - 14"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-21" ,"Puasa","Hari ke - 15"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-22" ,"Puasa","Hari ke - 16"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-23" ,"Puasa","Hari ke - 17"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-24" ,"Puasa","Hari ke - 18"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-25" ,"Puasa","Hari ke - 19"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-26" ,"Puasa","Hari ke - 20"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-27" ,"Puasa","Hari ke - 21"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-28" ,"Puasa","Hari ke - 22"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-29" ,"Puasa","Hari ke - 23"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-30" ,"Puasa","Hari ke - 24"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-05-31" ,"Puasa","Hari ke - 25"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-06-01" ,"Puasa","Hari ke - 26"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-06-02" ,"Puasa","Hari ke - 27"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-06-03" ,"Puasa","Hari ke - 28"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2019-06-04" ,"Puasa","Hari ke - 29"));


        cal_month2 = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy2 = (GregorianCalendar) cal_month2.clone();
        hwAdapter = new HwAdapter(this, cal_month2,HomeCollection.date_collection_arr);

        tv_month = (TextView) findViewById(R.id.tv_month2);
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month2));


        ImageButton previous = (ImageButton) findViewById(R.id.ib_prev2);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month2.get(GregorianCalendar.MONTH) == 4&&cal_month2.get(GregorianCalendar.YEAR)==2017) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(JadwalRamadhanActivity.this, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                }
                else {
                    setPreviousMonth();
                    refreshCalendar();
                }


            }
        });
        ImageButton next = (ImageButton) findViewById(R.id.lb_next2);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month2.get(GregorianCalendar.MONTH) == 5&&cal_month2.get(GregorianCalendar.YEAR)==2018) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(JadwalRamadhanActivity.this, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                }
                else {
                    setNextMonth();
                    refreshCalendar();
                }
            }
        });
        GridView gridview = (GridView) findViewById(R.id.gv_calendar2);
        gridview.setAdapter(hwAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selectedGridDate = HwAdapter.day_string.get(position);
                ((HwAdapter) parent.getAdapter()).getPositionList(selectedGridDate, JadwalRamadhanActivity.this);
            }

        });
    }
    protected void setNextMonth() {
        if (cal_month2.get(GregorianCalendar.MONTH) == cal_month2.getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month2.set((cal_month2.get(GregorianCalendar.YEAR) + 1), cal_month2.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month2.set(GregorianCalendar.MONTH,
                    cal_month2.get(GregorianCalendar.MONTH) + 1);
        }
    }

    protected void setPreviousMonth() {
        if (cal_month2.get(GregorianCalendar.MONTH) == cal_month2.getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month2.set((cal_month2.get(GregorianCalendar.YEAR) - 1), cal_month2.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month2.set(GregorianCalendar.MONTH, cal_month2.get(GregorianCalendar.MONTH) - 1);
        }
    }

    public void refreshCalendar() {
        hwAdapter.refreshDays();
        hwAdapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month2));
    }
}
