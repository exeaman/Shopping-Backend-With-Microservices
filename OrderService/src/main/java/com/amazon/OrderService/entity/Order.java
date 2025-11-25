package com.amazon.OrderService.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.amazon.OrderService.utils.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "orders") // "Order" is a reserved keyword in some SQL dialects, so "orders" is safer
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The ID of the user who placed the order (from UserService)
    private Long userId; 
    
    private LocalDateTime orderDate = LocalDateTime.now();

    // The total amount of the order (can be calculated before saving)
    private Double totalAmount; 
    
    // Address information (can be fetched from UserService but stored here for snapshot)
    private String shippingAddress; 
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING; // Default status upon creation

    // 1:M relationship with OrderLineItem
    // CascadeType.ALL ensures that if an Order is deleted, its items are too.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLineItem> items;
}