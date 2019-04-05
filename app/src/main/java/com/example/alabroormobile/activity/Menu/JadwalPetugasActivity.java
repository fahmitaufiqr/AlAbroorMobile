package com.example.alabroormobile.activity.Menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.alabroormobile.R;

public class JadwalPetugasActivity extends AppCompatActivity {

    private TextView tv_muazin1, tv_imam1, tv_muazin2, tv_imam2, tv_muazin3, tv_imam3, tv_muazin4, tv_imam4, tv_muazin5, tv_imam5;
    private ImageView im_su, im_dz, im_as, im_mg, im_is;
    private String su, dz, as, mg, is;
    private TextDrawable mDrawableBuilderSu, mDrawableBuilderDz, mDrawableBuilderAs, mDrawableBuilderMg, mDrawableBuilderIs;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_petugas);
        getSupportActionBar().setTitle("Jadwal Petugas");

        tv_muazin1 = (TextView) findViewById(R.id.tv_muazin1);
        tv_muazin2 = (TextView) findViewById(R.id.tv_muazin2);
        tv_muazin3 = (TextView) findViewById(R.id.tv_muazin3);
        tv_muazin4 = (TextView) findViewById(R.id.tv_muazin4);
        tv_muazin5 = (TextView) findViewById(R.id.tv_muazin5);
        tv_imam1 = (TextView) findViewById(R.id.tv_imam1);
        tv_imam2 = (TextView) findViewById(R.id.tv_imam2);
        tv_imam3 = (TextView) findViewById(R.id.tv_imam3);
        tv_imam4 = (TextView) findViewById(R.id.tv_imam4);
        tv_imam5 = (TextView) findViewById(R.id.tv_imam5);
        im_su = (ImageView) findViewById(R.id.iv_su);
        im_dz = (ImageView) findViewById(R.id.iv_dz);
        im_as = (ImageView) findViewById(R.id.iv_as);
        im_mg = (ImageView) findViewById(R.id.iv_mg);
        im_is = (ImageView) findViewById(R.id.iv_is);

        int color = mColorGenerator.getRandomColor();
        su = "Su";
        dz = "Dz";
        as = "As";
        mg = "Mg";
        is = "Is";
        mDrawableBuilderSu = TextDrawable.builder().buildRound(su, color);
        mDrawableBuilderDz = TextDrawable.builder().buildRound(dz, color);
        mDrawableBuilderAs = TextDrawable.builder().buildRound(as, color);
        mDrawableBuilderMg = TextDrawable.builder().buildRound(mg, color);
        mDrawableBuilderIs = TextDrawable.builder().buildRound(is, color);
        im_su.setImageDrawable(mDrawableBuilderSu);
        im_dz.setImageDrawable(mDrawableBuilderDz);
        im_as.setImageDrawable(mDrawableBuilderAs);
        im_mg.setImageDrawable(mDrawableBuilderMg);
        im_is.setImageDrawable(mDrawableBuilderIs);
    }
}
