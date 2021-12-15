package com.example.doandidong.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class BaoCaoKho implements Serializable {
    String nhanVien;
    String ngay;
    String gio;
    ArrayList<ChiTietNhapKho> chiTietNhapKho;

    public BaoCaoKho() {
    }

    public BaoCaoKho(String nhanVien, String ngay, String gio) {
        this.nhanVien = nhanVien;
        this.ngay = ngay;
        this.gio = gio;
    }

    public BaoCaoKho(String nhanVien, String ngay, String gio, ArrayList<ChiTietNhapKho> chiTietNhapKho) {
        this.nhanVien = nhanVien;
        this.ngay = ngay;
        this.gio = gio;
        this.chiTietNhapKho = chiTietNhapKho;
    }

    public String getNhanVien() { return nhanVien; }

    public void setNhanVien(String nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public ArrayList<ChiTietNhapKho> getChiTietNhapKho() {
        return chiTietNhapKho;
    }

    public void setChiTietNhapKho(ArrayList<ChiTietNhapKho> chiTietNhapKho) {
        this.chiTietNhapKho = chiTietNhapKho;
    }
}

