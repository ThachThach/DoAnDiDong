package com.example.doandidong.Adapter.Pakage_AdapterBan;

import java.io.Serializable;

public class StaticBanModel implements Serializable {
    private String ID ;
    private   String tenban;
    private  String tenNhanVien;
    private  String gioDaOder;
    private String id_khuvuc;

    public StaticBanModel(String ID, String tenban, String tenNhanVien, String gioDaOder, String id_khuvuc, String trangthai) {
        this.ID = ID;
        this.tenban = tenban;
        this.tenNhanVien = tenNhanVien;
        this.gioDaOder = gioDaOder;
        this.id_khuvuc = id_khuvuc;
        this.trangthai = trangthai;
    }

    public StaticBanModel(String ID, String tenban, String trangthai, String tenNhanVien, String gioDaOder) {
        this.ID = ID;
        this.tenban = tenban;
        this.trangthai = trangthai;
        this.tenNhanVien = tenNhanVien;
        this.gioDaOder = gioDaOder;
    }

    public String getId_khuvuc() {
        return id_khuvuc;
    }

    public void setId_khuvuc(String id_khuvuc) {
        this.id_khuvuc = id_khuvuc;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTenban(String tenban) {
        this.tenban = tenban;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public void setGioDaOder(String gioDaOder) {
        this.gioDaOder = gioDaOder;
    }


    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public String getGioDaOder() {
        return gioDaOder;
    }
    public String getTenban() {
        return tenban;
    }

    public String getTrangthai() {
        return trangthai;
    }



    public StaticBanModel(String tenban, String trangthai, String tenNhanVien, String gioDaOder) {
        this.tenban = "";
        this.trangthai = "";
        this.tenNhanVien = "";
        this.gioDaOder = "";
    }

    public StaticBanModel() {
    }

    private   String trangthai;

    public StaticBanModel(String tenban, String trangthai) {
        this.tenban = tenban;
        this.trangthai = trangthai;
    }

}
