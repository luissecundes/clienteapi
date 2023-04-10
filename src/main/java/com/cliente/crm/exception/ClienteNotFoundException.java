package com.cliente.crm.exception;

public class ClienteNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ClienteNotFoundException(String message) {
        super(message);
    }

    public ClienteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
