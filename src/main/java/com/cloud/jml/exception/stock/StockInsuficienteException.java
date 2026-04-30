package com.cloud.jml.exception.stock;

import org.springframework.http.HttpStatus;

public class StockInsuficienteException extends StockRuntimeException {

    public StockInsuficienteException(int cantidad) {
        super(
                HttpStatus.CONFLICT,
                "❌ [STOCK] Stock insuficiente. Cantidad solicitada: " + cantidad
        );
    }
}
