package com.example.doandidong.function.CuaHangOnline.Data;

public class ThongTinCuaHang {
    private String id;
    private String name;
    private String soDienThoai;
    private String namelogo;
    private String logoUrl;
    private String moTa;

    public ThongTinCuaHang() {
    }

    public ThongTinCuaHang(String id, String name, String soDienThoai, String namelogo, String logoUrl, String moTa) {
        this.id = id;
        this.name = name;
        this.soDienThoai = soDienThoai;
        this.namelogo = namelogo;
        this.logoUrl = logoUrl;
        this.moTa = moTa;
    }

    public ThongTinCuaHang(String name, String soDienThoai, String moTa) {
        this.name = name;
        this.soDienThoai = soDienThoai;
        this.moTa = moTa;
    }

    public ThongTinCuaHang(String name, String soDienThoai, String namelogo, String logoUrl, String moTa) {
        this.name = name;
        this.soDienThoai = soDienThoai;
        this.namelogo = namelogo;
        this.logoUrl = logoUrl;
        this.moTa = moTa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getNamelogo() {
        return namelogo;
    }

    public void setNamelogo(String namelogo) {
        this.namelogo = namelogo;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
