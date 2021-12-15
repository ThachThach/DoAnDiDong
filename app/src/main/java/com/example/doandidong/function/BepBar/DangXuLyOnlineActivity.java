package com.example.doandidong.function.BepBar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.FormatDouble;
import com.example.doandidong.Common.SupportFragmentDonOnline;
import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.R;
import com.example.doandidong.function.BepBar.Adapter.ChiTietDonHangOnlineAdapter;
import com.example.doandidong.function.BepBar.Data.DonHang;
import com.example.doandidong.function.BepBar.Data.SanPham;
import com.example.doandidong.function.BepBar.Data.SanPhamOder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DangXuLyOnlineActivity extends AppCompatActivity {

    private ThongTinCuaHangSql thongTinCuaHangSql;
    private String ID_QUAN;
    private String userName;
    private TextView khuvuc, soluong, tongdon, thoigian;
    private Button hoanthanh;
    private RecyclerView recycleview;
    private ChiTietDonHangOnlineAdapter chiTietDonHangAdapter;
    private String KEY_NGAY, ID_KEY;
    private FormatDouble formatDouble;
    private SupportFragmentDonOnline support = new SupportFragmentDonOnline();

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_xu_ly);
        IDLayout();
        Intent intent = getIntent();
        ID_KEY = intent.getStringExtra("key");
        KEY_NGAY = intent.getStringExtra("key_ngay");
        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_QUAN = thongTinCuaHangSql.IDCuaHang();
        userName = thongTinCuaHangSql.Username();

        getFirebase();
    }

    private void IDLayout() {
        khuvuc = findViewById(R.id.khuvuc);
        soluong = findViewById(R.id.soluong);
        tongdon = findViewById(R.id.tongdon);
        thoigian = findViewById(R.id.thoigian);
        hoanthanh = findViewById(R.id.hoanthanh);
        recycleview = findViewById(R.id.recycleview);
    }

    private void getFirebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("CuaHangOder/"+ID_QUAN+"/donhangonline/dondadat/"+KEY_NGAY+"/"+ID_KEY).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    DonHang donHang = snapshot.getValue(DonHang.class);
                    khuvuc.setText("ID " + donHang.getKey());
                    double tong = TinhTongTien(donHang.getSanpham());
                    ArrayList<SanPham> sanPhams = donHang.getSanpham();
                    soluong.setText(soLuong(donHang)+"");
                    tongdon.setText((tong - donHang.getGiaKhuyenMai())+"");
                    thoigian.setText(changeDate(ID_KEY) + "");
                    Log.d("ssss", donHang.getTrangthai()+"");
                    if (donHang.getTrangthai() == 2) {
                        hoanthanh.setText("Xử lí");
                    } else  if (donHang.getTrangthai() == 3) {

                        hoanthanh.setText("Hoàn thành");
                    } else  if (donHang.getTrangthai() == 4) {
                        hoanthanh.setText("Trả đơn");
                    }

                    hoanthanh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (donHang.getTrangthai() == 2) {
                                mDatabase.child("CuaHangOder/"+ID_QUAN+"/donhangonline/dondadat/"+KEY_NGAY+"/"+ID_KEY+"/trangthai").setValue(3);
                                mDatabase.child("CuaHangOder/"+ID_QUAN+"/donhangonline/dondadat/"+KEY_NGAY+"/"+ID_KEY+"/nhanVien").setValue(userName);
                                Toast.makeText(DangXuLyOnlineActivity.this, "Đã chuyển trạng thái đơn hàng", Toast.LENGTH_SHORT).show();
                                hoanthanh.setText("Hoàn Thành");
                                new SupportSaveLichSu(DangXuLyOnlineActivity.this, "Đang sử lý đơn hàng ID-"+ID_KEY);
                                donHang.setTrangthai(3);
                            }else if (donHang.getTrangthai() == 3) {
                                mDatabase.child("CuaHangOder/"+ID_QUAN+"/donhangonline/dondadat/"+KEY_NGAY+"/"+ID_KEY+"/trangthai").setValue(4);
                                Toast.makeText(DangXuLyOnlineActivity.this, "Đã chuyển trạng thái đơn hàng", Toast.LENGTH_SHORT).show();
                                new SupportSaveLichSu(DangXuLyOnlineActivity.this, "Đơn hàng ID-"+ID_KEY+" đã xử lý xong");
                                hoanthanh.setText("Trả đơn");
                                donHang.setTrangthai(4);
                                onBackPressed();
                            }else if (donHang.getTrangthai() == 4) {
                                mDatabase.child("CuaHangOder/"+ID_QUAN+"/donhangonline/dondadat/"+KEY_NGAY+"/"+ID_KEY+"/trangthai").setValue(5);
                                Toast.makeText(DangXuLyOnlineActivity.this, "Đã trả đơn", Toast.LENGTH_SHORT).show();
                                new SupportSaveLichSu(DangXuLyOnlineActivity.this, "Hoàn thành đơn hàng ID-"+ID_KEY);
                                onBackPressed();
                            }
                        }
                    });

                    recycleview.setLayoutManager(new GridLayoutManager(DangXuLyOnlineActivity.this, 1));
                    chiTietDonHangAdapter = new ChiTietDonHangOnlineAdapter(DangXuLyOnlineActivity.this, sanPhams);
                    recycleview.setAdapter(chiTietDonHangAdapter);

                    chiTietDonHangAdapter.notifyDataSetChanged();

                }else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String changeDate(String date){
        long dates = Long.parseLong(date);
        java.sql.Timestamp timestamp = new java.sql.Timestamp(dates);
        if(dates ==0){
            return "";
        }
        Date date1 =new Date(timestamp.getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
        String aaa = simpleDateFormat.format(date1);
        return  aaa;
    }

    private int soLuong(DonHang sanPhamOder) {
        int soLuong = 0;
        double tien = 0;
        for (int i = 0; i < sanPhamOder.getSanpham().size(); i++){
            soLuong += sanPhamOder.getSanpham().get(i).getSoluong();
        }

        return soLuong;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private Date formatDate(String strDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.sss yyyy-MM-dd");

        try {
            Date date = simpleDateFormat.parse(strDate);
            return date;
        } catch (Exception e) {
            Date date = new Date();
            return date;
        }
    }
    private Double TinhTongTien(ArrayList<SanPham> sanPhams) {
        double tongGia = 0;
        for (int i = 0; i < sanPhams.size(); i++) {
            tongGia += sanPhams.get(i).getGiaBan();
        }
        return tongGia;
    }
}