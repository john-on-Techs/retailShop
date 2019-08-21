package com.jotech.entity;

public class RunningBalanceProduct {
    private int id;
    private int productId;
    private int runningBalance;

    public RunningBalanceProduct() {
    }

    public RunningBalanceProduct(int productId, int runningBalance) {
        this.productId = productId;
        this.runningBalance = runningBalance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getRunningBalance() {
        return runningBalance;
    }

    public void setRunningBalance(int runningBalance) {
        this.runningBalance = runningBalance;
    }

    @Override
    public String toString() {
        return "RunningBalanceProduct{" +
                "id=" + id +
                ", productId=" + productId +
                ", runningBalance=" + runningBalance +
                '}';
    }
}
