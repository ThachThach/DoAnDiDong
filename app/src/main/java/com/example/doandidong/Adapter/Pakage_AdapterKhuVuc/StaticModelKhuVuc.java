package com.example.doandidong.Adapter.Pakage_AdapterKhuVuc;

import com.example.doandidong.Adapter.Pakage_AdapterBan.StaticBanModel;
import java.io.Serializable;
import java.util.ArrayList;

public class StaticModelKhuVuc implements Serializable {

    private String Id_khuvuc;
    private ArrayList<StaticBanModel> staticBanModels ;
    private String tenkhuvuc;
    private  String trangthai;

    public String getTenkhuvuc() {
        return tenkhuvuc;
    }

    public String getTrangthai() {
        return trangthai;
    }



    public StaticModelKhuVuc(String tenkhuvuc, String trangthai, ArrayList<StaticBanModel> staticBanModels) {
        this.tenkhuvuc = tenkhuvuc;
        this.trangthai = trangthai;
        this.staticBanModels = staticBanModels;
    }

    public StaticModelKhuVuc(String tenkhuvuc, String trangthai, String id_khuvuc, ArrayList<StaticBanModel> staticBanModels) {
        this.tenkhuvuc = tenkhuvuc;
        this.trangthai = trangthai;
       this.Id_khuvuc = id_khuvuc;
        this.staticBanModels = staticBanModels;
    }

    public void setTenkhuvuc(String tenkhuvuc) {
        this.tenkhuvuc = tenkhuvuc;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getId_khuvuc() {
        return Id_khuvuc;
    }

    public void setId_khuvuc(String id_khuvuc) {
        Id_khuvuc = id_khuvuc;
    }

    public void setStaticBanModels(ArrayList<StaticBanModel> staticBanModels) {
        this.staticBanModels = staticBanModels;
    }

    public ArrayList<StaticBanModel> getStaticBanModels() {
        return staticBanModels;
    }


    public StaticModelKhuVuc() {
    }

    public StaticModelKhuVuc(String tenkhuvuc, String trangthai, String id_khuvuc) {
        this.tenkhuvuc = tenkhuvuc;
        this.trangthai = trangthai;
        Id_khuvuc = id_khuvuc;
    }

    public StaticModelKhuVuc(String ten, String trangThai) {
        this.tenkhuvuc = ten;
        this.trangthai = trangThai;
    }


}
