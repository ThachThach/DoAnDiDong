package com.example.doandidong.ChucNang.CuaHangOnline.Data;

public class HinhThucGiaoHang {
//    private boolean quaDoiTac;
    private boolean tienMat;
    private boolean chuyenKhoan;
    private String thongTinChuyenKhoan;
    private String ghiChu;

    public HinhThucGiaoHang() {
    }

    public HinhThucGiaoHang(boolean tienMat, boolean chuyenKhoan, String thongTinChuyenKhoan, String ghiChu) {
//        this.quaDoiTac = quaDoiTac;
//        this.tuGiao = tuGiao;
        this.tienMat = tienMat;
        this.chuyenKhoan = chuyenKhoan;
        this.thongTinChuyenKhoan = thongTinChuyenKhoan;
        this.ghiChu = ghiChu;
    }

//    public boolean isQuaDoiTac() {
//        return quaDoiTac;
//    }
//
//    public void setQuaDoiTac(boolean quaDoiTac) {
//        this.quaDoiTac = quaDoiTac;
//    }

//    public boolean isTuGiao() {
//        return tuGiao;
//    }
//
//    public void setTuGiao(boolean tuGiao) {
//        this.tuGiao = tuGiao;
//    }

    public boolean isTienMat() {
        return tienMat;
    }

    public void setTienMat(boolean tienMat) {
        this.tienMat = tienMat;
    }

    public boolean isChuyenKhoan() {
        return chuyenKhoan;
    }

    public void setChuyenKhoan(boolean chuyenKhoan) {
        this.chuyenKhoan = chuyenKhoan;
    }

    public String getThongTinChuyenKhoan() {
        return thongTinChuyenKhoan;
    }

    public void setThongTinChuyenKhoan(String thongTinChuyenKhoan) {
        this.thongTinChuyenKhoan = thongTinChuyenKhoan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
