package com.example.Checkout.service;

import com.example.Checkout.dto.CartDetails;
import com.example.Checkout.dto.OrderHistoryOfUser;
import com.example.Checkout.dto.OrderRating;
import com.example.Checkout.entity.Order;
import com.example.Checkout.entity.OrderDetails;

import java.util.List;
public interface OrderService {
    Order save(Order order);
    List<Order> findAll();
    List<Order> orderHistoryOfUser(String userId);

    List<Order> orderHistoryOfMerchant(String merchantId);

    boolean giveRating(OrderRating orderRating);

    List<Order> confirmOrder(String userId);


//    List<OrderHistoryOfUser> findByUserId(String userId);


//    Order findByUserId(String userId);

//    OrderHistoryOfUser orderHistoryOfUser(String userId);
}
