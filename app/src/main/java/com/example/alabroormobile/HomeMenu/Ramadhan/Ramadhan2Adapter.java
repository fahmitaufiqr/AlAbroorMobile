package com.example.alabroormobile.HomeMenu.Ramadhan;

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

import java.util.List;

/**
 * Created by Aulia Ikvanda on 23,June,2019
 */
public class Ramadhan2Adapter extends RecyclerView.Adapter<Ramadhan2Adapter.ViewHolder> {

    private List<Ramadhan2Model> listitems;
    private Activity mActivity;

    public Ramadhan2Adapter(List<Ramadhan2Model> listitems, Activity activity) {
        this.listitems = listitems;
        this.mActivity = activity;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ramadhan, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Ramadhan2Model listitem = listitems.get(position);
        holder.hari_ke.setText(listitem.getHariKe());
        holder.imam.setText(listitem.getImam());
        holder.penceramah.setText(listitem.getQultum());

        holder.recviewRamadhann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goDetail = new Intent(mActivity, DetailRamadhanActivity.class);
                goDetail.putExtra("id", position + 1);
                goDetail.putExtra("qultum", listitem.getQultum());
                goDetail.putExtra("imam", listitem.getImam());
                goDetail.putExtra("hariKe", listitem.getHariKe());

                mActivity.startActivity(goDetail);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout recviewRamadhann;
        public TextView hari_ke, imam, penceramah;

        public ViewHolder(View itemview) {
            super(itemview);
            recviewRamadhann = itemview.findViewById(R.id.ramadhan_layout);
            hari_ke = itemview.findViewById(R.id.hari_ke);
            penceramah = itemview.findViewById(R.id.nama_qultum);
            imam = itemview.findViewById(R.id.nama_imam);
        }

    }
}
