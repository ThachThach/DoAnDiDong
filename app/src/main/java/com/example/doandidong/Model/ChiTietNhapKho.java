package com.example.doandidong.Model;

import java.io.Serializable;

public class ChiTietNhapKho implements Serializable {
    String tenSanPham;
    String soLieuCu;
    int soLieu;

    public ChiTietNhapKho() {
    }

    public ChiTietNhapKho(String tenSanPham, String soLieuCu, String soLieuMoi) {
        this.tenSanPham = tenSanPham;
        this.soLieuCu = soLieuCu;
        this.soLieuMoi = soLieuMoi;
    }

    public ChiTietNhapKho(String tenSanPham, String soLieuCu, int soLieu, String soLieuMoi) {
        this.tenSanPham = tenSanPham;
        this.soLieuCu = soLieuCu;
        this.soLieu = soLieu;
        this.soLieuMoi = soLieuMoi;
    }

    public int getSoLieu() {
        return soLieu;
    }

    public void setSoLieu(int soLieu) {
        this.soLieu = soLieu;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getSoLieuCu() {
        return soLieuCu;
    }

    public void setSoLieuCu(String soLieuCu) {
        this.soLieuCu = soLieuCu;
    }

    public String getSoLieuMoi() {
        return soLieuMoi;
    }

    public void setSoLieuMoi(String soLieuMoi) {
        this.soLieuMoi = soLieuMoi;
    }

    String soLieuMoi;
}
