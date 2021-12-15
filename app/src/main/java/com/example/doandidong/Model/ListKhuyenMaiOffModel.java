package com.example.doandidong.Model;

import java.util.ArrayList;

public class ListKhuyenMaiOffModel {
    private String Ngaybatdau;
    private String Ngayketthuc;
    private String Nhomkhachhang;
    private String Noidungkhuyenmai;
    private String Tenkhuyenmai;
    private ArrayList<KhuyenMaiOffModel> khuyenMaiOffModels;
    private String id;

    public ListKhuyenMaiOffModel() {
    }

    public ListKhuyenMaiOffModel(String ngaybatdau, String ngayketthuc, String nhomkhachhang, String noidungkhuyenmai, String tenkhuyenmai, ArrayList<KhuyenMaiOffModel> khuyenMaiOffModels) {
        Ngaybatdau = ngaybatdau;
        Ngayketthuc = ngayketthuc;
        Nhomkhachhang = nhomkhachhang;
        Noidungkhuyenmai = noidungkhuyenmai;
        Tenkhuyenmai = tenkhuyenmai;
        this.khuyenMaiOffModels = khuyenMaiOffModels;
    }

    public ListKhuyenMaiOffModel(String ngaybatdau, String ngayketthuc, String nhomkhachhang, String noidungkhuyenmai, String tenkhuyenmai, ArrayList<KhuyenMaiOffModel> khuyenMaiOffModels, String id) {
        Ngaybatdau = ngaybatdau;
        Ngayketthuc = ngayketthuc;
        Nhomkhachhang = nhomkhachhang;
        Noidungkhuyenmai = noidungkhuyenmai;
        Tenkhuyenmai = tenkhuyenmai;
        this.khuyenMaiOffModels = khuyenMaiOffModels;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNgaybatdau() {
        return Ngaybatdau;
    }

    public void setNgaybatdau(String ngaybatdau) {
        Ngaybatdau = ngaybatdau;
    }

    public String getNgayketthuc() {
        return Ngayketthuc;
    }

    public void setNgayketthuc(String ngayketthuc) {
        Ngayketthuc = ngayketthuc;
    }

    public String getNhomkhachhang() {
        return Nhomkhachhang;
    }

    public void setNhomkhachhang(String nhomkhachhang) {
        Nhomkhachhang = nhomkhachhang;
    }

    public String getNoidungkhuyenmai() {
        return Noidungkhuyenmai;
    }

    public void setNoidungkhuyenmai(String noidungkhuyenmai) {
        Noidungkhuyenmai = noidungkhuyenmai;
    }

    public String getTenkhuyenmai() {
        return Tenkhuyenmai;
    }

    public void setTenkhuyenmai(String tenkhuyenmai) {
        Tenkhuyenmai = tenkhuyenmai;
    }

    public ArrayList<KhuyenMaiOffModel> getKhuyenMaiOffModels() {
        return khuyenMaiOffModels;
    }

    public void setKhuyenMaiOffModels(ArrayList<KhuyenMaiOffModel> khuyenMaiOffModels) {
        this.khuyenMaiOffModels = khuyenMaiOffModels;
    }
}