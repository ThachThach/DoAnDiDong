package com.example.doandidong.Data.NhanVien_CaLam;

import java.io.Serializable;
import java.util.ArrayList;

public class CaLam implements Serializable {
    ArrayList<Boolean> caSang;
    ArrayList<Boolean> caToi;
    ArrayList<Boolean> caChieu;

    public CaLam() {
        caSang = new ArrayList<>();
        caChieu = new ArrayList<>();
        caToi = new ArrayList<>();
    }

    public CaLam(ArrayList<Boolean> caSang, ArrayList<Boolean> caToi, ArrayList<Boolean> caChieu) {
        this.caSang = caSang;
        this.caToi = caToi;
        this.caChieu = caChieu;
    }



    public void setCaSang(ArrayList<Boolean> caSang) {
        this.caSang = caSang;
    }



    public void setCaToi(ArrayList<Boolean> caToi) {
        this.caToi = caToi;
    }



    public void setCaChieu(ArrayList<Boolean> caChieu) {
        this.caChieu = caChieu;
    }

    public ArrayList<Boolean> getCaSang() {
        return caSang;
    }

    public void set1(Boolean[] cSang) {
        this.caSang.clear();
        for (int i = 0; i < cSang.length; i++) {
            this.caSang.add(cSang[i]);
        }
    }


    public ArrayList<Boolean> getCaToi() {
        return caToi;
    }

    public void set3(Boolean[] cToi) {
        this.caToi.clear();
        for (int i = 0; i < cToi.length; i++) {
            this.caToi.add(cToi[i]);
        }
    }

    public ArrayList<Boolean> getCaChieu() {
        return caChieu;
    }

    public void set2(Boolean[] cChieu) {
        this.caChieu.clear();
        for (int i = 0; i < cChieu.length; i++) {
            this.caChieu.add(cChieu[i]);
        }
    }

}
