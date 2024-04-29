package com.smartinventorymanagementsystem.adrian.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ImageUrlValidator {
    private static final Pattern IMAGE_URL_PATTERN = Pattern.compile(
            "https://(?:[\\w-]+\\.)+[\\w-]+(?:/[\\w-./?%&=]*)?\\.(jpg|jpeg|png|gif)$", Pattern.CASE_INSENSITIVE);

    public boolean isValidImageUrl(String urlString) {
        return IMAGE_URL_PATTERN.matcher(urlString).matches();
    }
}
