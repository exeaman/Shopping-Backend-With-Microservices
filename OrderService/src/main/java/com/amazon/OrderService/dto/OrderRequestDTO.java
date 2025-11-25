
package com.amazon.OrderService.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @Size(min = 1, message = "Order must contain at least one item")
    private List<OrderLineItemDTO> items;
}