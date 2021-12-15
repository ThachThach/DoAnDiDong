package com.example.doandidong.ChucNang.CuaHangOnline.Data;

public class ThoiGian {
    private String batDau;
    private String ketThuc;
    private boolean status;

    public ThoiGian() {
    }

    public ThoiGian(String batDau, String ketThuc, boolean status) {
        this.batDau = batDau;
        this.ketThuc = ketThuc;
        this.status = status;
    }

    public String getBatDau() {
        return batDau;
    }

    public void setBatDau(String batDau) {
        this.batDau = batDau;
    }

    public String getKetThuc() {
        return ketThuc;
    }

    public void setKetThuc(String ketThuc) {
        this.ketThuc = ketThuc;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
