package com.example.ecommerce.service;

import com.example.ecommerce.model.OrderProduct;
import com.example.ecommerce.repo.OrderProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class OrderProductService {

    @Autowired
    private OrderProductRepo orderProductRepository;

    // Create a new order-product association
    public OrderProduct createOrderProduct(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

}
