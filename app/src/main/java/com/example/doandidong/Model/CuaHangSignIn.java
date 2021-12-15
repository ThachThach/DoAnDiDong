package com.example.doandidong.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class CuaHangSignIn implements Parcelable {

    private String ChucVu;
    private String ID;
    private String Name;

    public CuaHangSignIn() {
    }

    public CuaHangSignIn(String chucVu, String ID, String name) {
        this.ChucVu = chucVu;
        this.ID = ID;
        this.Name = name;
    }

    protected CuaHangSignIn(Parcel in) {
        ChucVu = in.readString();
        ID = in.readString();
        Name = in.readString();
    }

    public static final Creator<CuaHangSignIn> CREATOR = new Creator<CuaHangSignIn>() {
        @Override
        public CuaHangSignIn createFromParcel(Parcel in) {
            return new CuaHangSignIn(in);
        }

        @Override
        public CuaHangSignIn[] newArray(int size) {
            return new CuaHangSignIn[size];
        }
    };

    public String getChucVu() {
        return ChucVu;
    }

    public void setChucVu(String chucVu) {
        ChucVu = chucVu;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ChucVu);
        dest.writeString(ID);
        dest.writeString(Name);
    }
}
