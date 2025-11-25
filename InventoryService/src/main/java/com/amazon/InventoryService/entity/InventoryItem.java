package com.amazon.InventoryService.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(unique = true, nullable = false)
 private String productCode;

 @Column(nullable = false)
 private String name;

 @Column(nullable = false)
 private Integer quantity;

 @Column(nullable = false)
 private Double price;
}