package com.example.ecommerce.service;

import com.example.ecommerce.model.PaymentMethod;
import com.example.ecommerce.repo.PaymentMethodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodService {

    private final PaymentMethodRepo paymentMethodRepository;

    @Autowired
    public PaymentMethodService(PaymentMethodRepo paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public PaymentMethod getPaymentMethodById(Long id) {
        return paymentMethodRepository.findById(id).orElse(null);
    }


}
