package com.cloud.jml.utils.producto;

import com.cloud.jml.dto.ProductoPapeleraResponseDTO;
import com.cloud.jml.dto.ProductoRequestDTO;
import com.cloud.jml.dto.ProductoResponseDTO;
import com.cloud.jml.model.ProductoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component // 🔹 Anotacion para indicar que es un componente de Spring
public class ProductoMapper {

    private final ProductoFormatearFecha productoFormatearFecha;

    public ProductoMapper(ProductoFormatearFecha productoFormatearFecha) {
        this.productoFormatearFecha = productoFormatearFecha;
        log.info("🔥 ProductoMapper inicializado correctamente.");
    }

    public ProductoEntity mapRequestDtoToEntity(ProductoRequestDTO productoRequestDTO) {
        log.info("📦 [MAPEO] Iniciando mapeo DTO → Entity para producto: nombre={}", productoRequestDTO.getNombre());

        ProductoEntity productoEntity = new ProductoEntity();

        productoEntity.setCodigo(productoRequestDTO.getCodigo());
        productoEntity.setNombre(productoRequestDTO.getNombre());
        productoEntity.setReferencia(productoRequestDTO.getReferencia());
        productoEntity.setDescripcion(productoRequestDTO.getDescripcion());
        productoEntity.setMarca(productoRequestDTO.getMarca());
        productoEntity.setUnidadMedida(productoRequestDTO.getUnidadMedida());
        productoEntity.setCantidad(productoRequestDTO.getCantidad());
        productoEntity.setPrecio(productoRequestDTO.getPrecio());
        productoEntity.setPrecioCosto(productoRequestDTO.getPrecioCosto());
        productoEntity.setProveedorId(productoRequestDTO.getProveedorId());
        productoEntity.setProveedorName(productoRequestDTO.getProveedorName());
        productoEntity.setCreadoPor(productoRequestDTO.getCreadoPor());
        productoEntity.setFechaCreacion(LocalDateTime.now());
        productoEntity.setEliminado(false);

        log.info("✅ [MAPEO] Mapeo completado DTO → Entity para producto: nombre={}", productoRequestDTO.getNombre());

        return productoEntity;
    }

    public ProductoResponseDTO mapEntityToResponseDto(ProductoEntity productoEntity) {
        log.info("📦 [MAPEO] Iniciando mapeo Entity → DTO para producto: nombre={}", productoEntity.getNombre());

        ProductoResponseDTO productoResponseDTO = new ProductoResponseDTO();

        productoResponseDTO.setCodigo(productoEntity.getCodigo());
        productoResponseDTO.setNombre(productoEntity.getNombre());
        productoResponseDTO.setReferencia(productoEntity.getReferencia());
        productoResponseDTO.setDescripcion(productoEntity.getDescripcion());
        productoResponseDTO.setMarca(productoEntity.getMarca());
        productoResponseDTO.setUnidadMedida(productoEntity.getUnidadMedida());
        productoResponseDTO.setCantidad(productoEntity.getCantidad());
        productoResponseDTO.setPrecio(productoEntity.getPrecio());
        productoResponseDTO.setPrecioCosto(productoEntity.getPrecioCosto());
        productoResponseDTO.setProveedorId(productoEntity.getProveedorId());
        productoResponseDTO.setProveedorName(productoEntity.getProveedorName());
        productoResponseDTO.setCreadoPor(productoEntity.getCreadoPor());
        productoResponseDTO.setActualizadoPor(productoEntity.getActualizadoPor());

        // 🕓 Formateo de fechas
        productoFormatearFecha.asignarFechasFormateadas(productoEntity, productoResponseDTO);

        log.info("✅ [MAPEO] Mapeo completado Entity → DTO para producto: nombre={}", productoEntity.getNombre());

        return productoResponseDTO;
    }

    public ProductoPapeleraResponseDTO mapEntityToPapeleraDto(ProductoEntity productoEntity) {
        log.info("📦 [MAPEO] Iniciando mapeo Entity → PapeleraDTO para producto: {}", productoEntity.getCodigo());

        ProductoPapeleraResponseDTO dto = new ProductoPapeleraResponseDTO();

        dto.setCodigo(productoEntity.getCodigo());
        dto.setNombre(productoEntity.getNombre());
        dto.setDescripcion(productoEntity.getDescripcion());
        dto.setMarca(productoEntity.getMarca());
        dto.setCantidad(productoEntity.getCantidad());
        dto.setPrecio(productoEntity.getPrecio());
        dto.setProveedorName(productoEntity.getProveedorName());
        dto.setCreadoPor(productoEntity.getCreadoPor());
        dto.setFechaCreacion(productoFormatearFecha.formatearFecha(productoEntity.getFechaCreacion()));
        dto.setFechaEliminacion(productoFormatearFecha.formatearFecha(productoEntity.getFechaEliminacion()));
        // Calcular fechaExpiracion y diasRestantes al vuelo (no se persisten en la entidad directamente)
        if (productoEntity.getFechaExpiracion() != null) {
            dto.setFechaExpiracion(productoFormatearFecha.formatearFecha(productoEntity.getFechaExpiracion()));
            dto.setDiasRestantes(ChronoUnit.DAYS.between(LocalDateTime.now(), productoEntity.getFechaExpiracion()));
        }
        dto.setEliminadoPorId(productoEntity.getEliminadoPorId());
        dto.setEliminadoPorNombre(productoEntity.getEliminadoPorNombre());
        dto.setEliminadoPorRol(productoEntity.getEliminadoPorRol());
        dto.setMotivo(productoEntity.getMotivo());

        log.info("✅ [MAPEO] Mapeo papelera completado para producto: {}", dto.getCodigo());

        return dto;
    }

    public void actualizarDatosProductoExistente(ProductoRequestDTO productoRequestDTO, ProductoEntity productoEntity) {
        log.info("📦 [ACTUALIZACION] Iniciando actualizacion de datos para producto: nombre={}", productoRequestDTO.getNombre());

        productoEntity.setNombre(productoRequestDTO.getNombre());
        productoEntity.setReferencia(productoRequestDTO.getReferencia());
        productoEntity.setDescripcion(productoRequestDTO.getDescripcion());
        productoEntity.setMarca(productoRequestDTO.getMarca());
        productoEntity.setUnidadMedida(productoRequestDTO.getUnidadMedida());
        productoEntity.setCantidad(productoRequestDTO.getCantidad());
        productoEntity.setPrecio(productoRequestDTO.getPrecio());
        productoEntity.setPrecioCosto(productoRequestDTO.getPrecioCosto());
        productoEntity.setProveedorId(productoRequestDTO.getProveedorId());
        productoEntity.setProveedorName(productoRequestDTO.getProveedorName());
        productoEntity.setActualizadoPor(productoRequestDTO.getActualizadoPor());

        productoEntity.setFechaActualizacion(LocalDateTime.now());

        log.info("✅ [ACTUALIZACION] Datos actualizados para producto: nombre={}", productoRequestDTO.getNombre());
    }
}
