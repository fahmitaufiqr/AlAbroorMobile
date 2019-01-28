package com.example.alabroormobile;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;


public class MainAdminActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private viewPageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        //Set View Page Start
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager =(ViewPager) findViewById(R.id.content);
        adapter = new viewPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new AdminHome(),"MENU");
        adapter.addFragment(new AdminJadwal(),"JADWAL");
        adapter.addFragment(new AdminMasjid(),"MASJID");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_jadwal);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_masjid);
        //View Page END


    }
}
