package com.smartinventorymanagementsystem.adrian.mappers;

import com.smartinventorymanagementsystem.adrian.dtos.OrderAndOrderStatusDTO;
import com.smartinventorymanagementsystem.adrian.dtos.OrderDTO;
import com.smartinventorymanagementsystem.adrian.dtos.OrderStatusDTO;
import com.smartinventorymanagementsystem.adrian.models.Order;
import com.smartinventorymanagementsystem.adrian.models.OrderStatus;
import org.hibernate.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    private ModelMapper modelMapper;

    @Autowired
    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderDTO toDTO(Order order) {
        try {
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
            return orderDTO;
        } catch (Exception exception) {
            throw new MappingException("Unable to map Order to OrderDTO", exception);
        }
    }

    public OrderAndOrderStatusDTO toOrderAndOrderStatusDTO(Order order) {
        try {
            OrderAndOrderStatusDTO orderAndOrderStatusDTO = modelMapper.map(order, OrderAndOrderStatusDTO.class);
            if(order.getOrderStatus() != null) {
                OrderStatusDTO orderStatusDTO = modelMapper.map(order.getOrderStatus(), OrderStatusDTO.class);
                orderAndOrderStatusDTO.setOrderStatusDTO(orderStatusDTO);
            }
            return orderAndOrderStatusDTO;
        } catch (Exception exception) {
            throw new MappingException("Unable to map Order to OrderAndOrderStatusDTO", exception);
        }
    }

    public Order toOrder(OrderAndOrderStatusDTO orderAndOrderStatusDTO) {
        try {
            Order order = modelMapper.map(orderAndOrderStatusDTO, Order.class);
            if(orderAndOrderStatusDTO.getOrderStatusDTO() != null) {
                OrderStatus orderStatus = modelMapper.map(orderAndOrderStatusDTO.getOrderStatusDTO(), OrderStatus.class);
                order.setOrderStatus(orderStatus);
                orderStatus.setOrder(order);
            }
            return order;
        } catch (Exception exception) {
            throw new MappingException("Unable to map OrderAndStatusDTO to Order", exception);
        }
    }

    public Order toOrder(OrderDTO orderDTO) {
        try {
            Order order = modelMapper.map(orderDTO, Order.class);
            return order;
        } catch (Exception exception) {
            throw new MappingException("Unable to map OrderDTO to Order", exception);
        }

    }
}
