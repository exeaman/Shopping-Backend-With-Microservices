package com.amazon.OrderService.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.amazon.OrderService.dto.InventoryReservationRequest;

/* 
@FeignClient(name = "inventory-service", url = "${application.config.inventory-service-url}")
public interface InventoryServiceFeignClient {

    @PostMapping("/api/inventory/reserve")
        // Assumes an Inventory Service endpoint that reserves items.
        // Throws a 404 (or similar) if items are out of stock.
    void reserveItems(@RequestBody List<InventoryReservationRequest> request);
}

*/



// InventoryServiceFeignClient.java
@FeignClient(name = "INVENTORYSERVICE")
public interface InventoryServiceFeignClient {
    @PostMapping("/api/inventory/reserve")
    void reserveItems(@RequestBody List<InventoryReservationRequest> request);

    // Keep only if Inventory service supports it
    @PostMapping("/api/inventory/release")
    void releaseItems(@RequestBody List<InventoryReservationRequest> request);
}
