package com.daisy.daisy_hotel_backend.exception;

public class InvalidCheckinDateException extends RuntimeException {
    public InvalidCheckinDateException(String message) {
        super(message);
    }
}
