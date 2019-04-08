package com.example.alabroormobile.homeMenu.Acara.Ramadhan;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alabroormobile.R;

import java.util.List;

/**
 * Created by Aulia Ikvanda on 31,March,2019
 */
public class RamadhanAdapter extends RecyclerView.Adapter<RamadhanAdapter.ViewHolder>{

    private List<RamadhanModel> listitems;
    private Activity mActivity;

    public RamadhanAdapter(List<RamadhanModel> listitems, Activity activity) {
        this.listitems = listitems;
        this.mActivity = activity;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ramadhan,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final RamadhanModel listitem = listitems.get(position);
        holder.ramadhan_ke.setText(listitem.getHariKe());
        holder.tanggal_puasa.setText(listitem.getTanggal());
        holder.waktu_buka.setText(listitem.getBuka());
        holder.waktu_sahur.setText(listitem.getSahur());
        holder.penceramah.setText(listitem.getPenceramah());

        holder.recviewRamadhann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goDetail = new Intent(mActivity, DetailRamadhanActivity.class);
                goDetail.putExtra("id", position+1);
                goDetail.putExtra("buka", listitem.getBuka());
                goDetail.putExtra("sahur", listitem.getSahur());
                goDetail.putExtra("penceramah", listitem.getPenceramah());
                goDetail.putExtra("tanggal", listitem.getTanggal());
                goDetail.putExtra("hariKe", listitem.getHariKe());

                mActivity.startActivity(goDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout recviewRamadhann;
        public TextView ramadhan_ke,tanggal_puasa,waktu_sahur,waktu_buka,penceramah;
        public ViewHolder(View itemview){
            super(itemview);
            recviewRamadhann = itemview.findViewById(R.id.ramadhan_layout);
            ramadhan_ke = itemview.findViewById(R.id.ramadhan_ke);
            tanggal_puasa=itemview.findViewById(R.id.tanggal_puasa);
            waktu_buka = itemview.findViewById(R.id.sahur_time);
            waktu_sahur=itemview.findViewById(R.id.buka_time);
            penceramah = itemview.findViewById(R.id.penceramah);
        }

    }
}
