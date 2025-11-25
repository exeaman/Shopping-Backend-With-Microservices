package com.amazon.InventoryService.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItemResponseDTO {
 private Long id;
 private String productCode;
 private String name;
 private Integer quantity;
 private Double price;
}