package com.example.Checkout.service.impl;

import com.example.Checkout.dto.CartMpr;
import com.example.Checkout.dto.OrderHistoryOfUser;
import com.example.Checkout.dto.OrderRating;
import com.example.Checkout.entity.Cart;
import com.example.Checkout.entity.Order;
import com.example.Checkout.entity.OrderDetails;
import com.example.Checkout.repository.CartRepository;
import com.example.Checkout.repository.OrderRepository;
import com.example.Checkout.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import sun.security.provider.certpath.OCSPResponse;

import javax.xml.ws.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Override
    public Order save(Order order) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        order.setOrderTimeStamp((dtf.format(now).toString()));
        return orderRepository.save(order);
    }

    @Override
    public List<Order> orderHistoryOfMerchant(String merchantId) {
        Iterable<Order> orderHistoryOfMerchantIterable = orderRepository.findByMerchantId(merchantId);
        List<Order> orderHistoryOfMerchantList = new ArrayList<>();
        orderHistoryOfMerchantIterable.forEach(orderHistoryOfMerchantList::add);
        return orderHistoryOfMerchantList;
    }



    @Override
    public List<Order> orderHistoryOfUser(String userId) {
        Iterable<Order> orderHistoryOfUserIterable = orderRepository.findByUserId(userId);
        List<Order> orderHistoryOfUserList = new ArrayList<>();
        orderHistoryOfUserIterable.forEach(orderHistoryOfUserList::add);
        return orderHistoryOfUserList;
    }




    @Override
    public List<Order> findAll() {
        Iterable<Order> orderIterable = orderRepository.findAll();
        List<Order> orderList = new ArrayList<>();
        orderIterable.forEach(orderList::add);
        return orderList;
    }

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Override
    public boolean giveRating(OrderRating orderRating) {
        Order order = orderRepository.findByOrderId(orderRating.getOrderId());
        order.setRating(orderRating.getRating());
        orderRepository.save(order);

        String merchantId = order.getMerchantId();
        double rating = orderRating.getRating();
        double merchantRating = orderRepository.calculateAvgRating(merchantId);
        System.out.println(merchantRating);
        String kafkamsg=order.getMerchantId()+"*"+merchantRating;
        //Test When Order is in Database
        kafkaTemplate.send("ratingfinal",kafkamsg);
        kafkaTemplate.send("authrating",kafkamsg);
        return true;
    }

    @Autowired
    CartRepository cartRepository;
    @Override
    public List<Order> confirmOrder(String userId) {

        List<Order> orderlist = new ArrayList<>();

        List<Cart> carttemp = cartRepository.findByUserId(userId);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String orderstring=null;
        for (Cart c: carttemp) {

            System.out.println("abc");
            Order order = new Order();
            order.setOrderTimeStamp((dtf.format(now).toString()));
            order.setUserId(c.getUserId());
            order.setMerchantId(c.getMerchantId());
            order.setPrice(c.getPrice());
            order.setQuantity(c.getQuantity());
            order.setProductId(c.getProductId());
            cartRepository.delete(c);
            Order order1=orderRepository.save(order);
            orderlist.add(order);
            orderstring+=String.valueOf(order1.getOrderId());
            orderstring+=".";

            CartMpr cartMpr = new CartMpr();
            cartMpr.setMerchantId(c.getMerchantId());
            cartMpr.setProductId(c.getProductId());
            cartMpr.setQuantity(c.getQuantity());

            ObjectMapper objectMapper = new ObjectMapper();

            String cartString ="";
            try {
                cartString =objectMapper.writeValueAsString(cartMpr);
            }catch (JsonProcessingException e){
                e.printStackTrace();
            }
            kafkaTemplate.send("confirmorder",cartString);
            io.restassured.response.Response response = RestAssured.get("http://localhost:9080/auth/getmail/"+userId);
            kafkaTemplate.send("123",response.print());
        }


        return orderlist;
    }
}
