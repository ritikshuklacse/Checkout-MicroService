package com.example.Checkout.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
@Entity
@Table(name = "orderhistory")
public class Order {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    int orderId;

    String userId;
    String productId;
    String merchantId;
    int quantity;
    double price;
    @JsonFormat(pattern = "dd/MM/yyyy")
    String orderTimeStamp;
    double rating;

    public double getRating() {

        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderTimeStamp() {
        return orderTimeStamp;
    }

    public void setOrderTimeStamp(String orderTimeStamp) {
        this.orderTimeStamp = orderTimeStamp;
    }
}
