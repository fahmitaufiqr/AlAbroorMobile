package com.example.alabroormobile.homeMenu.JadwalPetugas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.alabroormobile.R;
import com.example.alabroormobile.model.Pengurus;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class JadwalPetugasActivity extends AppCompatActivity {

    private TextView tv_muazin1, tv_imam1, tv_muazin2, tv_imam2, tv_muazin3, tv_imam3, tv_muazin4, tv_imam4, tv_muazin5, tv_imam5;
    private ImageView im_su, im_dz, im_as, im_mg, im_is;
    private TextDrawable mDrawableBuilderSu, mDrawableBuilderDz, mDrawableBuilderAs, mDrawableBuilderMg, mDrawableBuilderIs;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private CalendarView cv_jadwal_petugas;
    private String sendTanggal;
    String su = "Su";
    String dz = "Dz";
    String as = "As";
    String mg = "Mg";
    String is = "Is";
    int color = mColorGenerator.getRandomColor();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_jadwal_petugas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.tambah_jadwal_petugas){
            Intent intent = new Intent(JadwalPetugasActivity.this, TambahJadwalPetugasActivity.class);
            intent.putExtra("dataTanggal", sendTanggal);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    FirebaseUser currentUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_petugas);
        getSupportActionBar().setTitle("Jadwal Petugas");

        Calendar cal	= java.util.Calendar.getInstance();
        sendTanggal = cal.get(Calendar.DAY_OF_MONTH)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.YEAR);

        //inisialisasi
        cv_jadwal_petugas = (CalendarView) findViewById(R.id.cv_jadwal_petugas);
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

        //setColor
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

        cv_jadwal_petugas.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(JadwalPetugasActivity.this, "Year: "+year+ "\nMonth: "+month+ "\nDay of Month: "+dayOfMonth, Toast.LENGTH_SHORT).show();
                sendTanggal = dayOfMonth+"-"+month+"-"+year;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //CEK USER ADMIN =====================================================================
        String titik = ".";
        String username = currentUser.getEmail().split("@")[0];
        DatabaseReference dbuserA = FirebaseDatabase.getInstance().getReference("Pengurus").child(username);
        dbuserA.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pengurus pengurus = dataSnapshot.getValue(Pengurus.class);

                if (pengurus.getStatus().equals("Admin")){

                }else {

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //====================================================================================================

    }
}
