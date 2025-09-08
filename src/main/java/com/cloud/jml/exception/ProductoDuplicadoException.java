package com.cloud.jml.exception;

public class ProductoDuplicadoException extends RuntimeException {

    public ProductoDuplicadoException(Long codigo) {
        super("El producto con codigo " + codigo + " ya existe.");
    }
}
