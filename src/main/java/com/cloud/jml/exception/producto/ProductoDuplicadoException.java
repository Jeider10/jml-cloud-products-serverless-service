package com.cloud.jml.exception.producto;

import org.springframework.http.HttpStatus;

public class ProductoDuplicadoException extends ProductoRuntimeException {

    public ProductoDuplicadoException(Long codigo) {
        super(
                HttpStatus.CONFLICT,
                "⚠️ [DUPLICADO] Producto duplicado detectado con código: " + codigo);
    }
}
