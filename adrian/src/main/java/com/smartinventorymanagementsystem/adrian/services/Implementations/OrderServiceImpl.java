package com.smartinventorymanagementsystem.adrian.services.Implementations;

import com.smartinventorymanagementsystem.adrian.dtos.OrderAndOrderStatusDTO;
import com.smartinventorymanagementsystem.adrian.dtos.OrderDTO;
import com.smartinventorymanagementsystem.adrian.mappers.OrderMapper;
import com.smartinventorymanagementsystem.adrian.models.*;
import com.smartinventorymanagementsystem.adrian.repositories.*;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final DeliveryMethodRepository deliveryMethodRepository;
    private final CustomerRepository customerRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderStatusRepository orderStatusRepository,
                            OrderHistoryRepository orderHistoryRepository, DeliveryMethodRepository deliveryMethodRepository,
                            CustomerRepository customerRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.orderHistoryRepository = orderHistoryRepository;
        this.deliveryMethodRepository = deliveryMethodRepository;
        this.customerRepository = customerRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        logger.info("Fetching all orders");
        List<Order> orders = orderRepository.findAll();
        logger.debug("Fetched {} orders", orders);
        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderAndOrderStatusDTO> getAllOrdersWithStatus() {
        logger.info("Fetching all orders including order status");
        List<Order> orders = orderRepository.findAll();
        logger.debug("Fetched {} orders including order status", orders);
        return orders.stream()
                .map(orderMapper::toOrderAndOrderStatusDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrdersByCustomer(Long customerId) {
        logger.info("Fetching all orders by customer id: {}", customerId);
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        logger.debug("Fetched {} orders by customer id: {}", orders, customerId);
        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderAndOrderStatusDTO> getAllOrdersWithStatusByCustomer(Long customerId) {
        logger.info("Fetching all orders with order status by customer id: {}", customerId);
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        logger.debug("Fetched {} orders with order status by customer id: {}", orders, customerId);
        return orders.stream()
                .map(orderMapper::toOrderAndOrderStatusDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        logger.info("Fetching product by id: ", id);
        return orderMapper.toDTO(fetchOrderById(id));
    }

    @Override
    public OrderAndOrderStatusDTO getOrderWithStatusById(Long id) {
        logger.info("Fetching product by id: ", id);
        return orderMapper.toOrderAndOrderStatusDTO(fetchOrderById(id));
    }

    @Override
    public OrderAndOrderStatusDTO newOrder(OrderDTO orderDTO) {
        Order order = initializeOrder(orderDTO);
        OrderStatus orderStatus = createOrderStatus(order);
        order.setOrderStatus(orderStatus);
        logger.info("Creating new order: {}", order);
        Order savedOrder = orderRepository.save(order);
        logger.debug("Order created: {}", savedOrder);
        createOrderHistory(savedOrder);
        return orderMapper.toOrderAndOrderStatusDTO(savedOrder);
    }

    @Override
    public OrderAndOrderStatusDTO updateOrder(OrderDTO orderDTO) {
        //Todo not decided on how to handle updating of orders
        return null;
    }

    @Override
    public void deleteOrder(Long id) {
        logger.info("Deleting order with id: {}", id);
        if(orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            logger.debug("Order with id: {} deleted", id);
        } else {
            logger.warn("Order with id: {} not found for deletion", id);
            throw new EntityNotFoundException("Order with id: " + id + " not found");
        }
    }

    private Order fetchOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> {
            logger.warn("Order with id {} not found", id);
            throw new EntityNotFoundException("Order with id: " + id + " not fund");
        });

    }

    private Order initializeOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toOrder(orderDTO);
        logger.info("Fetching customer with id: {}", orderDTO.getCustomerId());
        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> {
                   logger.warn("Customer with id: {} could not be found while creating order: {}", orderDTO.getCustomerId(), orderDTO);
                   throw new EntityNotFoundException("Customer with id: " + orderDTO.getCustomerId() + " not found");
                });
        logger.debug("Fetched customer: {}", customer);
        order.setCustomer(customer);
        return order;
    }

    private OrderStatus createOrderStatus(Order order) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrder(order);
        orderStatus.setDeliveryMethod(selectDefaultDeliveryMethod()); //Todo needs to implement deliver method logic
        orderStatus.setExpectedShippingDate(calculateExpectedShippingDate(order));
        orderStatus.setExpectedDeliveryDate(calculateExpectedDeliveryDate(order));
        orderStatus.setStatus("Pending");
        orderStatus.setDelivered(false);

        logger.info("Saving order status: {}", orderStatus);
        OrderStatus savedOrderStatus = orderStatusRepository.save(orderStatus);
        logger.debug("Order status saved: {}", savedOrderStatus);
        return savedOrderStatus;
    }

    private LocalDateTime calculateExpectedShippingDate(Order order) {
        return order.getOrderDate().plusDays(2); //Todo temporary needs to be replaced with actual logic
    }

    private LocalDateTime calculateExpectedDeliveryDate(Order order) {
        return order.getOrderDate().plusDays(7); //Todo temporary needs to be replaced with actual logic
    }

    private DeliveryMethod selectDefaultDeliveryMethod() {
        return deliveryMethodRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("Default delivery method not found"));
    }

    private void createOrderHistory(Order order) {
        OrderHistory orderHistory = orderHistoryRepository.findByCustomerId(order.getCustomer().getId());
        logger.info("Adding order: {} to order history of customer: {}", order, order.getCustomer());
        orderHistoryRepository.save(orderHistory);
        logger.debug("Order history created: {} for customer: {}", orderHistory, order.getCustomer());
    }
}
