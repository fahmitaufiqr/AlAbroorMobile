package com.example.alabroormobile.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alabroormobile.R;
import com.example.alabroormobile.model.Acara;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JadwalPengajianActivity extends AppCompatActivity {

    RecycleAdapter adapter;
    ArrayList<Acara> acaraList;
    private Context x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_pengajian);
        getSupportActionBar().setTitle("Jadwal Pengajian");

        // FAB SETUP
        FloatingActionButton fabAdd = (FloatingActionButton)findViewById(R.id.fab_add_acara);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JadwalPengajianActivity.this, TambahAcaraActivity.class));
            }
        });

        acaraList = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv_jadwal_pengajian);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        adapter = new RecycleAdapter();
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    //LOAD DATA
    @Override
    protected void onResume() {
        super.onResume();
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            database.getReference("acaraList").addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            acaraList.clear();

                            Log.w("Data Acara", "getUser:onCancelled " + dataSnapshot.toString());
                            Log.w("Data Acara", "count = " + String.valueOf(dataSnapshot.getChildrenCount()) + " values " + dataSnapshot.getKey());
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                Acara acaraa = data.getValue(Acara.class);
                                acaraList.add(acaraa);
                            }

                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("Data Acara", "getUser:onCancelled", databaseError.toException());
                        }
                    });

    }

    //ADAPTER RECYCLER VIEW SETUP
    private class RecycleAdapter extends RecyclerView.Adapter {
        @Override
        public int getItemCount() {
            return acaraList.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            SimpleItemViewHolder pvh = new SimpleItemViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SimpleItemViewHolder viewHolder = (SimpleItemViewHolder) holder;
            viewHolder.position = position;
            Acara acaraa = acaraList.get(position);
            ((SimpleItemViewHolder) holder).title.setText(acaraa.getNama());
            ((SimpleItemViewHolder) holder).tanggal.setText(acaraa.getDate());
        }

        public final  class SimpleItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView title;
            TextView tanggal;
            public int position;
            public SimpleItemViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                title = (TextView) itemView.findViewById(R.id.txt_nama_acara);
                tanggal = (TextView) itemView.findViewById(R.id.txt_tanggal_acara);
            }

            //JIKA LIST DI KLIK
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(JadwalPengajianActivity.this, ViewPengajianActivity.class);
                newIntent.putExtra("acara", acaraList.get(position));
                JadwalPengajianActivity.this.startActivity(newIntent);
            }
        }
    }


}