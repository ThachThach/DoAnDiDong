package com.example.doandidong.Data.KhachHang;

import java.io.Serializable;

public class NhomKhachHang implements Serializable {
    String tenNhomKh;
    String maKH;
    String ghichuNhom;
    String id;

    public NhomKhachHang() {
    }

    public NhomKhachHang(String tenNhomKh, String maKH, String ghichuNhom, String id) {
        this.tenNhomKh = tenNhomKh;
        this.maKH = maKH;
        this.ghichuNhom = ghichuNhom;
        this.id = id;
    }

    public NhomKhachHang(String tenNhomKh, String maKH, String ghichuNhom) {
        this.tenNhomKh = tenNhomKh;
        this.maKH = maKH;
        this.ghichuNhom = ghichuNhom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenNhomKh() {
        return tenNhomKh;
    }

    public void setTenNhomKh(String tenNhomKh) {
        this.tenNhomKh = tenNhomKh;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getGhichuNhom() {
        return ghichuNhom;
    }

    public void setGhichuNhom(String ghichuNhom) {
        this.ghichuNhom = ghichuNhom;
    }
}
