package com.smartinventorymanagementsystem.adrian.mappers;

import com.smartinventorymanagementsystem.adrian.dtos.ProductDTO;
import com.smartinventorymanagementsystem.adrian.dtos.ProductWithImagesDTO;
import com.smartinventorymanagementsystem.adrian.models.Category;
import com.smartinventorymanagementsystem.adrian.models.Product;
import com.smartinventorymanagementsystem.adrian.models.ProductImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProductMapperTest {

    private ProductMapper productMapper;

    @Autowired
    public ProductMapperTest(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Test
    void convertProductEntityToDTO() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Keyboard1");
        product.setDescription("Description");
        product.setPrice(new BigDecimal(80.0));
        product.setStockQuantity(10);

        Category category = new Category();
        category.setId(1L);
        category.setName("Keyboards");
        category.setDescription("Description");

        product.setCategory(category);

        ProductDTO productDTO = productMapper.toDTO(product);
        assertEquals(productDTO.getId(), product.getId());
        assertEquals(productDTO.getName(), product.getName());
        assertEquals(productDTO.getDescription(), product.getDescription());
        assertEquals(productDTO.getPrice(), product.getPrice());
        assertEquals(productDTO.getStockQuantity(), product.getStockQuantity());
        assertEquals(productDTO.getCategoryId(), product.getCategory().getId());
    }

    @Test
    void convertDTOToProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Keyboard");
        productDTO.setDescription("Description");
        productDTO.setPrice(new BigDecimal(80.0));
        productDTO.setStockQuantity(10);
        productDTO.setCategoryId(1L);

        Product product = productMapper.toProduct(productDTO);
        assertEquals(productDTO.getId(), product.getId());
        assertEquals(productDTO.getName(), product.getName());
        assertEquals(productDTO.getDescription(), product.getDescription());
        assertEquals(productDTO.getPrice(), product.getPrice());
        assertEquals(productDTO.getStockQuantity(), product.getStockQuantity());
    }

    @Test
    void convertProductToFullDTO() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Keyboard");
        product.setDescription("This is a keyboard");
        product.setPrice(new BigDecimal(80.0));
        product.setStockQuantity(10);

        ProductImage productImage1 = new ProductImage();
        productImage1.setId(1L);
        productImage1.setImageURL("/test1/test1");
        productImage1.setProduct(product);

        ProductImage productImage2 = new ProductImage();
        productImage2.setId(2L);
        productImage2.setImageURL("/test2/test2");
        productImage2.setProduct(product);

        List<ProductImage> images = new ArrayList<>();
        images.add(productImage1);
        images.add(productImage2);

        product.setImages(images);

        ProductWithImagesDTO productDTO = productMapper.toFullDTO(product);
        assertEquals(productDTO.getId(), product.getId());
        assertEquals(productDTO.getName(), product.getName());
        assertEquals(productDTO.getDescription(), product.getDescription());
        assertEquals(productDTO.getPrice(), product.getPrice());
        assertEquals(productDTO.getStockQuantity(), product.getStockQuantity());
        assertEquals(product.getImages().size(), productDTO.getImages().size());
        for(int i = 0; i < product.getImages().size(); i++) {
            assertEquals(product.getImages().get(i).getId(), productDTO.getImages().get(i).getId());
            assertEquals(product.getImages().get(i).getImageURL(), productDTO.getImages().get(i).getImageURL());
        }
    }

}
