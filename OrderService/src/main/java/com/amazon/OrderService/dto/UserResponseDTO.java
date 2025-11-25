package com.amazon.OrderService.dto;


import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String userRole;
    private String shippingAddress; // Assuming User Service also stores this
}