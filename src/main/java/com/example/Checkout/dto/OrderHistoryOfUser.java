package com.example.Checkout.dto;

public class OrderHistoryOfUser {
    private int orderId;
    private String productId;
    private String merchantId;
    private int Quantity;
    private float price;
    private String orderTimeStamp;
    private double rating;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getOrderTimeStamp() {
        return orderTimeStamp;
    }

    public void setOrderTimeStamp(String orderTimeStamp) {
        this.orderTimeStamp = orderTimeStamp;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
