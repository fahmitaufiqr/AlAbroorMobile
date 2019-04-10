package com.example.alabroormobile.homeMenu.Acara.JadwalPengajian;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alabroormobile.R;
import com.example.alabroormobile.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Aulia Ikvanda on 31,March,2019
 */

public class JadwalPengajianActivity extends AppCompatActivity {

    ArrayList<PengajianModel> pengajianList;
    ArrayList<String> mPengajianId;
    PengajianAdapter pengajianAdapter;
    RecyclerView rc_list_acara;
    FloatingActionButton fab_tambah;
    ActionMode mActionMode;

    int year;
    int month;
    int dayOfMonth;

    int currentHour;
    int currentMinute;
    String amPm;

    private DatabaseReference mDatabase;
    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            pengajianList.add(dataSnapshot.getValue(PengajianModel.class));
            mPengajianId.add(dataSnapshot.getKey());
            pengajianAdapter.updateEmptyView();
            pengajianAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            int pos = mPengajianId.indexOf(dataSnapshot.getKey());
            pengajianList.set(pos, dataSnapshot.getValue(PengajianModel.class));
            pengajianAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            int pos = mPengajianId.indexOf(dataSnapshot.getKey());
            mPengajianId.remove(pos);
            pengajianList.remove(pos);
            pengajianAdapter.updateEmptyView();
            pengajianAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_pengajian);
        getSupportActionBar().setTitle("Jadwal Pengajian");

        pengajianList = new ArrayList<>();
        mPengajianId = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Pengajian");
        mDatabase.addChildEventListener(childEventListener);

        rc_list_acara = findViewById(R.id.rv_jadwal_pengajian);
        rc_list_acara.setHasFixedSize(true);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rc_list_acara.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(this,
                layoutManager.getOrientation());
        rc_list_acara.addItemDecoration(divider);

        View emptyView = findViewById(R.id.empty_view);
        pengajianAdapter = new PengajianAdapter(this, pengajianList, mPengajianId, emptyView,
                new PengajianAdapter.ClickHandler() {
                    @Override
                    public void onItemClick(int position) {
                        if (mActionMode != null) {
                            pengajianAdapter.toggleSelection(mPengajianId.get(position));
                            if (pengajianAdapter.selectionCount() == 0)
                                mActionMode.finish();
                            else
                                mActionMode.invalidate();
                            return;
                        }

                        String acara = pengajianList.get(position).toString();
                        Toast.makeText(JadwalPengajianActivity.this, "Tahan untuk edit dan hapus", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public boolean onItemLongClick(int position) {
                        if (mActionMode != null) return false;

                        pengajianAdapter.toggleSelection(mPengajianId.get(position));
                        mActionMode = JadwalPengajianActivity.this.startSupportActionMode(mActionModeCallback);
                        return true;
                    }
                });
        rc_list_acara.setAdapter(pengajianAdapter);

        fab_tambah = findViewById(R.id.fab_add_acara);
        fab_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPengajian();
            }
        });

    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.pengajian_context, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            mode.setTitle(String.valueOf(pengajianAdapter.selectionCount()));
            menu.findItem(R.id.action_edit).setVisible(pengajianAdapter.selectionCount() == 1);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_edit:
                    editPengajian();
                    return true;

                case R.id.action_delete:
                    deletePengajian();
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            pengajianAdapter.resetSelection();
        }
    };

    private void addPengajian() {
        final View view = getLayoutInflater().inflate(R.layout.pop_up_tambah_pengajian, null);
        // GET USER NAME
        TextView viewUserSend = (TextView) view.findViewById(R.id.nama_pengirim_input);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        DatabaseReference dbuser = FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());
        dbuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                viewUserSend.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
//        ----------------------------------------------------------------------------------------
//        DATE and TIME
        TextView viewDate = (TextView) view.findViewById(R.id.view_date);
        TextView viewTime = (TextView) view.findViewById(R.id.view_jam);

        //        DATE PICKER
        viewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar myCalendar = Calendar.getInstance();
                year = myCalendar.get(Calendar.YEAR);
                month = myCalendar.get(Calendar.MONTH);
                dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(JadwalPengajianActivity.this,
                        (view, year, month, dayOfMonth) -> viewDate.setText(dayOfMonth + " - " + (month + 1) + " - " + year),year,month,dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        //TIME PICKER
        viewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(JadwalPengajianActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = " PM";
                        } else {
                            amPm = " AM";
                        }
                        viewTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);
                timePickerDialog.show();
            }
        });

//        ===========================================================================================================================

        AlertDialog.Builder builder = new AlertDialog.Builder(JadwalPengajianActivity.this);
        builder.setTitle(R.string.tambah_pengajian)
                .setView(view)
                .setPositiveButton(R.string.pengajian_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        getDateandTime();
                        EditText namaEdtText = (EditText) view.findViewById(R.id.input_nama_acara);
                        EditText keteranganEditText = (EditText) view.findViewById(R.id.input_keterangan);


                        String key = mDatabase.push().getKey();
                        mDatabase.child(key).setValue(new PengajianModel(
                                namaEdtText.getText().toString(),
                                keteranganEditText.getText().toString(),
                                viewDate.getText().toString(),
                                viewTime.getText().toString(),
                                viewUserSend.getText().toString())
                        );
                        Toast.makeText(JadwalPengajianActivity.this, "Berhasil di Simpan", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }


    private void editPengajian() {
        final String currentPengajianId = pengajianAdapter.getSelectedId().get(0);
        PengajianModel selectedPengajian = pengajianList.get(mPengajianId.indexOf(currentPengajianId));

        View view = getLayoutInflater().inflate(R.layout.pop_up_tambah_pengajian, null);


        final EditText namaEdtText = (EditText) view.findViewById(R.id.input_nama_acara);
        namaEdtText.setText(selectedPengajian.getNama());
        final EditText keteranganEditText = (EditText) view.findViewById(R.id.input_keterangan);
        keteranganEditText.setText(selectedPengajian.getKeterangan());

//        DATE and TIME ===========================================================================================
        final TextView viewDate = (TextView) view.findViewById(R.id.view_date);
        final TextView viewTime = (TextView) view.findViewById(R.id.view_jam);

        //        DATE PICKER
        viewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar myCalendar = Calendar.getInstance();
                year = myCalendar.get(Calendar.YEAR);
                month = myCalendar.get(Calendar.MONTH);
                dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(JadwalPengajianActivity.this,
                        (view, year, month, dayOfMonth) -> viewDate.setText(dayOfMonth + " - " + (month + 1) + " - " + year),year,month,dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        viewDate.setText(selectedPengajian.getDate());

        //TIME PICKER
        viewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(JadwalPengajianActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = " PM";
                        } else {
                            amPm = " AM";
                        }
                        viewTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);
                timePickerDialog.show();
            }
        });
        viewTime.setText(selectedPengajian.getTime());
//        ===========================================================================================================================

        // GET USER NAME ==================================================================================
        final TextView viewUserSend = (TextView) view.findViewById(R.id.nama_pengirim_input);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        DatabaseReference dbuser = FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());
        dbuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                viewUserSend.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        viewUserSend.setText(selectedPengajian.getPengirim());
//        ==================================================================================================


        AlertDialog.Builder builder = new AlertDialog.Builder(JadwalPengajianActivity.this);
        builder.setTitle(R.string.pengajian_edit)
                .setView(view)
                .setPositiveButton(R.string.pengajian_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabase.child(currentPengajianId).setValue(new PengajianModel(
                                namaEdtText.getText().toString(),
                                keteranganEditText.getText().toString(),
                                viewDate.getText().toString(),
                                viewTime.getText().toString(),
                                viewUserSend.getText().toString())
                        );
                        mActionMode.finish();
                        Toast.makeText(JadwalPengajianActivity.this, "Berhasil di Edit", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mActionMode.finish();
                    }
                });
        builder.create().show();
    }

    private void deletePengajian() {
        final ArrayList<String> selectedIds = pengajianAdapter.getSelectedId();
        int message = selectedIds.size() == 1 ? R.string.delete_pengajian : R.string.delete_pengajian;

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (String currentPetId : selectedIds) {
                            mDatabase.child(currentPetId).removeValue();
                        }
                        mActionMode.finish();
                        Toast.makeText(JadwalPengajianActivity.this, "Berhasil Di Hapus", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        mActionMode.finish();
                    }
                });
        builder.create().show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}