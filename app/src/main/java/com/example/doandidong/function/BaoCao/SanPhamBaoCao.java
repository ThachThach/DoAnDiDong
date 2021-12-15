package com.example.doandidong.function.BaoCao;

import javax.xml.transform.Source;

public class SanPhamBaoCao {
    private Double giaProudct;
    private String nameProduct;
    private String imgProduct;
    private String loai;
    private int soluong;
    private String color;
    private String yeuCau;

    public SanPhamBaoCao() {
    }

    public SanPhamBaoCao(Double giaProudct, String nameProduct, String imgProduct, String loai, int soluong, String yeuCau) {
        this.giaProudct = giaProudct;
        this.nameProduct = nameProduct;
        this.imgProduct = imgProduct;
        this.loai = loai;
        this.soluong = soluong;
        this.yeuCau = yeuCau;
    }

    public Double getGiaProudct() {
        return giaProudct;
    }

    public void setGiaProudct(Double giaProudct) {
        this.giaProudct = giaProudct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getYeuCau() {
        return yeuCau;
    }

    public void setYeuCau(String yeuCau) {
        this.yeuCau = yeuCau;
    }
}
