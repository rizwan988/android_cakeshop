package com.example.bshop;

public class HelperClass2 {

    String username, email, shopname, address, password;

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String username) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HelperClass2(String username, String email, String shopname, String address, String password) {
        this.username = username;
        this.email = email;
        this.shopname = shopname;
        this.address = address;
        this.password = password;
    }

    public HelperClass2() {
    }
}
