package com.example.doandidong.function.BaoCao.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    private String Loai;
    private String nameProduct;
    private String yeuCau;
    private String imgProduct;
    private Double giaProudct;
    private int soluong;

    public SanPham() {
    }

    public SanPham(String loai, String nameProduct, String yeuCau, String imgProduct, Double giaProudct, int soluong) {
        Loai = loai;
        this.nameProduct = nameProduct;
        this.yeuCau = yeuCau;
        this.imgProduct = imgProduct;
        this.giaProudct = giaProudct;
        this.soluong = soluong;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String loai) {
        Loai = loai;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getYeuCau() {
        return yeuCau;
    }

    public void setYeuCau(String yeuCau) {
        this.yeuCau = yeuCau;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public Double getGiaProudct() {
        return giaProudct;
    }

    public void setGiaProudct(Double giaProudct) {
        this.giaProudct = giaProudct;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
