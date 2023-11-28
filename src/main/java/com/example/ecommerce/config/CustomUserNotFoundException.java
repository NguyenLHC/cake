package com.example.ecommerce.config;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
// Kế thừa UsernameNotFoundException
// super chuyển thông điệp vào lớp cha để khởi tạo một UsernameNotFoundException với thông điệp tương ứng
public class CustomUserNotFoundException extends UsernameNotFoundException {
    public CustomUserNotFoundException(String message) {
        super(message);
    }
}
