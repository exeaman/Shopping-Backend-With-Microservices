package com.amazon.OrderService.service;

import java.util.List;

import com.amazon.OrderService.dto.OrderRequestDTO;
import com.amazon.OrderService.dto.OrderResponseDTO;

public interface OrderService {
    OrderResponseDTO placeOrder(OrderRequestDTO request);
    OrderResponseDTO getOrder(Long orderId);
    List<OrderResponseDTO> getOrdersByUser(Long userId);
    void cancelOrder(Long orderId);
    boolean checkUser(Long id);
}
