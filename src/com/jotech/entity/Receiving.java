package com.jotech.entity;

import java.util.Date;

public class Receiving {
    private int receiveId;
    private int batchNo;
    private Date date;
    private int productId;
    private int quantity;
    private double buyingPrice;
    private double sellingPrice;
    private int userId;

    public Receiving() {
    }

    public Receiving(int batchNo, int productId, int quantity, double buyingPrice, double sellingPrice,int userId) {
        this.batchNo = batchNo;
        this.productId = productId;
        this.quantity = quantity;      ;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.userId =userId;
    }

    public int getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }

    public int getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(int batchNo) {
        this.batchNo = batchNo;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Receiving{" +
                "receiveId=" + receiveId +
                ", batchNo=" + batchNo +
                ", date=" + date +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", buyingPrice=" + buyingPrice +
                ", sellingPrice=" + sellingPrice +
                ", userId=" + userId +
                '}';
    }
}
