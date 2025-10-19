package com.cloud.jml.exception.producto;

import org.springframework.http.HttpStatus;

public class ProductoPersistenceException extends ProductoRuntimeException {

    public ProductoPersistenceException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "💾 [PERSISTENCIA] " + message);
    }

    public ProductoPersistenceException(String message, Throwable cause) {
        super(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "💾 [PERSISTENCIA] " + message +
                        (cause != null ? " | 💥 Causa: " + cause.getMessage() : "")
        );
    }

    // 🔒 Violación de integridad (constraint, duplicado, etc.) al guardar
    public static ProductoPersistenceException integrityViolation(Throwable cause) {
        return new ProductoPersistenceException(
                "❌ [INTEGRIDAD] Violación de integridad en base de datos al guardar el producto",
                cause
        );
    }

    // ⚙️ Error técnico de acceso a datos
    public static ProductoPersistenceException dataAccessError(Throwable cause) {
        return new ProductoPersistenceException(
                "❌ [DATOS] Error de acceso a datos al intentar guardar el producto",
                cause
        );
    }

    // 💥 Error inesperado
    public static ProductoPersistenceException unexpected(Throwable cause) {
        return new ProductoPersistenceException(
                "💥 [INESPERADO] Ocurrió un error inesperado al registrar el producto",
                cause
        );
    }
}
