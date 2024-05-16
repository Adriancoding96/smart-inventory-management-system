package com.smartinventorymanagementsystem.adrian.mappers;

import com.smartinventorymanagementsystem.adrian.dtos.OrderItemDTO;
import com.smartinventorymanagementsystem.adrian.dtos.ProductDTO;
import com.smartinventorymanagementsystem.adrian.dtos.ProductWithImagesDTO;
import com.smartinventorymanagementsystem.adrian.models.OrderItem;
import com.smartinventorymanagementsystem.adrian.models.Product;

import org.hibernate.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderItemMapper {

    private ModelMapper modelMapper;

    @Autowired
    public OrderItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderItemDTO toDTO(OrderItem orderItem) {
        try {
            OrderItemDTO orderItemDTO = modelMapper.map(orderItem, OrderItemDTO.class);
            return orderItemDTO;
        } catch (Exception exception) {
            throw new MappingException("Unable to map OrderItem to OrderItemDTO", exception);
        }
    }


    public OrderItem toOrderItem(OrderItemDTO orderItemDTO) {
        try {
            OrderItem orderItem = modelMapper.map(orderItemDTO, OrderItem.class);
            return orderItem;
        } catch (Exception exception) {
            throw new MappingException("Unable to map OrderItemDTO to OrderItem", exception);
        }
    }

}
