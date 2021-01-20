package com.example.Checkout.repository;

import com.example.Checkout.dto.CartDetails;
import com.example.Checkout.dto.OrderHistoryOfUser;
import com.example.Checkout.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
//    Order findByUserId(String userId);
    List<Order> findByUserId(String userId);

    List<Order> findByMerchantId(String merchantId);

    Order findByOrderId(int orderId);

    @Query(value = "select avg(rating) from orderhistory where merchant_id=:merchantId", nativeQuery = true)
    double calculateAvgRating(@Param("merchantId") String merchantId);
}
