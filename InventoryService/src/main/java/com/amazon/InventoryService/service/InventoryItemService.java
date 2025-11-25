package com.amazon.InventoryService.service;

import java.util.List;

import com.amazon.InventoryService.dto.InventoryItemRequestDTO;
import com.amazon.InventoryService.dto.InventoryItemResponseDTO;

public interface InventoryItemService {
    InventoryItemResponseDTO createItem(InventoryItemRequestDTO itemRequestDTO);
    InventoryItemResponseDTO getItemById(Long id);
    List<InventoryItemResponseDTO> getAllItems();
    InventoryItemResponseDTO updateItem(Long id, InventoryItemRequestDTO itemRequestDTO);
    void deleteItem(Long id);
}