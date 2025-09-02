package com.cloud.jml.exception;

public class ProductoNoEncontradoException extends RuntimeException {

    public ProductoNoEncontradoException(String identificacion) {
        super("No se encontró cliente con identificación: " + identificacion);
    }
}
