package com.example.doandidong.Data;

public class Address {
    private int soThuTu;
    private String tinh;
    private String[] huyen;

    public Address(int soThuTu, String tinh, String[] huyen) {
        this.soThuTu = soThuTu;
        this.tinh = tinh;
        this.huyen = huyen;
    }

    public int getSoThuTu() {
        return soThuTu;
    }

    public void setSoThuTu(int soThuTu) {
        this.soThuTu = soThuTu;
    }

    public Address() {
    }

    public String getTinh() {
        return tinh;
    }

    public void setTinh(String tinh) {
        this.tinh = tinh;
    }

    public String[] getHuyen() {
        return huyen;
    }

    public void setHuyen(String[] huyen) {
        this.huyen = huyen;
    }
}
