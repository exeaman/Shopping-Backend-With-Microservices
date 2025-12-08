package com.amazon.InventoryService.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.amazon.InventoryService.dto.InventoryItemRequestDTO;
import com.amazon.InventoryService.dto.InventoryItemResponseDTO;
import com.amazon.InventoryService.service.InventoryItemService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryItemController {
	
 private final InventoryItemService itemService;

 @PostMapping
 public ResponseEntity<InventoryItemResponseDTO> createItem(@Valid @RequestBody InventoryItemRequestDTO dto) {
     return ResponseEntity.ok(itemService.createItem(dto));
 }

 @GetMapping("/{id}")
 public ResponseEntity<InventoryItemResponseDTO> getItem(@PathVariable Long id) {
     return ResponseEntity.ok(itemService.getItemById(id));
 }

 @GetMapping
 public ResponseEntity<List<InventoryItemResponseDTO>> getAllItems() {
     return ResponseEntity.ok(itemService.getAllItems());
 }

 @PutMapping("/{id}")
 public ResponseEntity<InventoryItemResponseDTO> updateItem(
         @PathVariable Long id,
         @Valid @RequestBody InventoryItemRequestDTO dto) {
     return ResponseEntity.ok(itemService.updateItem(id, dto));
 }

 @DeleteMapping("/{id}")
 public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
     itemService.deleteItem(id);
     return ResponseEntity.noContent().build();
 }
}