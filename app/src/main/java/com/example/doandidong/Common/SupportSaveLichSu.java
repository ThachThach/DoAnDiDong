package com.example.doandidong.Common;

import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;

import com.example.doandidong.Data.LichSuHoatDong;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SupportSaveLichSu {

    private String ID_CUAHANG;
    private String TEN_NHAN_VIEN;
    private Context context;
    private ThongTinCuaHangSql thongTinCuaHangSql;

    public SupportSaveLichSu(Context context, String mota) {
        this.context = context;
        thongTinCuaHangSql = new ThongTinCuaHangSql(context);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        TEN_NHAN_VIEN = thongTinCuaHangSql.Username();
        LichSuHoatDong(mota);
    }


    public void LichSuHoatDong(String mota) {
        Date date = new Date();
        String ngay = formartDate(date);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        LichSuHoatDong lichSuHoatDong = new LichSuHoatDong(mota, TEN_NHAN_VIEN, formartDate2(date));

        lichSuHoatDong.getCongViec();

        FirebaseDatabase.getInstance().getReference().child("CuaHangOder").child(ID_CUAHANG).child("lichSuHoatDong").child(ngay).child(timestamp.getTime()+"").setValue(lichSuHoatDong);
    }

    private String formartDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dt = formatter.format(date);
        return dt;
    }

    private String formartDate2(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dt = formatter.format(date);
        return dt;
    }
}
