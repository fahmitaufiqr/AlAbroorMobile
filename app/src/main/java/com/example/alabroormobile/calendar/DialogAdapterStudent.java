package com.example.alabroormobile.calendar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alabroormobile.R;

import java.util.ArrayList;

class DialogAdapterStudent extends BaseAdapter {
    private Activity context;
    private ArrayList<Dialogpojo> alCustom;

    public DialogAdapterStudent(Activity context, ArrayList<Dialogpojo> alCustom) {
        this.context = context;
        this.alCustom = alCustom;
    }

    @Override
    public int getCount() {
        return alCustom.size();

    }

    @Override
    public Object getItem(int i) {
        return alCustom.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.row_addapt, null, true);

        TextView tvTitle=(TextView)listViewItem.findViewById(R.id.tv_name);
        TextView tvDescription=(TextView)listViewItem.findViewById(R.id.tv_desc);


        tvTitle.setText("Title : "+alCustom.get(position).getTitles());
        tvDescription.setText("Description : "+alCustom.get(position).getDescripts());

        return  listViewItem;
    }
}
