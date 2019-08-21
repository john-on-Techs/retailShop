package com.jotech.entity;

import java.util.Date;

public class Sale {
    private int id;
    private Date date;
    private int productId;
    private double sellingPrice;
    private int quantity;
    private int userId;

    public Sale() {
    }

    public Sale( Date date, int productId, double sellingPrice, int quantity,int userId) {
        this.date = date;
        this.productId = productId;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.userId =userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", date=" + date +
                ", productId=" + productId +
                ", sellingPrice=" + sellingPrice +
                ", quantity=" + quantity +
                ", userId=" + userId +
                '}';
    }
}
