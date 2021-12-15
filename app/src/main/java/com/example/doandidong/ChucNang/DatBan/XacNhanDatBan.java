package com.example.doandidong.ChucNang.DatBan;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.Package_AdapterDatBan.DatBanOnlineAdapter;

import com.example.doandidong.Data.DatBan.DatBanModel;
import com.example.doandidong.R;
import java.util.ArrayList;

public class XacNhanDatBan  extends AppCompatActivity {

    private DatabaseReference mDatabase ,mDatabase1;
    ArrayList<DatBanModel> datBanModels;
    String id_bk;
    String id;
    RecyclerView recyclerView;
    String  tenban;
    ProgressBar progressBar;
    TextView rong;
    private Toolbar toolbar;
    DatBanOnlineAdapter datBanAdapter;
    String id_CuaHang;
    Dialog dialogban;
    Window window;
    Dialog dialog1;
    Window window1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_xacnhanbanonline);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        id_CuaHang = "CuaHangOder/" + thongTinCuaHangSql.IDCuaHang();
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Danh sách chờ xác nhận");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rong = findViewById(R.id.tvchuadattruoc);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.rv_1);
        dialogban = new Dialog(XacNhanDatBan.this);
        dialogban.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogban.setContentView(R.layout.dialog_huydatban);
        window = dialogban.getWindow();
        dialog1 = new Dialog(XacNhanDatBan.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_handatban);
        window1 = dialog1.getWindow();
        DanhSachDon();
    }
//
    private void DanhSachDon(){
        mDatabase = FirebaseDatabase.getInstance().getReference("DuyetDatBan");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ID_datbans = new ArrayList<>();
                datBanModels = new ArrayList<>();
                if (snapshot.getValue() != null) {
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        for (DataSnapshot postSnapshot : snapshot1.getChildren()) {

                            id = postSnapshot.getKey();
                            DataSnapshot sss = postSnapshot;
                            for (DataSnapshot aaa : sss.getChildren()) {
                                String trangthai_dat = aaa.child("trangthai_dat").getValue()+"";
                                if(trangthai_dat.equals("0")){
                                    id_bk = aaa.child("id_bk").getValue() + "";
                                    String id_ngaydat = aaa.getKey();
                                    String giodat = aaa.child("giodat").getValue() + "";
                                    String gioketthuc = aaa.child("gioketthuc").getValue() + "";
                                    String ngaydat = aaa.child("ngaydat").getValue() + "";
                                    String ngayhientai = aaa.child("ngayhientai").getValue() + "";
                                    String sodienthoai = aaa.child("sodienthoai").getValue() + "";
                                    String sotiendattruoc = aaa.child("sotiendattruoc").getValue() + "";
                                    String tenkhachhang = aaa.child("tenkhachhang").getValue() + "";
                                    String tenban = aaa.child("tenban").getValue() + "";
                                    String trangthai = aaa.child("trangthai").getValue()+"";
                                    String id = aaa.getKey();
                                    String key_khachhang = aaa.child("key_khachhang").getValue()+"";
                                    datBanModels.add(new DatBanModel(tenban, id_ngaydat, giodat, gioketthuc, id_bk,ngaydat, ngayhientai, sodienthoai, sotiendattruoc, tenkhachhang,trangthai,id,key_khachhang));
//                                    ID_datban datban = new ID_datban(id, datBanModels);
//                                    ID_datbans.add(datban);
                                }
                            }
                        }
                    }
                }
                else {
                    rong.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.INVISIBLE);
                datBanAdapter = new DatBanOnlineAdapter(datBanModels, XacNhanDatBan.this,dialogban,dialog1,window,window1);
                recyclerView.setLayoutManager(new LinearLayoutManager(XacNhanDatBan.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(datBanAdapter);
                datBanAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void DanhSachDonDat(){
//
//            mDatabase1 = FirebaseDatabase.getInstance().getReference("DuyetDatBan");
//            mDatabase1.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    if (snapshot.getValue() != null) {
//                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                            ID_datbans = new ArrayList<>();
//
//                            for (DataSnapshot aaa : postSnapshot.getChildren()) {
//                                datBanModels = new ArrayList<>();
//                                id = aaa.getKey();
//
//                                for (DataSnapshot bbb : aaa.getChildren()) {
//                                    String trangthai_dat = bbb.child("trangthai_dat").getValue() + "";
//                                if (trangthai_dat.equals("0")) {
//                                    id_bk = bbb.child("id_bk").getValue() + "";
//                                    String id_ngaydat = bbb.getKey();
//                                    String giodat = bbb.child("giodat").getValue() + "";
//                                    String gioketthuc = bbb.child("gioketthuc").getValue() + "";
//                                    String ngaydat = bbb.child("ngaydat").getValue() + "";
//                                    String ngayhientai = bbb.child("ngayhientai").getValue() + "";
//                                    String sodienthoai = bbb.child("sodienthoai").getValue() + "";
//                                    String sotiendattruoc = bbb.child("sotiendattruoc").getValue() + "";
//                                    String tenkhachhang = bbb.child("tenkhachhang").getValue() + "";
//                                    String tenban = bbb.child("tenban").getValue() + "";
//                                    String trangthai = bbb.child("trangthai").getValue() + "";
//                                    String key = aaa.getKey();
//                                    String key_khachhang = bbb.child("key_khachhang").getValue()+"";
//                                    datBanModels.add(new DatBanModel(tenban, id_ngaydat, giodat, gioketthuc, id_bk,ngaydat, ngayhientai, sodienthoai, sotiendattruoc, tenkhachhang,trangthai,key,key_khachhang));
//                                    ID_datban datban = new ID_datban(id, datBanModels);
//                                    ID_datbans.add(datban);
//                                }
//
//                                }
//                            }
//                        }
//                    } else {
//                        rong.setVisibility(View.VISIBLE);
//                    }
//                    progressBar.setVisibility(View.INVISIBLE);
//                    datBanAdapter = new DatBanOnlineAdapter(ID_datbans, XacNhanDatBan.this,dialogban,dialog1,window,window1);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(XacNhanDatBan.this, LinearLayoutManager.VERTICAL, false));
//                    recyclerView.setAdapter(datBanAdapter);
//                    datBanAdapter.notifyDataSetChanged();
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//
//        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if (item_id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return true;
    }
}
