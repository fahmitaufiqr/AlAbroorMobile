package com.example.alabroormobile.homeMenu.Acara.JadwalPengajian;

/**
 * Created by Aulia Ikvanda on 10,April,2019
 */
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alabroormobile.R;

import java.util.ArrayList;

public class PengajianAdapter extends RecyclerView.Adapter<PengajianAdapter.ViewHolder> {

    private ClickHandler mClickHandler;
    private ArrayList<PengajianModel> mPengajian;
    private ArrayList<String> mPengajianId;
    private ArrayList<String> mSelectedId;
    private View mEmptyView;
    private Context mContext;

    public PengajianAdapter(Context context, ArrayList<PengajianModel> pengajians, ArrayList<String> dataId,
                            View emptyView, ClickHandler handler) {
        mContext = context;
        mPengajian = pengajians;
        mPengajianId = dataId;
        mEmptyView = emptyView;
        mClickHandler = handler;
        mSelectedId = new ArrayList<>();
    }

    public void updateEmptyView() {
        if (mPengajian.size() == 0)
            mEmptyView.setVisibility(View.VISIBLE);
        else
            mEmptyView.setVisibility(View.GONE);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(
                R.layout.list_item_acara, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        PengajianModel pengajian = mPengajian.get(position);
        holder.tv_title.setText(pengajian.getNama());
        holder.tv_date.setText(pengajian.getDate());
        holder.tv_desk.setText(pengajian.getKeterangan());
        holder.tv_time.setText(pengajian.getTime());
        holder.tv_pengirim.setText(pengajian.getPengirim());
        holder.itemView.setSelected(mSelectedId.contains(mPengajianId.get(position)));

    }

    @Override
    public int getItemCount() {
        return mPengajian.size();
    }

    public void toggleSelection(String dataId) {
        if (mSelectedId.contains(dataId))
            mSelectedId.remove(dataId);
        else
            mSelectedId.add(dataId);
        notifyDataSetChanged();
    }

    public int selectionCount() {
        return mSelectedId.size();
    }

    public void resetSelection() {
        mSelectedId = new ArrayList<>();
        notifyDataSetChanged();
    }

    public ArrayList<String> getSelectedId() {
        return mSelectedId;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener,
            View.OnLongClickListener {
        public TextView tv_title, tv_date,tv_time,tv_desk,tv_pengirim;

        ViewHolder(View view) {
            super(view);
            tv_title = view.findViewById(R.id.txt_nama_acara);
            tv_date = view.findViewById(R.id.txt_tanggal_acara);
            tv_desk = view.findViewById(R.id.txt_keterangan);
            tv_time = view.findViewById(R.id.txt_waktu_acara);
            tv_pengirim = view.findViewById(R.id.pengirim_view);

            view.setFocusable(true);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View itemView) {
            mClickHandler.onItemClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            return mClickHandler.onItemLongClick(getAdapterPosition());
        }
    }

    interface ClickHandler {
        void onItemClick(int position);
        boolean onItemLongClick(int position);
    }
}
