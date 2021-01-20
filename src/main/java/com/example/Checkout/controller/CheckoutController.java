package com.example.Checkout.controller;
import com.example.Checkout.dto.CartDetails;
import com.example.Checkout.dto.OrderHistoryOfUser;
import com.example.Checkout.dto.OrderRating;
import com.example.Checkout.dto.Product;
import com.example.Checkout.entity.*;
import com.example.Checkout.service.CartService;
import com.example.Checkout.service.GetProductService;
import com.example.Checkout.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.ws.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value="/checkout")
public class CheckoutController {

    @Autowired
    CartService cartService;
    @Autowired
    GetProductService getProductService;

//    public Cart save(@RequestBody Cart cart){
//        System.out.println("abc");
//        return cartService.save(cart);
//    }

    @PostMapping(value = "/cart")
    public Cart save(@RequestBody Cart cart){
        return cartService.save(cart);
    }

    @PostMapping(value= "/cartdelete")
    public List<Cart> delete(@RequestBody Cart cart)
    {

        System.out.println("delete from cart");
        cartService.delete(cart);
         return cartService.findByUserId(cart.getUserId());
    }

    @PostMapping(value = "/updateuserid")
    public List<Cart> updateuserid(@RequestBody UpdateUserId updateUserId){
        cartService.updateuserid(updateUserId);
        return cartService.findByUserId(updateUserId.getUserId());

    }



    @GetMapping(value = "/cart/{userId}")
    List<Cart> cartDetailsOfUser(@PathVariable("userId") String userId){
        System.out.println("abc");
        return cartService.findByUserId(userId);
    }

    @GetMapping(value = "/try")
    public Flux<Product> getProducts(){
        return getProductService
                .getUserByIdAsync("6002736f8c0c75126439e910");
    }



    @GetMapping(value="/cart/findAll")
    List<Cart> findAllUsers(){
        return cartService.findAll();
    }


    @Autowired
    OrderService orderService;

    @GetMapping(value="/order/findAll")
    List<Order> findAllOrders(){
        return orderService.findAll();
    }

    @GetMapping(value = "/user/order/{userId}")
    List<Order> orderHistoryOfUser(@PathVariable("userId") String userId){
        return orderService.orderHistoryOfUser(userId);
    }

    @GetMapping(value = "/merchant/order/{merchantId}")
    List<Order> orderHistoryOfMerchant(@PathVariable("merchantId") String merchantId){
        return orderService.orderHistoryOfMerchant(merchantId);
    }

    @PostMapping(value = "/order/rating")
    boolean giveRating(@RequestBody OrderRating orderRating){


        return orderService.giveRating(orderRating);
    }



    @GetMapping("/order/{userId}")
    public List<Order> confirmOrder(@PathVariable("userId") String userId){
        return orderService.confirmOrder(userId);
    }



    @Autowired
    KafkaTemplate<String,String> stringStringKafkaTemplate;

    @GetMapping("/sendmail/{id}")
    public String sendmail(@PathVariable("id") String id){
        System.out.println("Mail in controller");
        stringStringKafkaTemplate.send("pqr",id);
        return "Done";

    }


    @PostMapping("/sendrating/{string}")
    public void sendrating(@PathVariable("string") String string)
    {
        stringStringKafkaTemplate.send("authrating",string);
        stringStringKafkaTemplate.send("ratingfinal",string);
    }

    @KafkaListener(topics = "updateprice",groupId = "team7")
    public void updatePrice(String string)

    {
        cartService.updatePrice(string);

    }


    @GetMapping(value = "/getcartwithqty/{userId}")
    public List<Cart> getcartwithqty(@PathVariable("userId") String userId) throws IOException
    {
        List<Cart> cartList = cartService.findByUserId(userId);
        List<CartWithQty> cartWithQties = new ArrayList<>();
        String str= new String();

        for (Cart c:cartList
             ) {

            str+=c.getProductId()+"*"+c.getMerchantId()+"@";
            System.out.println(c.toString());
        }

        String [] array = new String[cartList.size()];
        if(cartList.size()>0) {
            io.restassured.response.Response response = RestAssured.get("http://10.177.1.254:9080/merchant/getcartwithqty/" + str);
            System.out.println(response.print());
            String stringqty=response.print();
            stringqty=stringqty.substring(0,stringqty.length()-1);
//            int i=0;
//            while (!stringqty.equals(null))
//            {
//                int index=stringqty.indexOf("@");
//
//                    array[i]=stringqty.substring(0,index);
//                    if (!stringqty.equals(null))
//                     stringqty=stringqty.substring(index+1);
//
//            }
            array = response.print().split("@");
        }


            for (int i=0;i<cartList.size();i++)
            {
            cartWithQties.get(i).setCart(cartList.get(i));
            cartWithQties.get(i).setQuantity(Integer.parseInt(array[i]));
        }
        return cartList;

//        ObjectMapper objectMapper = new ObjectMapper();
//        String str=null;
//        try {
//            str = objectMapper.writeValueAsString(cartList,Cart);
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }

//        for (Cart c:cartList
//             ) {
//
//

//
//        }
    }






}
