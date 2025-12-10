package com.amazon.OrderService.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryReservationRequest {
    private Long inventoryItemId;
    private Integer quantity;
}
