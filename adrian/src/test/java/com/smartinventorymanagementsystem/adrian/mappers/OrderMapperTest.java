package com.smartinventorymanagementsystem.adrian.mappers;

import com.smartinventorymanagementsystem.adrian.dtos.OrderAndOrderStatusDTO;
import com.smartinventorymanagementsystem.adrian.dtos.OrderDTO;
import com.smartinventorymanagementsystem.adrian.dtos.OrderStatusDTO;
import com.smartinventorymanagementsystem.adrian.models.Order;
import com.smartinventorymanagementsystem.adrian.models.OrderStatus;
import org.hibernate.MappingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderMapperTest {

    private ModelMapper modelMapper;
    private OrderMapper orderMapper;

    @BeforeEach
    public void setUp() {
        modelMapper = mock(ModelMapper.class);
        orderMapper = new OrderMapper(modelMapper);
    }

    @Test
    public void testToDTO() {
        Order order = new Order();
        OrderDTO orderDTO = new OrderDTO();

        when(modelMapper.map(order, OrderDTO.class)).thenReturn(orderDTO);

        OrderDTO result = orderMapper.toDTO(order);

        assertNotNull(result);
        assertEquals(orderDTO, result);
        verify(modelMapper).map(order, OrderDTO.class);
    }

    @Test
    public void testToOrderAndOrderStatusDTO() {
        Order order = new Order();
        OrderStatus orderStatus = new OrderStatus();
        order.setOrderStatus(orderStatus);

        OrderAndOrderStatusDTO orderAndOrderStatusDTO = new OrderAndOrderStatusDTO();
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();

        when(modelMapper.map(order, OrderAndOrderStatusDTO.class)).thenReturn(orderAndOrderStatusDTO);
        when(modelMapper.map(orderStatus, OrderStatusDTO.class)).thenReturn(orderStatusDTO);

        OrderAndOrderStatusDTO result = orderMapper.toOrderAndOrderStatusDTO(order);

        assertNotNull(result);
        assertEquals(orderAndOrderStatusDTO, result);
        assertEquals(orderStatusDTO, result.getOrderStatusDTO());
        verify(modelMapper).map(order, OrderAndOrderStatusDTO.class);
        verify(modelMapper).map(orderStatus, OrderStatusDTO.class);
    }

    @Test
    public void testToOrderFromOrderAndOrderStatusDTO() {
        OrderAndOrderStatusDTO orderAndOrderStatusDTO = new OrderAndOrderStatusDTO();
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
        orderAndOrderStatusDTO.setOrderStatusDTO(orderStatusDTO);

        Order order = new Order();
        OrderStatus orderStatus = new OrderStatus();

        when(modelMapper.map(orderAndOrderStatusDTO, Order.class)).thenReturn(order);
        when(modelMapper.map(orderStatusDTO, OrderStatus.class)).thenReturn(orderStatus);

        Order result = orderMapper.toOrder(orderAndOrderStatusDTO);

        assertNotNull(result);
        assertEquals(order, result);
        assertEquals(orderStatus, result.getOrderStatus());
        verify(modelMapper).map(orderAndOrderStatusDTO, Order.class);
        verify(modelMapper).map(orderStatusDTO, OrderStatus.class);
    }

    @Test
    public void testToOrderFromOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        Order order = new Order();

        when(modelMapper.map(orderDTO, Order.class)).thenReturn(order);

        Order result = orderMapper.toOrder(orderDTO);

        assertNotNull(result);
        assertEquals(order, result);
        verify(modelMapper).map(orderDTO, Order.class);
    }

    @Test
    public void testToDTOThrowsMappingException() {
        Order order = new Order();

        when(modelMapper.map(order, OrderDTO.class)).thenThrow(new RuntimeException());

        assertThrows(MappingException.class, () -> orderMapper.toDTO(order));
        verify(modelMapper).map(order, OrderDTO.class);
    }

    @Test
    public void testToOrderAndOrderStatusDTOThrowsMappingException() {
        Order order = new Order();

        when(modelMapper.map(order, OrderAndOrderStatusDTO.class)).thenThrow(new RuntimeException());

        assertThrows(MappingException.class, () -> orderMapper.toOrderAndOrderStatusDTO(order));
        verify(modelMapper).map(order, OrderAndOrderStatusDTO.class);
    }

    @Test
    public void testToOrderFromOrderAndOrderStatusDTOThrowsMappingException() {
        OrderAndOrderStatusDTO orderAndOrderStatusDTO = new OrderAndOrderStatusDTO();

        when(modelMapper.map(orderAndOrderStatusDTO, Order.class)).thenThrow(new RuntimeException());

        assertThrows(MappingException.class, () -> orderMapper.toOrder(orderAndOrderStatusDTO));
        verify(modelMapper).map(orderAndOrderStatusDTO, Order.class);
    }

    @Test
    public void testToOrderFromOrderDTOThrowsMappingException() {
        OrderDTO orderDTO = new OrderDTO();

        when(modelMapper.map(orderDTO, Order.class)).thenThrow(new RuntimeException());

        assertThrows(MappingException.class, () -> orderMapper.toOrder(orderDTO));
        verify(modelMapper).map(orderDTO, Order.class);
    }
}

