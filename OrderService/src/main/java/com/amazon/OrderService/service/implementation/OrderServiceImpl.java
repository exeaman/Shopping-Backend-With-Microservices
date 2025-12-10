
package com.amazon.OrderService.service.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazon.OrderService.client.InventoryServiceFeignClient;
import com.amazon.OrderService.client.UserServiceFeignClient;
import com.amazon.OrderService.dto.InventoryReservationRequest;
import com.amazon.OrderService.dto.OrderRequestDTO;
import com.amazon.OrderService.dto.OrderLineItemDTO;
import com.amazon.OrderService.dto.OrderResponseDTO;
import com.amazon.OrderService.dto.UserResponseDTO;
import com.amazon.OrderService.entity.Order;
import com.amazon.OrderService.entity.OrderLineItem;
import com.amazon.OrderService.exception.BadRequestException;
import com.amazon.OrderService.exception.ConflictException;
import com.amazon.OrderService.exception.NotFoundException;
import com.amazon.OrderService.exception.UpstreamException;
import com.amazon.OrderService.repository.OrderRepository;
import com.amazon.OrderService.service.OrderService;
import com.amazon.OrderService.utils.OrderStatus;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserServiceFeignClient userClient;
    private final InventoryServiceFeignClient inventoryClient;

    @Transactional
    @Override
    public OrderResponseDTO placeOrder(OrderRequestDTO request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BadRequestException("Order must contain at least one item");
        }

        UserResponseDTO user = userClient.getUserById(request.getUserId());
        if (user == null) {
            throw new NotFoundException("User not found: " + request.getUserId());
        }

        // Reserve inventory
        List<InventoryReservationRequest> reservations = request.getItems()
                .stream()
                .map(i -> new InventoryReservationRequest(i.getInventoryItemId(), i.getQuantity()))
                .collect(Collectors.toList());

        try {
            inventoryClient.reserveItems(reservations);
        } catch (FeignException.NotFound e) {
            throw new ConflictException("One or more items are out of stock.");
        } catch (FeignException e) {
            throw new UpstreamException("Inventory reservation failed", e);
        }

        double total = request.getItems()
                .stream()
                .mapToDouble(i -> i.getUnitPrice() * i.getQuantity())
                .sum();

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setShippingAddress(user.getShippingAddress());
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.CONFIRMED);

        List<OrderLineItem> lineItems = request.getItems().stream()
                .map(i -> {
                    OrderLineItem li = new OrderLineItem();
                    li.setInventoryItemId(i.getInventoryItemId());
                    li.setUnitPrice(i.getUnitPrice());
                    li.setQuantity(i.getQuantity());
                    li.setOrder(order);
                    return li;
                })
                .collect(Collectors.toList());

        order.setItems(lineItems);
        Order saved = orderRepository.save(order);

        return toResponseDTO(saved);
    }

    @Transactional
    @Override
    public OrderResponseDTO getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found: " + orderId));
        return toResponseDTO(order);
    }

    @Transactional
    @Override
    public List<OrderResponseDTO> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found: " + orderId));
        if (order.getStatus() == OrderStatus.CANCELLED || order.getStatus() == OrderStatus.FAILED) {
            return;
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    private OrderResponseDTO toResponseDTO(Order order) {
        List<OrderLineItemDTO> items =
            (order.getItems() == null ? List.<OrderLineItemDTO>of() :
             order.getItems().stream().map(li -> {
                 OrderLineItemDTO dto = new OrderLineItemDTO();
                 dto.setInventoryItemId(li.getInventoryItemId());
                 dto.setQuantity(li.getQuantity());
                 dto.setUnitPrice(li.getUnitPrice());
                 return dto;
             }).collect(Collectors.toList()));

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setItems(items);
        return dto;
    }
}
