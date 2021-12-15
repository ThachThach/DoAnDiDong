package com.example.doandidong.Model.SanPham;

public class Category {
    String id;
    String nameCategory;

    public Category(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public Category(String id, String nameCategory) {
        this.id = id;
        this.nameCategory = nameCategory;
    }

    public Category() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }
}
