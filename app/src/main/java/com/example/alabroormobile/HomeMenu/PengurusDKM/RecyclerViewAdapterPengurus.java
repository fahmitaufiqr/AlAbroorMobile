package com.example.alabroormobile.HomeMenu.PengurusDKM;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alabroormobile.model.Pengurus;
import com.example.alabroormobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecyclerViewAdapterPengurus extends RecyclerView.Adapter<RecyclerViewAdapterPengurus.MyViewHolderPengurus> {

    List<Pengurus> pengurusList;
    private Activity mActivity;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    public class MyViewHolderPengurus extends RecyclerView.ViewHolder {
        public LinearLayout rl_layout_pengurus;
        public TextView tv_nama_pengurus, tv_email_pengurus;

        public MyViewHolderPengurus(View view) {
            super(view);
            rl_layout_pengurus = view.findViewById(R.id.rl_layout_pengurus);
            tv_nama_pengurus = view.findViewById(R.id.nama_pengurus);
            tv_email_pengurus = view.findViewById(R.id.email_pengurus);
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


        //CEK USER ADMIN =====================================================================
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        String username = currentUser.getEmail().split("@")[0];
        DatabaseReference dbuserA = FirebaseDatabase.getInstance().getReference("Pengurus").child(username);
        dbuserA.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pengurus pengurus = dataSnapshot.getValue(Pengurus.class);

                if (pengurus.getStatus().equals("Admin")){
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

                }else {


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    @Override
    public int getItemCount() {
        return pengurusList.size();
    }
}
