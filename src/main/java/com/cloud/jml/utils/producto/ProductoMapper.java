package com.cloud.jml.utils.producto;

import com.cloud.jml.dto.ProductoRequestDTO;
import com.cloud.jml.dto.ProductoResponseDTO;
import com.cloud.jml.model.ProductoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component // 🔹 Anotación para indicar que es un componente de Spring
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
        productoEntity.setDescripcion(productoRequestDTO.getDescripcion());
        productoEntity.setCantidad(productoRequestDTO.getCantidad());
        productoEntity.setPrecio(productoRequestDTO.getPrecio());
        productoEntity.setProveedorId(productoRequestDTO.getProveedorId());
        productoEntity.setProveedorName(productoRequestDTO.getProveedorName());
        productoEntity.setFechaCreacion(LocalDateTime.now());

        log.info("✅ [MAPEO] Mapeo completado DTO → Entity para producto: nombre={}", productoRequestDTO.getNombre());

        return productoEntity;
    }

    public ProductoResponseDTO mapEntityToResponseDto(ProductoEntity productoEntity) {
        log.info("📦 [MAPEO] Iniciando mapeo Entity → DTO para producto: nombre={}", productoEntity.getNombre());

        ProductoResponseDTO productoResponseDTO = new ProductoResponseDTO();

        productoResponseDTO.setCodigo(productoEntity.getCodigo());
        productoResponseDTO.setNombre(productoEntity.getNombre());
        productoResponseDTO.setDescripcion(productoEntity.getDescripcion());
        productoResponseDTO.setCantidad(productoEntity.getCantidad());
        productoResponseDTO.setPrecio(productoEntity.getPrecio());
        productoResponseDTO.setProveedorId(productoEntity.getProveedorId());
        productoResponseDTO.setProveedorName(productoEntity.getProveedorName());

        // 🕓 Formateo de fechas
        productoFormatearFecha.asignarFechasFormateadas(productoEntity, productoResponseDTO);

        log.info("✅ [MAPEO] Mapeo completado Entity → DTO para producto: nombre={}", productoEntity.getNombre());

        return productoResponseDTO;
    }

    public void actualizarDatosProductoExistente(ProductoRequestDTO productoRequestDTO, ProductoEntity productoEntity) {
        log.info("📦 [ACTUALIZACIÓN] Iniciando actualización de datos para producto: nombre={}", productoRequestDTO.getNombre());

        productoEntity.setNombre(productoRequestDTO.getNombre());
        productoEntity.setDescripcion(productoRequestDTO.getDescripcion());
        productoEntity.setCantidad(productoRequestDTO.getCantidad());
        productoEntity.setPrecio(productoRequestDTO.getPrecio());
        productoEntity.setProveedorId(productoRequestDTO.getProveedorId());
        productoEntity.setProveedorName(productoRequestDTO.getProveedorName());

        productoEntity.setFechaActualizacion(LocalDateTime.now());

        log.info("✅ [ACTUALIZACIÓN] Datos actualizados para producto: nombre={}", productoRequestDTO.getNombre());
    }
}
