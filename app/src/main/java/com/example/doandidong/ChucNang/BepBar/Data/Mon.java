package com.example.doandidong.ChucNang.BepBar.Data;

public class Mon {
    String nameProduct;
    String yeuCau;
    long soluong;
    double giaProudct;
    String imgProduct;

    public String getYeuCau() {
        return yeuCau;
    }

    public void setYeuCau(String yeuCau) {
        this.yeuCau = yeuCau;
    }

    public Mon(String nameProduct, long soluong,String yeuCau) {
        this.nameProduct = nameProduct;
        this.soluong = soluong;
        this.yeuCau=yeuCau;
    }

    public Mon(String nameProduct, String yeuCau, long soluong, double giaProudct) {
        this.nameProduct = nameProduct;
        this.yeuCau = yeuCau;
        this.soluong = soluong;
        this.giaProudct = giaProudct;
    }

    public Mon(String nameProduct, String yeuCau, long soluong, double giaProudct, String imgProduct) {
        this.nameProduct = nameProduct;
        this.yeuCau = yeuCau;
        this.soluong = soluong;
        this.giaProudct = giaProudct;
        this.imgProduct = imgProduct;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public double getGiaProudct() {
        return giaProudct;
    }

    public void setGiaProudct(double giaProudct) {
        this.giaProudct = giaProudct;
    }

    public Mon() {}

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public long getSoluong() {
        return soluong;
    }

    public void setSoluong(long soluong) {
        this.soluong = soluong;
    }


}
