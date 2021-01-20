package com.example.Checkout.service.impl;


import com.example.Checkout.dto.CartDetails;
import com.example.Checkout.entity.Cart;
import com.example.Checkout.entity.UpdateUserId;
import com.example.Checkout.repository.CartRepository;
import com.example.Checkout.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Override
    public Cart save(Cart cart) {
        //System.out.println("abcservice");
        return cartRepository.save(cart);
    }

    @Override
    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }

    @Override
    public void updateuserid(UpdateUserId updateUserId) {
        List<Cart> cart = cartRepository.findByUserId(updateUserId.getMacAddress());
        for (Cart c:cart) {
            c.setUserId(updateUserId.getUserId());
        }
    }

    @Override
    public void updatePrice(String string) {

        System.out.println("string");
        int index = string.indexOf('*');
        String productId = string.substring(0,index);
        String priceString = string.substring(index+1);
        System.out.println(productId);
        System.out.println(priceString);

        double price= Double.valueOf(priceString);
        System.out.println(price);
        List<Cart> cartList = findByProductId(productId);
        for (Cart c: cartList) {
            c.setPrice(price);
            cartRepository.save(c);
        }

    }

    @Override
    public List<Cart> findByProductId(String pId) {
        return cartRepository.findByProductId(pId);
    }

    @Override
    public List<Cart> findByUserId(String userId) {

        Iterable<Cart> cartIterable =  cartRepository.findByUserId(userId);
        List<Cart> cartList = new ArrayList<>();
        cartIterable.forEach(cartList::add);
        return cartList;
    }

    @Override
    public List<Cart> findAll() {
        Iterable<Cart> cartIterable = cartRepository.findAll();
        List<Cart> cartList = new ArrayList<>();
        cartIterable.forEach(cartList::add);
        return cartList;
    }



}
