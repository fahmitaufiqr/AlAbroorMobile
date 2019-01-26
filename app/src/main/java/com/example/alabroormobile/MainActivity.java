package com.example.alabroormobile;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private viewPageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set View Page Start
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager =(ViewPager) findViewById(R.id.content);
        adapter = new viewPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserHome(),"MENU");
        adapter.addFragment(new UserJadwal(),"JADWAL");
        adapter.addFragment(new UserMasjid(),"MASJID");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_jadwal);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_masjid);
        //View Page END
    }
}
