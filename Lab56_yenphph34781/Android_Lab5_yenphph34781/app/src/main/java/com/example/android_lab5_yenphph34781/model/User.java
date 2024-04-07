package com.example.android_lab5_yenphph34781.model;

public class User {
    private String _id, username, password, email, name, avartar, available;
    private String createAt, updateat;

    public User() {
    }

    public User(String _id, String username, String password, String email, String name, String avartar, String available, String createAt, String updateat) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.avartar = avartar;
        this.available = available;
        this.createAt = createAt;
        this.updateat = updateat;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvartar() {
        return avartar;
    }

    public void setAvartar(String avartar) {
        this.avartar = avartar;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateat() {
        return updateat;
    }

    public void setUpdateat(String updateat) {
        this.updateat = updateat;
    }
}
