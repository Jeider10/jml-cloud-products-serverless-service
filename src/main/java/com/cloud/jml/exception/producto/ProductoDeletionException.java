package com.cloud.jml.exception.producto;

import org.springframework.http.HttpStatus;

public class ProductoDeletionException extends ProductoRuntimeException {

    public ProductoDeletionException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "🗑️ [ELIMINACIÓN] " + message);
    }

    public ProductoDeletionException(String message, Throwable cause) {
        super(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "🗑️ [ELIMINACIÓN] " + message +
                        (cause != null ? " | 💥 Causa: " + cause.getMessage() : "")
        );
    }

    // 🔒 Violación de integridad referencial (por constraints o dependencias)
    public static ProductoDeletionException integrityViolation(Throwable cause) {
        return new ProductoDeletionException(
                "❌ [INTEGRIDAD] No se pudo eliminar el producto debido a una violación de integridad referencial",
                cause
        );
    }

    // ⚙️ Error de acceso a datos
    public static ProductoDeletionException dataAccessError(Throwable cause) {
        return new ProductoDeletionException(
                "❌ [DATOS] Error de acceso a la base de datos al intentar eliminar el producto",
                cause
        );
    }

    // 💥 Error inesperado
    public static ProductoDeletionException unexpected(Throwable cause) {
        return new ProductoDeletionException(
                "💥 [INESPERADO] Ocurrió un error inesperado al intentar eliminar el producto",
                cause
        );
    }
}
