package com.example.ratecommerce.model;

public class Users {
    private String Name,Phonenumber,password,image,Address;

    public Users()
    {

    }

    public Users(String name, String phonenumber, String password, String image, String address) {
        Name = name;
        Phonenumber = phonenumber;
        this.password = password;
        this.image = image;
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
