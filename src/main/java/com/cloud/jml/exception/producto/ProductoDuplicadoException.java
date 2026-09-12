package com.cloud.jml.exception.producto;

import org.springframework.http.HttpStatus;

public class ProductoDuplicadoException extends ProductoRuntimeException {

    public ProductoDuplicadoException(String codigo) {
        super(
                HttpStatus.CONFLICT,
                "⚠️ [DUPLICADO] Producto duplicado detectado con codigo: " + codigo);
    }
}
