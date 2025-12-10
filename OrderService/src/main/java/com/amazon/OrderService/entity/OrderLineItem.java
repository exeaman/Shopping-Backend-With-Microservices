package com.amazon.OrderService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "order_line_items")
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The ID of the item (from InventoryItemService)
    private Long inventoryItemId; 

    // Snapshot of the price at the time of purchase
    private Double unitPrice; 
    
    private Integer quantity;

    // M:1 relationship back to the Order
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false) // Foreign key to the Order table
    @JsonIgnore
    private Order order; 
}