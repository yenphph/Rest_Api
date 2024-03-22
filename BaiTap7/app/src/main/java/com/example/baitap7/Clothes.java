package com.example.baitap7;

import java.io.Serializable;

public class Clothes implements Serializable {
    private String _id;
    private String type;
    private String name;
    private double price;

    public Clothes(String _id, String type, String name, double price) {
        this._id = _id;
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public Clothes(String type, String name, double price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}