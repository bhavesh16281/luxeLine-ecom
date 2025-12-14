package com.bhavesh16281.ecommerce.luxeLine_ecom.exceptions;

public class APIException extends RuntimeException {

    private static final long serialVersionUid = 1L;

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }
}
