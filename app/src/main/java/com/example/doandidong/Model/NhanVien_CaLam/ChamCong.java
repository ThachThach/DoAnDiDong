package com.example.doandidong.Model.NhanVien_CaLam;

import java.io.Serializable;

public class ChamCong implements Serializable {
    private int tongCaLam;
    private int tongCaNghi;
    private int tongTangCa;

    public ChamCong() {
    }

    public ChamCong(int tongCaLam, int tongCaNghi, int tongTangCa) {
        this.tongCaLam = tongCaLam;
        this.tongCaNghi = tongCaNghi;
        this.tongTangCa = tongTangCa;
    }

    public int getTongCaLam() {
        return tongCaLam;
    }

    public void setTongCaLam(int tongCaLam) {
        this.tongCaLam = tongCaLam;
    }

    public int getTongCaNghi() {
        return tongCaNghi;
    }

    public void setTongCaNghi(int tongCaNghi) {
        this.tongCaNghi = tongCaNghi;
    }

    public int getTongTangCa() {
        return tongTangCa;
    }

    public void setTongTangCa(int tongTangCa) {
        this.tongTangCa = tongTangCa;
    }
}
