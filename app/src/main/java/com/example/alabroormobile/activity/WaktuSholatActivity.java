package com.example.alabroormobile.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.alabroormobile.R;
import com.example.alabroormobile.helpers.MethodHelper;
import com.example.alabroormobile.helpers.WaktuShalatHelper;
import com.example.alabroormobile.helpers.VarConstants.Constants;

public class WaktuSholatActivity extends AppCompatActivity {

    // ---------------------------------------------------------------------------------------------
    private int countTime;
    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waktu_sholat);
        getSupportActionBar().setTitle("Waktu Sholat");

        // Deklarasi Class helper
        WaktuShalatHelper mWaktuShalatHelper = new WaktuShalatHelper();
        MethodHelper mMethodHelper = new MethodHelper();
        // -----------------------------------------------------------------------------------------
        int jumlahDetikSaatIni = mMethodHelper.getSumWaktuDetik();
        String mJadwal = mWaktuShalatHelper.getJadwalShalat();
        // -----------------------------------------------------------------------------------------
        int detikShubuh = mWaktuShalatHelper.getJmlWaktuShubuh();
        int detikDzuhur = mWaktuShalatHelper.getJmlWaktuDzuhur();
        int detikAshar = mWaktuShalatHelper.getJmlWaktuAshar();
        int detikMaghrib = mWaktuShalatHelper.getJmlWaktuMaghrib();
        int detikIsya = mWaktuShalatHelper.getJmlWaktuIsya();
        int detikAfterMid = mWaktuShalatHelper.getJmlAftMidnight();
        int detikBeforeMid = mWaktuShalatHelper.getJmlBeMidnight();
        // -----------------------------------------------------------------------------------------
        // Deklarasi Elemen XML
        TextView mTextViewCoundown = findViewById(R.id.jadwal_textview_hitungmundur);
        TextView mTextViewWaktuShubuh = findViewById(R.id.jadwal_textview_shubuh);
        TextView mTextViewWaktuDzuhur = findViewById(R.id.jadwal_textview_dzuhur);
        TextView mTextViewWaktuAshar = findViewById(R.id.jadwal_textview_ashar);
        TextView mTextViewWaktuMaghrib = findViewById(R.id.jadwal_textview_maghrib);
        TextView mTextViewWaktuIsya = findViewById(R.id.jadwal_textview_isya);
        TextView mTextViewShalatMendatang = findViewById(R.id.jadwal_textview_shalat);
        // -----------------------------------------------------------------------------------------
        switch (mJadwal) {
            case Constants.SHUBUH:
                mTextViewShalatMendatang.setText(Constants.DZUHUR.substring(7));
                countTime = (detikDzuhur - jumlahDetikSaatIni) * Constants.DETIK_KE_MILI;
                break;
            case Constants.DZUHUR:
                mTextViewShalatMendatang.setText(Constants.ASHAR.substring(7));
                countTime = (detikAshar - jumlahDetikSaatIni) * Constants.DETIK_KE_MILI;
                break;
            case Constants.ASHAR:
                mTextViewShalatMendatang.setText(Constants.MAGHRIB.substring(7));
                countTime = (detikMaghrib - jumlahDetikSaatIni) * Constants.DETIK_KE_MILI;
                break;
            case Constants.MAGHRIB:
                mTextViewShalatMendatang.setText(Constants.ISYA.substring(7));
                countTime = (detikIsya - jumlahDetikSaatIni) * Constants.DETIK_KE_MILI;
                break;
            case Constants.ISYA:
                mTextViewShalatMendatang.setText(Constants.SHUBUH.substring(7));
                if ((jumlahDetikSaatIni == detikAfterMid) || (jumlahDetikSaatIni < detikShubuh)) {
                    countTime = (detikShubuh - jumlahDetikSaatIni) * Constants.DETIK_KE_MILI;
                } else if ((jumlahDetikSaatIni == detikIsya) || (jumlahDetikSaatIni <= detikBeforeMid)) {
                    countTime =  (detikShubuh + detikBeforeMid - jumlahDetikSaatIni) * Constants.DETIK_KE_MILI;
                }
                break;
            default:
                mTextViewShalatMendatang.setText(Constants.DZUHUR.substring(7));
                countTime = (detikDzuhur - jumlahDetikSaatIni) * Constants.DETIK_KE_MILI;
                break;
        }
        // -----------------------------------------------------------------------------------------
        mWaktuShalatHelper.setTimeOnline(mTextViewWaktuShubuh, mTextViewWaktuDzuhur, mTextViewWaktuAshar, mTextViewWaktuMaghrib, mTextViewWaktuIsya);
        mWaktuShalatHelper.CoundownTime(countTime, mTextViewCoundown);
        // -----------------------------------------------------------------------------------------

    }
}
