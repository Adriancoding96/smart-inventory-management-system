package com.smartinventorymanagementsystem.adrian.exceptions;

public class InsufficientStockException extends RuntimeException{

    public InsufficientStockException() {
        super("Insufficient stock available");
    }

    public InsufficientStockException(String customMessage) {
        super(customMessage);
    }
}
