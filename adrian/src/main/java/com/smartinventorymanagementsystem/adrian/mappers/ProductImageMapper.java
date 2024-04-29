package com.smartinventorymanagementsystem.adrian.mappers;

import org.hibernate.MappingException;
import com.smartinventorymanagementsystem.adrian.dtos.ProductImageDTO;
import com.smartinventorymanagementsystem.adrian.models.ProductImage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductImageMapper {

    private ModelMapper modelMapper;

    @Autowired
    public ProductImageMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductImageDTO toDTO(ProductImage productImage) {
        try {
            ProductImageDTO productImageDTO = modelMapper.map(productImage, ProductImageDTO.class);
            return productImageDTO;
        } catch (Exception exception) {
            throw new MappingException("Unable to map ProductImage to ProductImageDTO", exception);
        }
    }

    public ProductImage toProductImage(ProductImageDTO productImageDTO) {
        try {
            ProductImage productImage = modelMapper.map(productImageDTO, ProductImage.class);
            return productImage;
        } catch (Exception exception) {
            throw new MappingException("Unable to map ProductImage to ProductImageDTO", exception);
        }
    }
}
