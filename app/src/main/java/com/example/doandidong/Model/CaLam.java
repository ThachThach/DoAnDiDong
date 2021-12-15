package com.example.doandidong.Model;

import java.util.ArrayList;

public class CaLam {
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

    public ArrayList<Boolean> getCaSang() {
        return caSang;
    }

    public void setCaSang(ArrayList<Boolean> caSang) {
        this.caSang = caSang;
    }

    public ArrayList<Boolean> getCaToi() {
        return caToi;
    }

    public void setCaToi(ArrayList<Boolean> caToi) {
        this.caToi = caToi;
    }

    public ArrayList<Boolean> getCaChieu() {
        return caChieu;
    }

    public void setCaChieu(ArrayList<Boolean> caChieu) {
        this.caChieu = caChieu;
    }
}