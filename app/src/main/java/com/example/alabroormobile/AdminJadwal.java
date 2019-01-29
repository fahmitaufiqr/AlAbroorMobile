package com.example.alabroormobile;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminJadwal extends Fragment {


    public AdminJadwal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_jadwal, container, false);

        //inisialisasi
        CardView imamBt = (CardView) view.findViewById(R.id.jadwalImam);
        CardView muadzinBt = (CardView) view.findViewById(R.id.jadwalMuadzin);
        CardView pengajianBt = (CardView) view.findViewById(R.id.jadwalPengajian);
        CardView ramadhanBt = (CardView) view.findViewById(R.id.jadwalRamadhan);
        Button imamTbh = (Button) view.findViewById(R.id.btn_tambah_imam);
        Button muadzinTbh = (Button) view.findViewById(R.id.btn_tambah_muadzin);
        Button pengajianTbh = (Button) view.findViewById(R.id.btn_tambah_pengajian);
        Button ramadhanTbh = (Button) view.findViewById(R.id.btn_tambah_ramadhan);

        //CardView IMAM
        imamBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),JadwalKesediaan.class);
                startActivity(intent);
            }
        });

        imamTbh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AdminTambahPetugas.class);
                startActivity(intent);
            }
        });

        //CardView MUADZIN
        muadzinBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),JadwalKesediaan.class);
                startActivity(intent);
            }
        });

        muadzinTbh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AdminTambahPetugas.class);
                startActivity(intent);
            }
        });

        //CardView PENGAJIAN
        pengajianBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),JadwalPengajian.class);
                startActivity(intent);
            }
        });

        pengajianTbh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AdminTambahPengajian.class);
                startActivity(intent);
            }
        });

        //CardView RAMADHAN
        ramadhanBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),JadwalRamadhan.class);
                startActivity(intent);
            }
        });

        ramadhanTbh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AdminTambahRamadhan.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
