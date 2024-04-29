package com.smartinventorymanagementsystem.adrian.urlvalidation;

import com.smartinventorymanagementsystem.adrian.util.ImageUrlValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ImageUrlValidationTests {

    private ImageUrlValidator imageUrlValidator;

    @Autowired
    public ImageUrlValidationTests(ImageUrlValidator imageUrlValidator) {
        this.imageUrlValidator = imageUrlValidator;
    }

    @Test
    void validateImageUrls() {
        assertEquals(true, imageUrlValidator.isValidImageUrl("https://example.com/path/to/image.jpg"));
        assertEquals(true, imageUrlValidator.isValidImageUrl("https://example.com/anotherpath/toanother/image.PNG"));
        assertEquals(false, imageUrlValidator.isValidImageUrl("https://example.com/wrongpath/image.txt"));
        assertEquals(false, imageUrlValidator.isValidImageUrl("https:/example.com/image.jpg"));
        assertEquals(false, imageUrlValidator.isValidImageUrl("http:/example.com/image.jpg"));
    }
}
