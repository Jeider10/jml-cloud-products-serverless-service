package com.cloud.jml.utils.producto;

import com.cloud.jml.dto.ProductoResponseDTO;
import com.cloud.jml.model.ProductoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
@Component // 🔹 Anotación para indicar que es un componente de Spring
public class ProductoFormatearFecha {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("d/M/yyyy, h:mm:ss a", Locale.of("es", "CO"));

    public ProductoFormatearFecha() {
        log.info("🔥 ProductoFormatearFecha inicializado correctamente.");
    }

    public void asignarFechasFormateadas(ProductoEntity productoEntity, ProductoResponseDTO productoResponseDTO) {
        if (productoEntity == null || productoResponseDTO == null) {
            log.warn("⚠️ Entidad o DTO nulos al intentar asignar fechas formateadas.");
            return;
        }

        log.info("📦 Asignando fechas formateadas al producto: {}", productoEntity.getNombre());

        // Fecha de creación
        String fechaCreacion = formatearFecha(productoEntity.getFechaCreacion());
        productoResponseDTO.setFechaCreacion(fechaCreacion);
        log.debug("🕓 Fecha de creación asignada: {}", fechaCreacion);

        // Fecha de actualización
        String fechaActualizacion = formatearFecha(productoEntity.getFechaActualizacion());
        productoResponseDTO.setFechaActualizacion(fechaActualizacion);
        log.debug("🕓 Fecha de actualización asignada: {}", fechaActualizacion);
    }

    /**
     * 🕒 Formatea una fecha LocalDateTime al formato colombiano:
     * Ejemplo → 18/10/2025, 2:35:45 p.m.
     */
    public String formatearFecha(LocalDateTime fecha) {
        if (fecha == null) {
            log.warn("⚠️ Fecha recibida nula, se retorna null.");
            return null;
        }

        String fechaFormateada = fecha.format(FORMATTER).toLowerCase();
        log.info("🕓 Formateando fecha: {}", fechaFormateada);

        // Reemplazar expresiones locales de AM/PM con formato limpio
        fechaFormateada = fechaFormateada
                .replace("a. m.", "a.m.")
                .replace("p. m.", "p.m.");

        log.info("🕓 Fecha formateada correctamente: {}", fechaFormateada);

        return fechaFormateada;
    }
}
