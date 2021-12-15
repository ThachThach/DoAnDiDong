package com.example.doandidong.Model;

import com.example.doandidong.Model.ChucNangThanhToan.DonGia;
import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    String id;
    String nameProduct;
    String chitiet;
    String Nhomsanpham;
    Double giaNhap;
    Double giaBan;
    int soluong;
    String imgProduct;

    public ArrayList<DonGia> getDonGia() {
        return donGiaOrder;
    }

    public void setDonGia(ArrayList<DonGia> donGiaOrder) {
        this.donGiaOrder = donGiaOrder;
    }

    ArrayList<DonGia> donGiaOrder;
    boolean addToCart;

    public Product(String id, String nameProduct, String chitiet, String nhomsanpham, Double giaNhap, ArrayList<DonGia> donGiaOrder , int soluong, String imgProduct, String status) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.chitiet = chitiet;
        Nhomsanpham = nhomsanpham;
        this.giaNhap = giaNhap;
        this.donGiaOrder = donGiaOrder;
        this.soluong = soluong;
        this.imgProduct = imgProduct;
        this.status = status;
    }
    public Product(String nameProduct, int soluong, String imgProduct, ArrayList<DonGia> donGiaOrder, String status, String id) {
        this.nameProduct = nameProduct;
        this.soluong = soluong;
        this.imgProduct = imgProduct;
        this.donGiaOrder = donGiaOrder;
        this.status = status;
        this.id = id;
    }



    public Product(String id, String nameProduct, int soluong, String imgProduct, ArrayList<DonGia> donGiaOrder) {
        this.id = id;
        this.nameProduct = nameProduct;

        this.soluong = soluong;
        this.imgProduct = imgProduct;
        this.donGiaOrder = donGiaOrder;
    }
    public Product(String id, String nameProduct, int soluong, String imgProduct,Double giaBan) {
        this.id = id;
        this.nameProduct = nameProduct;

        this.soluong = soluong;
        this.imgProduct = imgProduct;
        this.giaBan = giaBan;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    String status;

    public Product(String nameProduct, int soluong) {
        this.nameProduct = nameProduct;
        this.soluong = soluong;
    }

    public Product(String id, String nameProduct, String chitiet, String nhomsanpham, Double giaNhap, Double giaBan, int soluong, String imgProduct) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.chitiet = chitiet;
        Nhomsanpham = nhomsanpham;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soluong = soluong;
        this.imgProduct = imgProduct;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public boolean isAddToCart() {
        return addToCart;
    }

    public void setAddToCart(boolean addToCart) {
        this.addToCart = addToCart;
    }

    public Product(String nameProduct, int soluong, String imgProduct) {
        this.nameProduct = nameProduct;
        this.soluong = soluong;
        this.imgProduct = imgProduct;
    }

    public Product(String nameProduct, Double giaBan, String imgProduct) {
        this.nameProduct = nameProduct;
        this.giaBan = giaBan;
        this.imgProduct = imgProduct;
    }


    public Product(String nameProduct, String chitiet, String nhomsanpham, Double giaNhap, Double giaBan, int soluong) {
        this.nameProduct = nameProduct;
        this.chitiet = chitiet;
        Nhomsanpham = nhomsanpham;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soluong = soluong;
    }

    public Product(String nameProduct, Double giaBan, String imgProduct, String status) {
        this.nameProduct = nameProduct;
        this.giaBan = giaBan;
        this.imgProduct = imgProduct;
        this.status = status;
    }

    public Product() {
    }

    public Product(String id, String nameProduct, String chitiet, String nhomsanpham, Double giaNhap, Double giaBan, int soluong) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.chitiet = chitiet;
        Nhomsanpham = nhomsanpham;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soluong = soluong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getChitiet() {
        return chitiet;
    }

    public void setChitiet(String chitiet) {
        this.chitiet = chitiet;
    }

    public String getNhomsanpham() {
        return Nhomsanpham;
    }

    public void setNhomsanpham(String nhomsanpham) {
        Nhomsanpham = nhomsanpham;
    }

    public Double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(Double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public Double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(Double giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
