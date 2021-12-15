package com.example.doandidong.Model;

public class KhuyenMaiOffModel {
     String giakhuyenmaitu;
     String giakhuyenmaiden;
     String giakhuyenmai;
     String key;
     Boolean check =false;

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public KhuyenMaiOffModel() {
    }
    public String getGiakhuyenmaitu() {
        return giakhuyenmaitu;
    }

    public void setGiakhuyenmaitu(String giakhuyenmaitu) {
        this.giakhuyenmaitu = giakhuyenmaitu;
    }

    public String getGiakhuyenmaiden() {
        return giakhuyenmaiden;
    }

    public void setGiakhuyenmaiden(String giakhuyenmaiden) {
        this.giakhuyenmaiden = giakhuyenmaiden;
    }

    public String getGiakhuyenmai() {
        return giakhuyenmai;
    }

    public void setGiakhuyenmai(String giakhuyenmai) {
        this.giakhuyenmai = giakhuyenmai;
    }

    public KhuyenMaiOffModel(String giakhuyenmaitu, String giakhuyenmaiden, String giakhuyenmai) {
        this.giakhuyenmaitu = giakhuyenmaitu;
        this.giakhuyenmaiden = giakhuyenmaiden;
        this.giakhuyenmai = giakhuyenmai;
    }

    public KhuyenMaiOffModel(String giakhuyenmaitu, String giakhuyenmaiden, String giakhuyenmai, String key) {
        this.giakhuyenmaitu = giakhuyenmaitu;
        this.giakhuyenmaiden = giakhuyenmaiden;
        this.giakhuyenmai = giakhuyenmai;
        this.key = key;
    }
}
