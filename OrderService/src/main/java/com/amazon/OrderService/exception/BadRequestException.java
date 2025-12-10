package com.amazon.OrderService.exception;


public class BadRequestException extends RuntimeException {
    public BadRequestException(String m) { super(m); }
}
