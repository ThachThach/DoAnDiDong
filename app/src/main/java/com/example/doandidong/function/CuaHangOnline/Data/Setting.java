package com.example.doandidong.function.CuaHangOnline.Data;

import java.util.ArrayList;

public class Setting {
    private String tenTinh;
    private ArrayList<GiaoHang> giaoHangs;
    private boolean drop;

    public Setting() {
    }

    public Setting(String tenTinh, ArrayList<GiaoHang> giaoHangs) {
        this.tenTinh = tenTinh;
        this.giaoHangs = giaoHangs;
        this.drop = false;
    }

    public String getTenTinh() {
        return tenTinh;
    }

    public void setTenTinh(String tenTinh) {
        this.tenTinh = tenTinh;
    }

    public ArrayList<GiaoHang> getGiaoHangs() {
        return giaoHangs;
    }

    public void setGiaoHangs(ArrayList<GiaoHang> giaoHangs) {
        this.giaoHangs = giaoHangs;
    }

    public boolean isDrop() {
        return drop;
    }

    public void setDrop(boolean drop) {
        this.drop = drop;
    }
}
