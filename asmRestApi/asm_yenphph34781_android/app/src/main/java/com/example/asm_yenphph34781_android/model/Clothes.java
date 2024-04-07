package com.example.asm_yenphph34781_android.model;

import java.util.ArrayList;

public class Clothes {
    private String _id, name, description;
    private int price;
    private ArrayList<String> image;

    private String createdAt, updatedAt;

    public Clothes(String _id, String name, String description, int price, ArrayList<String> image, String createdAt, String updatedAt) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Clothes() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
