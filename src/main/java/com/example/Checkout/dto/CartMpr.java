package com.example.Checkout.dto;

public class CartMpr {

    private String productId;
    private String merchantId;
    private int quantity;


    @Override
    public String toString() {
        return "CartMpr{" +
                "productId='" + productId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", quantity=" + quantity +
                '}';
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
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
