package com.example.Checkout.entity;

public class CartWithQty {

   Cart cart;
   int quantity;

    public Cart getCart() {
        return cart;
    }

    @Override
    public String toString() {
        return "CartWithQty{" +
                "cart=" + cart +
                ", quantity=" + quantity +
                '}';
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
