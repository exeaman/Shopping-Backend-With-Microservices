package com.amazon.OrderService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.amazon.OrderService.dto.UserResponseDTO;


// UserServiceFeignClient.java
@FeignClient(name = "USERSERVICE")
public interface UserServiceFeignClient {
    @GetMapping("/api/users/{id}")
    UserResponseDTO getUserById(@PathVariable("id") Long id);

    @GetMapping("/api/users/validateuser/{id}")
    boolean validateuser(@PathVariable("id") Long id);
}
