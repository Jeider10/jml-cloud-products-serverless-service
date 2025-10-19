package com.cloud.jml.exception.producto;

import org.springframework.http.HttpStatus;

public class ProductoNoEncontradoException extends ProductoRuntimeException {

    public ProductoNoEncontradoException(Long codigo) {
        super(
                HttpStatus.NOT_FOUND,
                "❌ [CONSULTA] Producto no encontrado con código: " + codigo);
    }
}
