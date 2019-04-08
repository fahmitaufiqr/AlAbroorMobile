package com.example.alabroormobile.homeMenu.Acara.JadwalPengajian;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alabroormobile.R;

import java.util.List;

public class RecyclerViewAdapterPengajian extends RecyclerView.Adapter<RecyclerViewAdapterPengajian.MyViewHolder> {

    List<Pengajian> pengajianList;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rl_layout;
        public TextView tv_title, tv_date,tv_time,tv_desk,tv_pengirim;

        public MyViewHolder(View view) {
            super(view);
            rl_layout = view.findViewById(R.id.rl_layout);
            tv_title = view.findViewById(R.id.txt_nama_acara);
            tv_date = view.findViewById(R.id.txt_tanggal_acara);
            tv_desk = view.findViewById(R.id.txt_keterangan);
            tv_time = view.findViewById(R.id.txt_waktu_acara);
            tv_pengirim = view.findViewById(R.id.pengirim_view);
        }
    }

    public RecyclerViewAdapterPengajian(List<Pengajian> pengajianList, Activity activity) {
        this.pengajianList = pengajianList;
        this.mActivity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_acara, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Pengajian movie = pengajianList.get(position);
        holder.tv_title.setText(movie.getNama());
        holder.tv_date.setText(movie.getDate());
        holder.tv_desk.setText(movie.getKeterangan());
        holder.tv_time.setText(movie.getTime());
        holder.tv_pengirim.setText(movie.getPengirim());
        holder.rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goDetail = new Intent(mActivity, TambahPengajianActivity.class);
                goDetail.putExtra("id", movie.getKey());
                goDetail.putExtra("nama", movie.getNama());
                goDetail.putExtra("keterangan", movie.getKeterangan());
                goDetail.putExtra("date", movie.getDate());
                goDetail.putExtra("time", movie.getTime());
                goDetail.putExtra("pengirim", movie.getPengirim());

                mActivity.startActivity(goDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pengajianList.size();
    }
}