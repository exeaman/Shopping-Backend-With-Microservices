package com.amazon.InventoryService.dto;


import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemRequestDTO {
 @NotBlank
 private String productCode;

 @NotBlank
 private String name;

 @NotNull
 @Min(0)
 private Integer quantity;

 @NotNull
 @Min(0)
 private Double price;
}