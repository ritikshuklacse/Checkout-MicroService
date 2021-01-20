package com.example.Checkout.service;

import com.example.Checkout.dto.CartDetails;
import com.example.Checkout.entity.Cart;
import com.example.Checkout.entity.UpdateUserId;

import java.util.List;

public interface CartService {
    Cart save(Cart cart);
    List<Cart> findByUserId(String userId);
    List<Cart> findAll();
    void delete(Cart cart);
    List<Cart> findByProductId(String pId);
    void updatePrice(String string);

    void updateuserid(UpdateUserId updateUserId);
//    List<Cart> cartDetailsofUser(String userId);
}
