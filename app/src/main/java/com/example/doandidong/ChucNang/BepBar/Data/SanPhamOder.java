package com.example.doandidong.ChucNang.BepBar.Data;

import java.util.List;

public class SanPhamOder {
    private String  nameTable;
    private List<Mon> sanpham;
    private long date;
    private int trangThai;

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public SanPhamOder(String nameTable, List<Mon> sanpham, long date, int trangthai) {
        this.nameTable = nameTable;
        this.sanpham = sanpham;
        this.date = date;
        this.trangThai=trangthai;
    }

    public List<Mon> getSanpham() {
        return sanpham;
    }

    public void setSanpham(List<Mon> sanpham) {
        this.sanpham = sanpham;
    }

    public SanPhamOder(long date) {
        this.date = date;
    }

    public SanPhamOder() {

    }
    public SanPhamOder(int trangThai){
        this.trangThai=trangThai;
    }



    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }



    public void setDate(long date) {
        this.date = date;
    }

    public String getNameTable() {
        return nameTable;
    }


    public long getDate() {
        return date;
    }
}
