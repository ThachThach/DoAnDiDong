package com.example.doandidong.Data;

public class ThuChi {
    private String lydo;
    private long tongchi;
    private String ngayThang;

    public ThuChi(String lydo, long tongchi, String ngayThang) {
        this.lydo = lydo;
        this.tongchi = tongchi;
        this.ngayThang = ngayThang;
    }

    public String getLydo() {
        return lydo;
    }

    public void setLydo(String lydo) {
        this.lydo = lydo;
    }

    public long getTongchi() {
        return tongchi;
    }

    public void setTongchi(long tongchi) {
        this.tongchi = tongchi;
    }

    public String getNgayThang() {
        return ngayThang;
    }

    public void setNgayThang(String ngayThang) {
        this.ngayThang = ngayThang;
    }

    public ThuChi() {
    }
}
