package com.cloud.jml.utils;

import com.cloud.jml.dto.ProductoRequestDTO;
import com.cloud.jml.dto.ProductoResponseDTO;
import com.cloud.jml.exception.ProductoNoEncontradoException;
import com.cloud.jml.model.ProductoEntity;
import com.cloud.jml.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Component // 🔹 Anotación para indicar que es un componente de Spring
public class ProductoUtils {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("d/M/yyyy, h:mm:ss a", Locale.of("es", "CO"));

    private final ProductoRepository productoRepository;

    public ProductoUtils(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
        log.info("🔥 ProductoUtils inicializado correctamente.");
    }

    public ProductoEntity validarExistenciaProducto(ProductoRequestDTO productoRequestDTO) {
        Optional<ProductoEntity> optionalProveedor = productoRepository.findByCodigo(productoRequestDTO.getCodigo());

        if (optionalProveedor.isPresent()) {
            log.info("📌 Proveedor encontrado con Codigo de Sucursal: {}", productoRequestDTO.getCodigo());
            return optionalProveedor.get();
        } else {
            log.warn("⚠️ Proveedor no encontrado con Codigo de Sucursal: {}", productoRequestDTO.getCodigo());
            throw new ProductoNoEncontradoException(productoRequestDTO.getCodigo());
        }
    }

    public void actualizarDatosProducto(ProductoRequestDTO productoRequestDTO, ProductoEntity productoEntity) {

        productoEntity.setNombre(productoRequestDTO.getNombre());
        productoEntity.setDescripcion(productoRequestDTO.getDescripcion());
        productoEntity.setCantidad(productoRequestDTO.getCantidad());
        productoEntity.setPrecio(productoRequestDTO.getPrecio());
        productoEntity.setProveedorId(productoRequestDTO.getProveedorId());
        productoEntity.setProveedorName(productoRequestDTO.getProveedorName());

        productoEntity.setFechaActualizacion(LocalDateTime.now());
    }

    public String formatearFecha(LocalDateTime fecha) {
        String fechaFormateada = fecha.format(FORMATTER).toLowerCase();
        log.info("📌 Fecha formateada originalmente: {}", fechaFormateada);

        // Reemplazar y reasignar el valor "a. m." → "a.m." y "p. m." → "p.m."
        fechaFormateada = fechaFormateada
                .replace("a. m.", "a.m.")
                .replace("p. m.", "p.m.");

        log.info("📌 Fecha formateada final: {}", fechaFormateada);

        return fechaFormateada;
    }

    public void asignarFechasFormateadas(ProductoEntity productoEntity, ProductoResponseDTO productoResponseDTO) {
        if (productoEntity.getFechaCreacion() != null) {
            String fechaCreacion = formatearFecha(productoEntity.getFechaCreacion());
            log.info("📌 Fecha creación formateada: {}", fechaCreacion);

            productoResponseDTO.setFechaCreacion(fechaCreacion);
        } else {
            productoResponseDTO.setFechaCreacion(null);
        }

        if (productoEntity.getFechaActualizacion() != null) {
            String fechaActualizacion = formatearFecha(productoEntity.getFechaActualizacion());
            log.info("📌 Fecha actualización formateada: {}", fechaActualizacion);

            productoResponseDTO.setFechaActualizacion(fechaActualizacion);
        } else {
            productoResponseDTO.setFechaActualizacion(null);
        }
    }
}
