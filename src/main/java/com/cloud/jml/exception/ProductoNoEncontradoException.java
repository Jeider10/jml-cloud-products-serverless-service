package com.cloud.jml.exception;

public class ProductoNoEncontradoException extends RuntimeException {

    public ProductoNoEncontradoException(Long codigo) {
        super("No se encontró producto con código: " + codigo);
    }
}
