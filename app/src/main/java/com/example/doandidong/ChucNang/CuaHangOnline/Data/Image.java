package com.example.doandidong.ChucNang.CuaHangOnline.Data;

public class Image {
    private String key;
    private String imageUrl;
    private String imageName;

    public Image() {
    }

    public Image(String key, String imageUrl, String imageName) {
        this.key = key;
        this.imageUrl = imageUrl;
        this.imageName = imageName;
    }

    public Image(String key, String imageUrl) {
        this.key = key;
        this.imageUrl = imageUrl;
    }

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
