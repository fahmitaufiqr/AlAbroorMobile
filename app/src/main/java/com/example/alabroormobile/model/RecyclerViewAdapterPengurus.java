package com.example.alabroormobile.model;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alabroormobile.R;
import com.example.alabroormobile.activity.Pengurus.TambahPengurusActivity;

import java.util.List;

public class RecyclerViewAdapterPengurus extends RecyclerView.Adapter<RecyclerViewAdapterPengurus.MyViewHolderPengurus> {

    List<Pengurus> pengurusList;
    private Activity mActivity;

    public class MyViewHolderPengurus extends RecyclerView.ViewHolder {
        public LinearLayout rl_layout_pengurus;
        public TextView tv_nama_pengurus, tv_email_pengurus, tv_status;

        public MyViewHolderPengurus(View view) {
            super(view);
            rl_layout_pengurus = view.findViewById(R.id.rl_layout_pengurus);
            tv_nama_pengurus = view.findViewById(R.id.nama_pengurus);
            tv_email_pengurus = view.findViewById(R.id.email_pengurus);
            tv_status = view.findViewById(R.id.status);
        }
    }

    public RecyclerViewAdapterPengurus(List<Pengurus> pengurusList,Activity activity) {
        this.pengurusList = pengurusList;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public MyViewHolderPengurus onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pengurus, parent, false);
        return new MyViewHolderPengurus(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderPengurus holder, int position) {
        final Pengurus movie = pengurusList.get(position);

        holder.tv_nama_pengurus.setText(movie.getNama());
        holder.tv_email_pengurus.setText(movie.getEmail());
        holder.tv_status.setText(movie.getStatus());
        holder.rl_layout_pengurus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goDetail = new Intent(mActivity, TambahPengurusActivity.class);
                goDetail.putExtra("id", movie.getKey());
                goDetail.putExtra("nama", movie.getNama());
                goDetail.putExtra("email", movie.getEmail());
                goDetail.putExtra("status", movie.getStatus());

                mActivity.startActivity(goDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pengurusList.size();
    }
}
