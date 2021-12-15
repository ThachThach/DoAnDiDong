package com.example.doandidong.ChucNang.CuaHangOnline.Data;

public class DiaChiCuaHang {
    private String soNha;
    private String tinhThanhPho;
    private String quanHuyen;
    private String phuongXa;

    public DiaChiCuaHang() {
    }

    public DiaChiCuaHang(String soNha, String tinhThanhPho, String quanHuyen, String phuongXa) {
        this.soNha = soNha;
        this.tinhThanhPho = tinhThanhPho;
        this.quanHuyen = quanHuyen;
        this.phuongXa = phuongXa;
    }

    public String getSoNha() {
        return soNha;
    }

    public void setSoNha(String soNha) {
        this.soNha = soNha;
    }

    public String getTinhThanhPho() {
        return tinhThanhPho;
    }

    public void setTinhThanhPho(String tinhThanhPho) {
        this.tinhThanhPho = tinhThanhPho;
    }

    public String getQuanHuyen() {
        return quanHuyen;
    }

    public void setQuanHuyen(String quanHuyen) {
        this.quanHuyen = quanHuyen;
    }

    public String getPhuongXa() {
        return phuongXa;
    }

    public void setPhuongXa(String phuongXa) {
        this.phuongXa = phuongXa;
    }
}
