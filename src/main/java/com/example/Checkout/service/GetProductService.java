package com.example.Checkout.service;

import com.example.Checkout.dto.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GetProductService {
    public Flux<Product> getUserByIdAsync(final String id);
}
