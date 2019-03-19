package com.example.alabroormobile.model;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alabroormobile.R;
import com.example.alabroormobile.activity.TambahAcaraActivity;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    List<Acara> acaraList;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rl_layout;
        public TextView tv_title, tv_date;

        public MyViewHolder(View view) {
            super(view);
            rl_layout = view.findViewById(R.id.rl_layout);
            tv_title = view.findViewById(R.id.txt_nama_acara);
            tv_date = view.findViewById(R.id.txt_tanggal_acara);
        }
    }

    public RecyclerViewAdapter(List<Acara> acaraList,Activity activity) {
        this.acaraList = acaraList;
        this.mActivity = activity;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Acara movie = acaraList.get(position);

        holder.tv_title.setText(movie.getNama());
        holder.tv_date.setText(movie.getDate());

        holder.rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goDetail = new Intent(mActivity, TambahAcaraActivity.class);
                goDetail.putExtra("id", movie.getKey());
                goDetail.putExtra("nama", movie.getNama());
                goDetail.putExtra("keterangan", movie.getKeterangan());
                goDetail.putExtra("date", movie.getDate());
                goDetail.putExtra("time", movie.getTime());


                mActivity.startActivity(goDetail);


            }
        });

    }

    @Override
    public int getItemCount() {

        return acaraList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView tanggal;

        public ViewHolder(View itemView) {

            super(itemView);

            title = (TextView) itemView.findViewById(R.id.txt_nama_acara);

            tanggal = (TextView) itemView.findViewById(R.id.txt_tanggal_acara);
        }
    }
}