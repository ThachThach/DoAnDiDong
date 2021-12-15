package com.example.doandidong.Common;

import android.content.Context;
import android.database.Cursor;

import com.example.doandidong.Data.NhanVien_CaLam.NhanVien;
import java.util.ArrayList;

public class ThongTinCuaHangSql {

    private Context context;

    public ThongTinCuaHangSql(Context context) {
        this.context = context;
    }

    public String IDCuaHang() {
        com.example.doandidong.SQL.ThongTinCuaHangSql thongTinCuaHangSql =
                new com.example.doandidong.SQL.
                        ThongTinCuaHangSql(context, "app_database.sqlite", null, 2);
        thongTinCuaHangSql.createTable();
        Cursor cursor = thongTinCuaHangSql.selectThongTin();
        String id = "";
        if (cursor.getCount() > 0) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    id = cursor.getString(0);
                }
            } else {

            }
        }
        return id;
    }

    public String IDUser() {
        com.example.doandidong.SQL.ThongTinCuaHangSql thongTinCuaHangSql = new com.example.doandidong.SQL.ThongTinCuaHangSql(context, "app_database.sqlite", null, 2);
        thongTinCuaHangSql.createTableUser();
        Cursor cursor = thongTinCuaHangSql.selectUser();
        String id = "";
        if (cursor.getCount() > 0) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    id = cursor.getString(0);
                }
            } else {

            }
        }
        return id;
    }

    public String Username() {
        com.example.doandidong.SQL.ThongTinCuaHangSql thongTinCuaHangSql = new com.example.doandidong.SQL.ThongTinCuaHangSql(context, "app_database.sqlite", null, 2);
        thongTinCuaHangSql.createTableUser();
        Cursor cursor = thongTinCuaHangSql.selectUser();
        String username = "";
        if (cursor.getCount() > 0) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    username = cursor.getString(1);
                }
            } else {

            }
        }
        return username;
    }

    public NhanVien selectUser() {
        NhanVien nhanVien = new NhanVien();
        com.example.doandidong.SQL.ThongTinCuaHangSql thongTinCuaHangSql = new com.example.doandidong.SQL.ThongTinCuaHangSql(context, "app_database.sqlite", null, 2);
        thongTinCuaHangSql.createTableUser();
        Cursor cursor = thongTinCuaHangSql.selectUser();
        if (cursor.getCount() > 0) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    nhanVien.setId(cursor.getString(0));
                    nhanVien.setUsername(cursor.getString(1));
                    nhanVien.setEmail(cursor.getString(2));
                    nhanVien.setPhone(cursor.getString(3));
                    String quyen = cursor.getString(4);
                    nhanVien.setChucVu(chucQuyen(quyen));
                }
            } else {

            }
        }

        return nhanVien;
    }

    public boolean isChu() {
        com.example.doandidong.SQL.ThongTinCuaHangSql thongTinCuaHangSql = new com.example.doandidong.SQL.ThongTinCuaHangSql(context, "app_database.sqlite", null, 2);
        thongTinCuaHangSql.createTableChuCuaHang();
        Cursor cursor = thongTinCuaHangSql.selectChuCuaHang();
        String check = "";

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                check = cursor.getString(1);
            }
        } else {

        }
        boolean res = Boolean.parseBoolean(check);

        return res;
    }

    private ArrayList<Boolean> chucQuyen(String quyenStr) {
        char[] ch = quyenStr.toCharArray();
        ArrayList<Boolean> quyenArr = new ArrayList<>();
        for (int i = 0; i < ch.length; i++) {
            int so = Integer.parseInt(ch[i] + "");
            if (so == 1) {
                quyenArr.add(true);
            } else if (so == 0) {
                quyenArr.add(false);
            }
        }
        return quyenArr;
    }
}
