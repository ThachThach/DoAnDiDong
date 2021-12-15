package com.example.doandidong.Data;

public class NguyenLieu {
    private String ten;
    private int soluong;
    private String donvi;
    private String key;

    public NguyenLieu() {
    }

    public NguyenLieu(String ten, int soluong, String donvi) {
        this.ten = ten;
        this.soluong = soluong;
        this.donvi = donvi;
    }

    public NguyenLieu(String ten, int soluong, String donvi, String key) {
        this.ten = ten;
        this.soluong = soluong;
        this.donvi = donvi;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getDonvi() {
        return donvi;
    }

    public void setDonvi(String donvi) {
        this.donvi = donvi;
    }
}
