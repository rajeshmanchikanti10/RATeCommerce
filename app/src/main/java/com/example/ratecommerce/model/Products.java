package com.example.ratecommerce.model;

public class Products {
    public  String pname,price,description,category,image,time;
    public Products(){

    }

    public Products(String pname, String price, String description, String category, String image, String time) {
        this.pname = pname;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
        this.time = time;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
