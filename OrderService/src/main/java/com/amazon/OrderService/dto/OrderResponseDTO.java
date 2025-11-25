package com.amazon.OrderService.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.amazon.OrderService.utils.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private Long userId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private OrderStatus status;
    private List<OrderLineItemDTO> items; // Can reuse the request DTO
}