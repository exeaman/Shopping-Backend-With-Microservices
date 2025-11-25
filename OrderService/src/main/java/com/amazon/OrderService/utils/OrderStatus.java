package com.amazon.OrderService.utils;

public enum OrderStatus {
    PENDING,        // Initial state, waiting for inventory check/payment
    CONFIRMED,      // Inventory reserved and/or payment successful
    SHIPPED,        // Order has left the warehouse
    DELIVERED,      // Order successfully received by the customer
    CANCELLED,      // Order cancelled by user or system
    FAILED          // Failed due to payment or inventory issues
}