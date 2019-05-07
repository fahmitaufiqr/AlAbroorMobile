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
        holder.tanggal.setText(listitem.getTanggal());
        holder.imamTarawih.setText(listitem.getImam());
        holder.qultum.setText(listitem.getQultum());

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout recviewRamadhann;
        public TextView tanggal,imamTarawih,qultum;
        public ViewHolder(View itemview){
            super(itemview);
            recviewRamadhann = itemview.findViewById(R.id.ramadhan_layout);
            tanggal = itemview.findViewById(R.id.tanggal_puasa);
            imamTarawih=itemview.findViewById(R.id.nama_imam);
            qultum = itemview.findViewById(R.id.nama_qultum);
        }

    }
}
