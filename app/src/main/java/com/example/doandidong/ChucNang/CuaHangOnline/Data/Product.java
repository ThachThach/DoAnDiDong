package com.example.doandidong.ChucNang.CuaHangOnline.Data;

import com.example.doandidong.Data.ChucNangThanhToan.DonGia;
import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    private String id;
    private String nameProduct;
    private String chitiet;
    private String Nhomsanpham;
    private Double giaNhap;
    private Double giaBan;
    private int soluong;
    private String imgProduct;
    private String nameImage;
    private Double giamGia;
    private String status;
    private ArrayList<DonGia> donGiaOrder;
    private String idCuaHang;
    private boolean up;
    private String title;
    private boolean superquangcao;
    private String soDienThoai;
    private String name;
    private String ngayBatDau;
    private String ngayKetThuc;

    //(key,name,moTa,nhomSp,0.0, sLuong, img, nameImage, giamGia, status, listDonGia, ID_CUAHANG, false);

    public Product(String id, String nameProduct, String chitiet, String nhomsanpham, Double giaNhap, int soluong, String imgProduct, String nameImage, Double giamGia, String status, ArrayList<DonGia> donGiaOrder) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.chitiet = chitiet;
        this.Nhomsanpham = nhomsanpham;
        this.giaNhap = giaNhap;
        this.soluong = soluong;
        this.imgProduct = imgProduct;
        this.nameImage = nameImage;
        this.giamGia = giamGia;
        this.status = status;
        this.donGiaOrder = donGiaOrder;
    }
    public Product(String id, String nameProduct, String chitiet, String nhomsanpham, Double giaNhap, int soluong, String imgProduct, String nameImage, String status, ArrayList<DonGia> donGiaOrder) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.chitiet = chitiet;
        this.Nhomsanpham = nhomsanpham;
        this.giaNhap = giaNhap;
        this.soluong = soluong;
        this.imgProduct = imgProduct;
        this.giamGia = 0.0;
        this.nameImage = nameImage;
        this.status = status;
        this.donGiaOrder = donGiaOrder;
    }

    public Product(String id, String nameProduct, String chitiet, String nhomsanpham, Double giaNhap, int soluong, String imgProduct, String nameImage, Double giamGia, String status, ArrayList<DonGia> donGiaOrder, String idCuaHang, boolean up, String title, boolean superquangcao) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.chitiet = chitiet;
        Nhomsanpham = nhomsanpham;
        this.giaNhap = giaNhap;
        this.soluong = soluong;
        this.imgProduct = imgProduct;
        this.nameImage = nameImage;
        this.giamGia = giamGia;
        this.status = status;
        this.donGiaOrder = donGiaOrder;
        this.idCuaHang = idCuaHang;
        this.title = title;
        this.up = up;
        this.superquangcao = false;
    }

    public Product(String id, String nameProduct, String chitiet, String nhomsanpham, Double giaNhap, Double giaBan, int soluong, String imgProduct, String nameImage, Double giamGia, String status, ArrayList<DonGia> donGiaOrder, String idCuaHang, boolean up, String title, boolean superquangcao, String soDienThoai, String name) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.chitiet = chitiet;
        Nhomsanpham = nhomsanpham;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soluong = soluong;
        this.imgProduct = imgProduct;
        this.nameImage = nameImage;
        this.giamGia = giamGia;
        this.status = status;
        this.donGiaOrder = donGiaOrder;
        this.idCuaHang = idCuaHang;
        this.up = up;
        this.title = title;
        this.superquangcao = superquangcao;
        this.soDienThoai = soDienThoai;
        this.name = name;
    }

    public Product(String id, String nameProduct, String chitiet, String nhomsanpham, Double giaNhap, Double giaBan, int soluong, String imgProduct, String nameImage, Double giamGia, String status, ArrayList<DonGia> donGiaOrder, String idCuaHang, boolean up, String title, boolean superquangcao, String soDienThoai, String name, String ngayBatDau, String ngayKetThuc) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.chitiet = chitiet;
        Nhomsanpham = nhomsanpham;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soluong = soluong;
        this.imgProduct = imgProduct;
        this.nameImage = nameImage;
        this.giamGia = giamGia;
        this.status = status;
        this.donGiaOrder = donGiaOrder;
        this.idCuaHang = idCuaHang;
        this.up = up;
        this.title = title;
        this.superquangcao = superquangcao;
        this.soDienThoai = soDienThoai;
        this.name = name;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSuperquangcao() {
        return superquangcao;
    }

    public void setSuperquangcao(boolean superquangcao) {
        this.superquangcao = superquangcao;
    }

    public Product() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<DonGia> getDonGia() {
        return donGiaOrder;
    }

    public void setDonGia(ArrayList<DonGia> donGiaOrder) {
        this.donGiaOrder = donGiaOrder;
    }

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }

    public Double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(Double giamGia) {
        this.giamGia = giamGia;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
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

    public String getIdCuaHang() {
        return idCuaHang;
    }

    public void setIdCuaHang(String idCuaHang) {
        this.idCuaHang = idCuaHang;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }
}
