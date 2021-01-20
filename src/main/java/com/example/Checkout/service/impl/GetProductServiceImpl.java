package com.example.Checkout.service.impl;

import com.example.Checkout.dto.Product;
import com.example.Checkout.service.GetProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GetProductServiceImpl implements GetProductService {

    WebClient client = WebClient.create("http://10.177.1.104:8080");

    @Override
    public Flux<Product> getUserByIdAsync(final String id) {
        return client
                .get()
                .uri(String.join("", "/product/", id))
                .retrieve()
                .bodyToFlux(Product.class);
    }

}
