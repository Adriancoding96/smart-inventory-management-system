package com.smartinventorymanagementsystem.adrian.mappers;

import com.smartinventorymanagementsystem.adrian.dtos.ProductImageDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.smartinventorymanagementsystem.adrian.models.ProductImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ProductImageMapperTest {

    private ProductImageMapper productImageMapper;

    @Autowired
    public ProductImageMapperTest(ProductImageMapper productImageMapper) {
        this.productImageMapper = productImageMapper;
    }

    @Test
    void convertProductImageEntityToDTO() {
        ProductImage productImage = new ProductImage();
        productImage.setId(1L);
        productImage.setImageURL("this/is/a/url");

        ProductImageDTO productImageDTO = productImageMapper.toDTO(productImage);
        assertEquals(productImageDTO.getId(), productImage.getId());
        assertEquals(productImageDTO.getId(), productImage.getId());
    }

    @Test
    void convertProductImageDTOToProduct() {
        ProductImageDTO productImageDTO = new ProductImageDTO();
        productImageDTO.setId(1L);
        productImageDTO.setImageURL("this/is/a/url");

        ProductImage productImage = productImageMapper.toProductImage(productImageDTO);
        assertEquals(productImageDTO.getId(), productImage.getId());
        assertEquals(productImageDTO.getImageURL(), productImage.getImageURL());

    }
}
