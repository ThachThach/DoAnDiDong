package com.example.doandidong.Data.KhachHang;

import java.io.Serializable;

public class KhachHang implements Serializable {
    String hoTenKhachHang;
    String soDT;
    String diaChiTinh;
    String diaChiHuyen;
    String diaChiXa;
    String soNha;
    String nhomKhachKhach;
    String gioiTinh;
    String email;
    String ghiChu;
    String ngaySinh;

    public KhachHang() {
    }

    public KhachHang(String hoTenKhachHang, String soDT) {
        this.hoTenKhachHang = hoTenKhachHang;
        this.soDT = soDT;
    }

    public KhachHang(String hoTenKhachHang, String soDT, String diaChiTinh, String diaChiHuyen, String diaChiXa, String soNha, String nhomKhachKhach, String gioiTinh, String email, String ghiChu, String ngaySinh) {
        this.hoTenKhachHang = hoTenKhachHang;
        this.soDT = soDT;
        this.diaChiTinh = diaChiTinh;
        this.diaChiHuyen = diaChiHuyen;
        this.diaChiXa = diaChiXa;
        this.soNha = soNha;
        this.nhomKhachKhach = nhomKhachKhach;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.ghiChu = ghiChu;
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChiTinh() {
        return diaChiTinh;
    }

    public void setDiaChiTinh(String diaChiTinh) {
        this.diaChiTinh = diaChiTinh;
    }

    public String getDiaChiHuyen() {
        return diaChiHuyen;
    }

    public void setDiaChiHuyen(String diaChiHuyen) {
        this.diaChiHuyen = diaChiHuyen;
    }

    public String getDiaChiXa() {
        return diaChiXa;
    }

    public void setDiaChiXa(String diaChiXa) {
        this.diaChiXa = diaChiXa;
    }

    public String getSoNha() {
        return soNha;
    }

    public void setSoNha(String soNha) {
        this.soNha = soNha;
    }

    public String getHoTenKhachHang() {
        return hoTenKhachHang;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setHoTenKhachHang(String hoTenKhachHang) {
        this.hoTenKhachHang = hoTenKhachHang;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getNhomKhachKhach() {
        return nhomKhachKhach;
    }

    public void setNhomKhachKhach(String nhomKhachKhach) {
        this.nhomKhachKhach = nhomKhachKhach;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
