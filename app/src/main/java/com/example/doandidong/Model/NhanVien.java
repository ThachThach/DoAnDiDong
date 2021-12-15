package com.example.doandidong.Model;

import com.example.doandidong.Model.NhanVien_CaLam.ChamCong;
import java.util.ArrayList;

public class NhanVien {
    String username;
    String email;
    ArrayList<Boolean> chucVu;
    CaLam caLam;
    String phone;
    String id;
    boolean chuCuaHang;
    ChamCong chamcong;

    public NhanVien() {
    }

    public NhanVien(String username, String email, String phone, String id) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.id = id;
    }

    public NhanVien(String username, String email, ArrayList<Boolean> chucVu, CaLam caLam, String phone, String id) {
        this.username = username;
        this.email = email;
        this.chucVu = chucVu;
        this.caLam = caLam;
        this.phone = phone;
        this.id = id;
    }

    public NhanVien(String username, String email, ArrayList<Boolean> chucVu, CaLam caLam, String phone, String id, boolean chuCuaHang) {
        this.username = username;
        this.email = email;
        this.chucVu = chucVu;
        this.caLam = caLam;
        this.phone = phone;
        this.id = id;
        this.chuCuaHang = chuCuaHang;
    }

    public NhanVien(String username, String email, ArrayList<Boolean> chucVu, CaLam caLam, String phone, String id, boolean chuCuaHang, ChamCong chamcong) {
        this.username = username;
        this.email = email;
        this.chucVu = chucVu;
        this.caLam = caLam;
        this.phone = phone;
        this.id = id;
        this.chuCuaHang = chuCuaHang;
        this.chamcong = chamcong;
    }

    public ChamCong getChamcong() {
        return chamcong;
    }

    public void setChamcong(ChamCong chamcong) {
        this.chamcong = chamcong;
    }

    public boolean isChuCuaHang() {
        return chuCuaHang;
    }

    public void setChuCuaHang(boolean chuCuaHang) {
        this.chuCuaHang = chuCuaHang;
    }

    public ArrayList<Boolean> getChucVu() {
        return chucVu;
    }

    public void setChucVu(ArrayList<Boolean> chucVu) {
        this.chucVu = chucVu;
    }

    public CaLam getCaLam() {
        return caLam;
    }

    public void setCaLam(CaLam caLam) {
        this.caLam = caLam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
