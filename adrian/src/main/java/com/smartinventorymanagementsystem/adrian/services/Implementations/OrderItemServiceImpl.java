package com.smartinventorymanagementsystem.adrian.services.Implementations;

import com.smartinventorymanagementsystem.adrian.dtos.OrderItemDTO;
import com.smartinventorymanagementsystem.adrian.dtos.ProductDTO;
import com.smartinventorymanagementsystem.adrian.mappers.OrderItemMapper;
import com.smartinventorymanagementsystem.adrian.models.Category;
import com.smartinventorymanagementsystem.adrian.models.OrderItem;
import com.smartinventorymanagementsystem.adrian.models.Product;
import com.smartinventorymanagementsystem.adrian.repositories.OrderItemRepository;
import com.smartinventorymanagementsystem.adrian.repositories.ProductRepository;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.OrderItemService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private static final Logger logger = LoggerFactory.getLogger(OrderItemServiceImpl.class);
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final ProductRepository productRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper, ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderItemDTO> getAllOrderItems() {
        logger.info("Fetching all order items");
        List<OrderItem> orderItems = orderItemRepository.findAll();
        logger.debug("Fetched {} order items", orderItems.size());
        return orderItems.stream()
                .map(orderItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "OrderItemCache", unless = "#result == null")
    public OrderItemDTO getOrderItemById(Long id) {
        logger.info("Fetching order item by id: {}", id);
        return orderItemMapper.toDTO(fetchOrderItemById(id));
    }

    @Override
    public OrderItemDTO saveOrderItem(OrderItemDTO orderItemDTO) {
        logger.info("Saving order item {}", orderItemDTO);
        OrderItem orderItem = orderItemMapper.toOrderItem(orderItemDTO);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        logger.debug("Order item saved with id: {}", savedOrderItem.getId());
        return orderItemMapper.toDTO(savedOrderItem);
    }

    @Override
    public OrderItemDTO updateOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = fetchOrderItemById(orderItemDTO.getId());
        OrderItem updatedOrderItem = updateFields(orderItem, orderItemDTO);
        logger.debug("Order item updated with id: {}", updatedOrderItem.getId());
        return orderItemMapper.toDTO(orderItemRepository.save(updatedOrderItem));
    }

    @Override
    public void deleteOrderItem(Long id) {
        logger.info("Deleting order item with id: {}", id);
        if (orderItemRepository.existsById(id)) {
            orderItemRepository.deleteById(id);
            logger.debug("Order item with id: {} deleted", id);
        } else {
            logger.warn("Order item with id: {} not found for deletion", id);
            throw new EntityNotFoundException("Order item with id: " + id + " not found");
        }
    }
    private OrderItem fetchOrderItemById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> {
            logger.warn("Order item with id: {} not found", id);
            return new EntityNotFoundException("Order item with id: " + id + " not found");
        });
    }

    private OrderItem updateFields(OrderItem orderItem, OrderItemDTO orderItemDTO) {
        Product product = productRepository.findById(orderItemDTO.getProductId())
                .orElseThrow(() -> {
                    logger.warn("Could not find product id: {}", orderItemDTO.getProductId());
                    throw new EntityNotFoundException("Product with id: " + orderItemDTO.getProductId() + " not found");
                });
        orderItem.setProduct(product);
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setTotalPrice(calculateTotalPrice(product.getPrice(), orderItemDTO.getQuantity()));
        return orderItem;
    }

    private BigDecimal calculateTotalPrice(BigDecimal price, int quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

}
