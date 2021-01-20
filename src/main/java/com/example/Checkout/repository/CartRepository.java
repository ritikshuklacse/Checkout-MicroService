package com.example.Checkout.repository;

import com.example.Checkout.dto.CartDetails;
import com.example.Checkout.entity.Cart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, String> {
    List<Cart> findByUserId(String userId);

    @Override
    void delete(Cart cart);

    List<Cart> findByProductId(String pId);
}
