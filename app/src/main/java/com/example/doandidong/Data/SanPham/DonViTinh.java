package com.example.doandidong.Data.SanPham;

public class DonViTinh {
    String TenDonViTinh;
    String id;

    public DonViTinh(String donViTinh, String id) {
        TenDonViTinh = donViTinh;
        this.id = id;
    }

    public DonViTinh() {
    }

    public String getDonViTinh() {
        return TenDonViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        TenDonViTinh = donViTinh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
