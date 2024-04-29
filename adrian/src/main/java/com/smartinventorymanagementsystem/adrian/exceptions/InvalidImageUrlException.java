package com.smartinventorymanagementsystem.adrian.exceptions;

public class InvalidImageUrlException extends RuntimeException {

    public InvalidImageUrlException() {
        super("Image URL not valid");
    }

    public InvalidImageUrlException(String customMessage) {
        super(customMessage);
    }
}
