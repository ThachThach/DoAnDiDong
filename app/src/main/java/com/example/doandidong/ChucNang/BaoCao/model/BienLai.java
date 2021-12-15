package com.example.doandidong.ChucNang.BaoCao.model;

import java.io.Serializable;
import java.util.ArrayList;

public class BienLai implements Serializable {
    private String id_ban;
    private String id_khachhang;
    private String id_khuvuc;
    private String id_nhanvien;
    private int status;
    private float tongtien;
    private String id_datban;
    private String key;
    private boolean loai;
    private ArrayList<SanPham> sanpham;

    public BienLai() {
    }



    public BienLai(String id_ban, String id_khachhang, String id_khuvuc, String id_nhanvien, int status, float tongtien, String id_datban, ArrayList<SanPham> sanpham) {
        this.id_ban = id_ban;
        this.id_khachhang = id_khachhang;
        this.id_khuvuc = id_khuvuc;
        this.id_nhanvien = id_nhanvien;
        this.status = status;
        this.tongtien = tongtien;
        this.id_datban = id_datban;
        this.sanpham = sanpham;
    }

    public ArrayList<SanPham> getSanpham() {
        return sanpham;
    }

    public void setSanpham(ArrayList<SanPham> sanpham) {
        this.sanpham = sanpham;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isLoai() {
        return loai;
    }

    public void setLoai(boolean loai) {
        this.loai = loai;
    }

    public String getId_ban() {
        return id_ban;
    }

    public void setId_ban(String id_ban) {
        this.id_ban = id_ban;
    }

    public String getId_khachhang() {
        return id_khachhang;
    }

    public void setId_khachhang(String id_khachhang) {
        this.id_khachhang = id_khachhang;
    }

    public String getId_khuvuc() {
        return id_khuvuc;
    }

    public void setId_khuvuc(String id_khuvuc) {
        this.id_khuvuc = id_khuvuc;
    }

    public String getId_nhanvien() {
        return id_nhanvien;
    }

    public void setId_nhanvien(String id_nhanvien) {
        this.id_nhanvien = id_nhanvien;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getTongtien() {
        return tongtien;
    }

    public void setTongtien(float tongtien) {
        this.tongtien = tongtien;
    }

    public String getId_datban() {
        return id_datban;
    }

    public void setId_datban(String id_datban) {
        this.id_datban = id_datban;
    }


}
