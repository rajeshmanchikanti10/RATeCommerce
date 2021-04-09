package com.example.ratecommerce;

public class Cart {
    private  String Pid,Pname,description,price,quantity,time;
    public Cart(){

    }

    public Cart(String pid, String pname, String description, String price, String quantity, String time) {
        Pid = pid;
        Pname = pname;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.time = time;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
