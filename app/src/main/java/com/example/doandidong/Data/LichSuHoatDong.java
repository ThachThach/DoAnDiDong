package com.example.doandidong.Data;

public class LichSuHoatDong {
    private String congViec;
    private String nhanVien;
    private String thoiGian;
    private String key;

    public LichSuHoatDong() {
    }

    public LichSuHoatDong(String congViec, String nhanVien, String thoiGian) {
        this.congViec = congViec;
        this.nhanVien = nhanVien;
        this.thoiGian = thoiGian;
    }

    public LichSuHoatDong(String congViec, String nhanVien, String thoiGian, String key) {
        this.congViec = congViec;
        this.nhanVien = nhanVien;
        this.thoiGian = thoiGian;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCongViec() {
        return congViec;
    }

    public void setCongViec(String congViec) {
        this.congViec = congViec;
    }

    public String getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(String nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }
}
