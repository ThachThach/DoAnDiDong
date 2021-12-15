package com.example.doandidong.Data.KhuyenMai;

public class KhuyenMai {
    Double giaDeDuocKhuyenMai;
    String idCuahang;
    int loaiKhuyenmai;
    Double phanTramKhuyenMai;
    String tenQuanHuyen;
    String mota;

    public KhuyenMai() {
    }

    public KhuyenMai(Double giaDeDuocKhuyenMai, int loaiKhuyenmai) {
        this.giaDeDuocKhuyenMai = giaDeDuocKhuyenMai;
        this.loaiKhuyenmai = loaiKhuyenmai;
    }

    public KhuyenMai(Double giaDeDuocKhuyenMai, String idCuahang, int loaiKhuyenmai, Double phanTramKhuyenMai,String mota) {
        this.giaDeDuocKhuyenMai = giaDeDuocKhuyenMai;
        this.idCuahang = idCuahang;
        this.loaiKhuyenmai = loaiKhuyenmai;
        this.phanTramKhuyenMai = phanTramKhuyenMai;
        this.mota = mota;
    }

    public KhuyenMai(String idCuahang, int loaiKhuyenmai, String tenQuanHuyen, Double giaDeDuocKhuyenMai,String mota) {
        this.idCuahang = idCuahang;
        this.loaiKhuyenmai = loaiKhuyenmai;
        this.tenQuanHuyen = tenQuanHuyen;
        this.giaDeDuocKhuyenMai = giaDeDuocKhuyenMai;
        this.mota = mota;
    }

    public Double getGiaDeDuocKhuyenMai() {
        return giaDeDuocKhuyenMai;
    }

    public void setGiaDeDuocKhuyenMai(Double giaDeDuocKhuyenMai) {
        this.giaDeDuocKhuyenMai = giaDeDuocKhuyenMai;
    }

    public int getLoaiKhuyenmai() {
        return loaiKhuyenmai;
    }

    public void setLoaiKhuyenmai(int loaiKhuyenmai) {
        this.loaiKhuyenmai = loaiKhuyenmai;
    }

    public String getIdCuahang() {
        return idCuahang;
    }

    public void setIdCuahang(String idCuahang) {
        this.idCuahang = idCuahang;
    }

    public Double getPhanTramKhuyenMai() {
        return phanTramKhuyenMai;
    }

    public void setPhanTramKhuyenMai(Double phanTramKhuyenMai) {
        this.phanTramKhuyenMai = phanTramKhuyenMai;
    }

    public String getTenQuanHuyen() {
        return tenQuanHuyen;
    }

    public void setTenQuanHuyen(String tenQuanHuyen) {
        this.tenQuanHuyen = tenQuanHuyen;
    }
}
