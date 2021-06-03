package com.enfield.esoldy.Model;

public class Products {

 private String Product_Name,Price_INR,Description,Image,PID,Category,Date,Time;

 public Products()
 {

 }

 public Products(String product_Name, String price_INR, String description, String image, String PID, String category, String date, String time) {
  this.Product_Name = product_Name;
  this.Price_INR = price_INR;
  this.Description = description;
  this.Image = image;
  this.PID = PID;
  this.Category = category;
  this.Date = date;
  this.Time = time;
 }

 public String getProduct_Name() {
  return Product_Name;
 }

 public void setProduct_Name(String product_Name) {
  this.Product_Name = product_Name;
 }

 public String getPrice_INR() {
  return Price_INR;
 }

 public void setPrice_INR(String price_INR) {
  this.Price_INR = price_INR;
 }

 public String getDescription() {
  return Description;
 }

 public void setDescription(String description) {
  this.Description = description;
 }

 public String getImage() {
  return Image;
 }

 public void setImage(String image) {
  this.Image = image;
 }

 public String getPID() {
  return PID;
 }

 public void setPID(String PID) {
  this.PID = PID;
 }

 public String getCategory() {
  return Category;
 }

 public void setCategory(String category) {
  this.Category = category;
 }

 public String getDate() {
  return Date;
 }

 public void setDate(String date) {
  this.Date = date;
 }

 public String getTime() {
  return Time;
 }

 public void setTime(String time) {
  this.Time = time;
 }
}
