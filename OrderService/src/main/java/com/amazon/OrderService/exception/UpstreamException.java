package com.amazon.OrderService.exception;


public class UpstreamException extends RuntimeException {
    public UpstreamException(String m, Throwable t) { super(m, t); }
}
