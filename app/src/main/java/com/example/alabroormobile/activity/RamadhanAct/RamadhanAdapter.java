package com.example.alabroormobile.activity.RamadhanAct;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alabroormobile.R;

import java.util.List;

/**
 * Created by Aulia Ikvanda on 31,March,2019
 */
public class RamadhanAdapter extends RecyclerView.Adapter<RamadhanAdapter.ViewHolder>{

    private List<RamadhanItem> listitems;
    private Context mcontext;

    public RamadhanAdapter(List<RamadhanItem> listitems, Context mcontext) {
        this.listitems = listitems;
        this.mcontext = mcontext;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ramadhan_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final RamadhanItem listitem = listitems.get(position);
        holder.texttittle.setText(listitem.getItem());
        holder.textDesc.setText(listitem.getDecription());
        holder.days.setText(listitem.getDays());
        holder.seh.setText(listitem.getSeh_time());
        holder.ift.setText(listitem.getIft_time());
        holder.mar.setText(listitem.getMark());
    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView texttittle,textDesc,days,seh,ift,mar;
        public ViewHolder(View itemview){
            super(itemview);
            texttittle = itemview.findViewById(R.id.text1);
            textDesc=itemview.findViewById(R.id.text2);
            days = itemview.findViewById(R.id.days);
            seh=itemview.findViewById(R.id.seh_time);
            ift = itemview.findViewById(R.id.ift_time);
            mar=itemview.findViewById(R.id.textView);
            //textopt=itemview.findViewById(R.id.texopt);

        }

    }
}
