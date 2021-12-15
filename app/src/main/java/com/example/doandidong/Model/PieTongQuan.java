package com.example.doandidong.Model;

public class PieTongQuan {
    private String name;
    private int soLuong;
    private double gia;
    private String color;

    public PieTongQuan(){}

    public PieTongQuan(String name, int soLuong) {
        this.name = name;
        this.soLuong = soLuong;
        this.gia = 0.0;
        this.color = "";
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
}
