package com.cloud.jml.exception;

public class ProductoDuplicadoException extends RuntimeException {

    public ProductoDuplicadoException(String identificacion) {
        super("El cliente con identificación " + identificacion + " ya existe.");
    }
}
