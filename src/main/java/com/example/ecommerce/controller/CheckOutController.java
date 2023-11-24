package com.example.ecommerce.controller;

import com.example.ecommerce.model.*;
import com.example.ecommerce.service.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

@Controller
public class CheckOutController {
    private int ship = 30000;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @PostMapping("/checkout")
    public String checkout(Model model, @RequestParam String total) {
        model.addAttribute("products", cartService.getCartItems());
        model.addAttribute("orderTotal", Integer.parseInt(total)+ship);
        model.addAttribute("ship", ship);
        model.addAttribute("order", new Order());
        return "checkout/index";
    }
    @PostMapping("/checkout/confirm")
    public String checkoutConfirm(@ModelAttribute("order") Order o, Principal principal) throws MessagingException, IOException {
        var products = cartService.getCartItems();
        PaymentMethod p = paymentMethodService.getPaymentMethodById(o.getPaymentMethod().getId());
        int total = 0;
        if(principal != null) {
            User user = userDetailsService.getUserByString(principal.getName());
            o.getCustomer().setUser(user);
        }
        Customer c = customerService.createCustomer(o.getCustomer());
        Order order = new Order();
        order.setPaymentMethod(p);
        order.setOrderDate(new Date());
        order.setCustomer(c);
        order.setStatus(OrderStatus.DELIVERING);
        orderService.createOrder(order);
        for (var product : products) {
            OrderProduct op = new OrderProduct();
            op.setOrder(order);
            op.setProduct(product.getProduct());
            op.setQuantity(product.getQuantity());
            orderProductService.createOrderProduct(op);
            total+= product.getProduct().getPrice()*product.getQuantity();
            product.getProduct().setQuantity(product.getProduct().getQuantity()-product.getQuantity());
            productService.update(product.getProduct());
        }
        total = total+ship;
        order.setTotalAmount(BigDecimal.valueOf(total));
        orderService.updateOrder(order.getId(), order);

        orderService.updateOrder(order.getId(), order);
        cartService.removeAll();
        emailService.sendOrderDetail(c.getEmail(), "Order Tracking", order);

        return "Home/index";
    }
}
