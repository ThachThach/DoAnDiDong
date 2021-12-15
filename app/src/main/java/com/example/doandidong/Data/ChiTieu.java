package com.example.doandidong.Data;

public class ChiTieu {
    private String key;
    private String lyDo;
    private Double tongChi;

    public ChiTieu() {
    }

    public ChiTieu(String key, String lyDo, Double tongChi) {
        this.key = key;
        this.lyDo = lyDo;
        this.tongChi = tongChi;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLyDo() {
        return lyDo;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public Double getTongChi() {
        return tongChi;
    }

    public void setTongChi(Double tongChi) {
        this.tongChi = tongChi;
    }
}
