package com.example.android_lab5_yenphph34781.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Fruit {
    private String _id, name, quantity, status, price;
    private String description;
    private ArrayList<String> image;
    @SerializedName("id_distributor")
    Distributor distributor;
    private String createAt, updateAt;

    public Fruit(String _id, String name, String quantity, String status, String price, String description, ArrayList<String> image, Distributor distributor, String createAt, String updateAt) {
        this._id = _id;
        this.name = name;
        this.quantity = quantity;
        this.status = status;
        this.price = price;
        this.description = description;
        this.image = image;
        this.distributor = distributor;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Fruit() {
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}
