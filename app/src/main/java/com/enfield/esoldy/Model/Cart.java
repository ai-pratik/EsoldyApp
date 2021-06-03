package com.enfield.esoldy.Model;

public class Cart {

    private String PID;
    private String Product_name;
    private String Price_INR;
    private String Quantity;
    private String Discount;

    public Cart() {
    }

    public Cart(String PID, String product_name, String price_INR, String quantity, String discount) {
        this.PID = PID;
        Product_name = product_name;
        Price_INR = price_INR;
        Quantity = quantity;
        Discount = discount;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getProduct_name() {
        return Product_name;
    }

    public void setProduct_name(String product_name) {
        Product_name = product_name;
    }

    public String getPrice_INR() {
        return Price_INR;
    }

    public void setPrice_INR(String price_INR) {
        Price_INR = price_INR;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
