package com.amazon.InventoryService.service.implementation;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.amazon.InventoryService.dto.InventoryItemRequestDTO;
import com.amazon.InventoryService.dto.InventoryItemResponseDTO;
import com.amazon.InventoryService.entity.InventoryItem;
import com.amazon.InventoryService.exception.DuplicateResourceException;
import com.amazon.InventoryService.exception.ResourceNotFoundException;
import com.amazon.InventoryService.repository.InventoryItemRepository;
import com.amazon.InventoryService.service.InventoryItemService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryItemServiceImpl implements InventoryItemService {

 private final InventoryItemRepository itemRepository;
 private final ModelMapper modelMapper;

 @Override
 public InventoryItemResponseDTO createItem(InventoryItemRequestDTO dto) {
     if (itemRepository.existsByProductCode(dto.getProductCode())) {
         throw new DuplicateResourceException("Product with code " + dto.getProductCode() + " already exists");
     }
     InventoryItem item = modelMapper.map(dto, InventoryItem.class);
     InventoryItem saved = itemRepository.save(item);
     return modelMapper.map(saved, InventoryItemResponseDTO.class);
 }

 @Override
 public InventoryItemResponseDTO getItemById(Long id) {
     InventoryItem item = itemRepository.findById(id)
             .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found"));
     return modelMapper.map(item, InventoryItemResponseDTO.class);
 }

 @Override
 public List<InventoryItemResponseDTO> getAllItems() {
     return itemRepository.findAll()
             .stream()
             .map(item -> modelMapper.map(item, InventoryItemResponseDTO.class))
             .toList();
 }

 @Override
 public InventoryItemResponseDTO updateItem(Long id, InventoryItemRequestDTO dto) {
     InventoryItem item = itemRepository.findById(id)
             .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found"));

     // Check for duplicate product code *only if* the code is being changed
     if (!item.getProductCode().equals(dto.getProductCode()) && itemRepository.existsByProductCode(dto.getProductCode())) {
         throw new DuplicateResourceException("Product with code " + dto.getProductCode() + " already exists");
     }

     modelMapper.map(dto, item);
     InventoryItem updated = itemRepository.save(item);
     return modelMapper.map(updated, InventoryItemResponseDTO.class);
 }

 @Override
 public void deleteItem(Long id) {
     // Optional: Check if exists before deleting to throw ResourceNotFoundException
     if (!itemRepository.existsById(id)) {
          throw new ResourceNotFoundException("Inventory item not found");
     }
     itemRepository.deleteById(id);
 }
}