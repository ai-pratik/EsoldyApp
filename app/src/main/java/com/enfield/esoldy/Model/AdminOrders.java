package com.enfield.esoldy.Model;

public class AdminOrders {
    private String Date,State,Time,Total_Amount,address,city,name,phone;

    public AdminOrders() {
    }

    public AdminOrders(String date, String state, String time, String total_amount, String address, String city, String name, String phone) {
        Date = date;
        State = state;
        Time = time;
        Total_Amount = total_amount;
        this.address = address;
        this.city = city;
        this.name = name;
        this.phone = phone;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(String total_amount) {
        Total_Amount = total_amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
