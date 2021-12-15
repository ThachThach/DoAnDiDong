package com.example.doandidong.Model.ChucNangThanhToan;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductPushFB implements Serializable {

    private long date;
    private boolean flag;
    private int trangThai;
    private int TrangThai;
    private ArrayList<ProuductPushFB1> sanpham;

    public ProductPushFB(long date, boolean flag) {
        this.date = date;
        this.flag = flag;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public ProductPushFB() {
        this.sanpham = new ArrayList<>();
    }

    public ProductPushFB(long date, boolean flag, int trangThai, ArrayList<ProuductPushFB1> sanpham) {
        this.date = date;
        this.flag = flag;
        TrangThai = trangThai;
        this.sanpham = sanpham;
    }

    public ProductPushFB(long date, boolean flag, ArrayList<ProuductPushFB1> sanpham) {
        this.date = date;
        this.flag = flag;
        this.sanpham = sanpham;
    }

    public ArrayList<ProuductPushFB1> getSanpham() {
        return sanpham;
    }

    public void setSanpham(ArrayList<ProuductPushFB1> sanpham) {
        this.sanpham = sanpham;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
