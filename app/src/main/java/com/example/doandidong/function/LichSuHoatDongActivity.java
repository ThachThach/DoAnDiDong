package com.example.doandidong.function;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.LichSuHoatDongAdapte;
import com.example.doandidong.Model.LichSuHoatDong;
import com.example.doandidong.R;
import java.util.ArrayList;
import java.util.Calendar;

public class LichSuHoatDongActivity extends AppCompatActivity {

    private LichSuHoatDongAdapte lichSuHoatDongAdapte;
    private ArrayList<LichSuHoatDong> list = new ArrayList<>();
    private ArrayList<LichSuHoatDong> listTheoNgay = new ArrayList<>();
    private RecyclerView recyclerview;
    private String ID_CUAHANG;
    private String ngayChon = "";
    private DatePickerDialog datePickerDialog;
    private TextView ngay;

    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase;
    private boolean all = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_hoat_dong);
        recyclerview = findViewById(R.id.recyclerview);
        ngay = findViewById(R.id.ngay);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference("CuaHangOder/" + ID_CUAHANG).child("lichSuHoatDong");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(LichSuHoatDongActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String day = dayOfMonth+"";
                if (dayOfMonth< 10) {
                    day = "0"+dayOfMonth;
                }

                ngayChon = day + "-" + (month + 1) + "-" + year;
                ngay.setText(ngayChon);

                all = false;
                listTheoNgay.clear();

                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getKey().equals(ngayChon)) {
                        listTheoNgay.add(list.get(i));
                    }
                    lichSuHoatDongAdapte.setData(listTheoNgay);
                }
            }
        }, year, month, day);

        lichSuHoatDongAdapte = new LichSuHoatDongAdapte(list);
        recyclerview.setAdapter(lichSuHoatDongAdapte);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LichSuHoatDongActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(linearLayoutManager);
        lichSuHoatDongAdapte.notifyDataSetChanged();
        getData();

    }

    private void getData() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String key = snap.getKey();
                    for (DataSnapshot s : snap.getChildren()) {
                        LichSuHoatDong lichSuHoatDong = s.getValue(LichSuHoatDong.class);
                        lichSuHoatDong.setKey(key);
                        if (all) {
                            list.add(0 ,lichSuHoatDong);
                            lichSuHoatDongAdapte.setData(list);
                        } else {
                            if (lichSuHoatDong.getKey().equals(ngayChon)) {
                                listTheoNgay.add(0, lichSuHoatDong);
                                lichSuHoatDongAdapte.setData(listTheoNgay);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chon_ngay, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.chonngay:
                datePickerDialog.show();
                break;
            case R.id.tatca:
                all = true;
                ngay.setText("Tất cả ngày");
                lichSuHoatDongAdapte.setData(list);
                break;
        }
        return true;
    }
}