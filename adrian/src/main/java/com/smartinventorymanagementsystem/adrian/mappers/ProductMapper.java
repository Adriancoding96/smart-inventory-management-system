package com.smartinventorymanagementsystem.adrian.mappers;

import com.smartinventorymanagementsystem.adrian.dtos.ProductDTO;
import com.smartinventorymanagementsystem.adrian.dtos.ProductWithImagesDTO;
import com.smartinventorymanagementsystem.adrian.models.Product;

import org.hibernate.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductMapper {

    private ModelMapper modelMapper;

    @Autowired
    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductDTO toDTO(Product product) {
        try {
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            return productDTO;
        } catch (Exception exception) {
            throw new MappingException("Unable to map Product to ProductDTO", exception);
        }
    }

    public ProductWithImagesDTO toFullDTO(Product product) {
        try {
            ProductWithImagesDTO productWithImagesDTO = modelMapper.map(product, ProductWithImagesDTO.class);
            return productWithImagesDTO;
        } catch (Exception exception) {
            throw new MappingException("Unable to map Producto to ProductWithImagesDTO", exception);
        }
    }

    public Product toProduct(ProductDTO productDTO) {
        try {
            Product product = modelMapper.map(productDTO, Product.class);
            return product;
        } catch (Exception exception) {
            throw new MappingException("Unable to map ProductDTO to Product", exception);
        }
    }

}
