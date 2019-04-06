package com.example.alabroormobile.AdminController;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.alabroormobile.R;
import com.example.alabroormobile.activity.GoogleLogin.UserModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Aulia Ikvanda on 06,April,2019
 */
public class userAdapter extends RecyclerView.Adapter<userAdapter.MyViewHolderUser> {
    List<UserModel> userList;
    private Activity mActivity;

    private String sPid;

    public class MyViewHolderUser extends RecyclerView.ViewHolder {
        public LinearLayout rl_layout_user;
        public TextView tv_nama_user, tv_email_user, tv_status, buttonViewOption;

        public MyViewHolderUser(View view) {
            super(view);
            rl_layout_user = view.findViewById(R.id.user_layout);
            tv_nama_user = view.findViewById(R.id.fullName);
            tv_email_user = view.findViewById(R.id.emailUser);
            tv_status = view.findViewById(R.id.status_user);
            buttonViewOption = view.findViewById(R.id.textViewOptions);

        }
    }

    public userAdapter(List<UserModel> userList,Activity activity) {
        this.userList = userList;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public MyViewHolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new MyViewHolderUser(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderUser holder, int position) {
        final UserModel user = userList.get(position);

        holder.tv_nama_user.setText(user.getName());
        holder.tv_email_user.setText(user.getEmail());
        holder.tv_status.setText(user.getStatus());
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("user").child(user.getIdEmail());

        HashMap<String, Object> hashMap = new HashMap<>();

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(mActivity, holder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_option_user);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.add_admin:
                                //handle menu1 click
                                hashMap.put("status","Admin Al-Ab'roor Mobile");
                                database.updateChildren(hashMap);
                                break;
                            case R.id.add_pengurus:
                                //handle menu2 click
                                hashMap.put("status","Pengurus DKM Masjid Al-Al'Abroor");
                                database.updateChildren(hashMap);
                                break;
                            case R.id.hapus_user:
                                //handle menu3 click
                                hashMap.put("status","Jemaah Masjid Al-Ab'roor");
                                database.updateChildren(hashMap);
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nama;
        public TextView email;
        public TextView status;
        public TextView buttonViewOption;

        public ViewHolder(View itemView) {

            super(itemView);

            nama = (TextView) itemView.findViewById(R.id.nama_pengurus);
            email = (TextView) itemView.findViewById(R.id.email_pengurus);
            status = (TextView) itemView.findViewById(R.id.status);
            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
        }
    }
}

