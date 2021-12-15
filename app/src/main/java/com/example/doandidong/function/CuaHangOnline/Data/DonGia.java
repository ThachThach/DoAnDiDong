package com.example.doandidong.function.CuaHangOnline.Data;

public class DonGia {
    private String id;
    private Boolean check;
    private long giaBan;
    private long giachung;
    private String tenDonGia;

    public DonGia() {
    }

    public DonGia(String id, Boolean check, long giaBan, long giachung, String tenDonGia) {
        this.id = id;
        this.check = check;
        this.giaBan = giaBan;
        this.giachung = giachung;
        this.tenDonGia = tenDonGia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public long getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(long giaBan) {
        this.giaBan = giaBan;
    }

    public long getGiachung() {
        return giachung;
    }

    public void setGiachung(long giachung) {
        this.giachung = giachung;
    }

    public String getTenDonGia() {
        return tenDonGia;
    }

    public void setTenDonGia(String tenDonGia) {
        this.tenDonGia = tenDonGia;
    }
}
