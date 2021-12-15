package com.example.doandidong.function.CuaHangOnline.Data;

public class GiaoHang {
    private String name;
    private long phiShip;
    private long phiMin;
    private long phimax;

    public GiaoHang() {
    }

    public GiaoHang(String name, long phiShip, long phiMin, long phimax) {
        this.name = name;
        this.phiShip = phiShip;
        this.phiMin = phiMin;
        this.phimax = phimax;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhiShip() {
        return phiShip;
    }

    public void setPhiShip(long phiShip) {
        this.phiShip = phiShip;
    }

    public long getPhiMin() {
        return phiMin;
    }

    public void setPhiMin(long phiMin) {
        this.phiMin = phiMin;
    }

    public long getPhimax() {
        return phimax;
    }

    public void setPhimax(long phimax) {
        this.phimax = phimax;
    }
}
