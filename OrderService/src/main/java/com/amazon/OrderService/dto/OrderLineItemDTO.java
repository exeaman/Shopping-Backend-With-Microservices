package com.amazon.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItemDTO {
    // This is the ID of the product in the Inventory Service
    private Long inventoryItemId;
    
    private Integer quantity;
    
    // The price, fetched from Inventory Service and included in the request
    private Double unitPrice; 
}