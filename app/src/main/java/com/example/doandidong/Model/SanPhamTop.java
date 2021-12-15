package com.example.doandidong.Model;

public class SanPhamTop {
    private String name;
    private int soLuong;
    private double gia;
    private String color;
    //private ImageView imageView;

    public SanPhamTop() {
        this.color = "";
    }

    public SanPhamTop(String name, int soLuong, double gia, String color) {
        this.name = name;
        this.soLuong = soLuong;
        this.gia = gia;
        this.color = color;
    }

    public SanPhamTop(String name, int soLuong, double gia) {
        this.name = name;
        this.soLuong = soLuong;
        this.gia = gia;
        this.color = "";
    }

    public SanPhamTop(String name, int soLuong, String color) {
        this.name = name;
        this.soLuong = soLuong;
        this.gia = 0.0;
        this.color = color;
    }

    public SanPhamTop(String name, int soLuong) {
        this.name = name;
        this.soLuong = soLuong;
        this.gia = 0.0;
        this.color = "";
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
