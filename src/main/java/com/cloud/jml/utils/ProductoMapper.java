package com.cloud.jml.utils;

import com.cloud.jml.dto.ProductoRequestDTO;
import com.cloud.jml.dto.ProductoResponseDTO;
import com.cloud.jml.model.ProductoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component // 🔹 Anotación para indicar que es un componente de Spring
public class ProductoMapper {

    private final ProductoUtils productoUtils;

    public ProductoMapper(ProductoUtils productoUtils) {
        this.productoUtils = productoUtils;
    }

    // ------------------ 🔹 Métodos privados de Mapeos ------------------

    public ProductoEntity mapRequestDtoToEntity(ProductoRequestDTO productoRequestDTO) {
        log.info("📌 Iniciando mapeo DTO a Entity para crear Producto");

        ProductoEntity productoEntity = new ProductoEntity();

        productoEntity.setCodigo(productoRequestDTO.getCodigo());
        productoEntity.setNombre(productoRequestDTO.getNombre());
        productoEntity.setDescripcion(productoRequestDTO.getDescripcion());
        productoEntity.setCantidad(productoRequestDTO.getCantidad());
        productoEntity.setPrecio(productoRequestDTO.getPrecio());
        productoEntity.setProveedorId(productoRequestDTO.getProveedorId());
        productoEntity.setProveedorName(productoRequestDTO.getProveedorName());
        productoEntity.setFechaCreacion(LocalDateTime.now());

        log.info("📌 Finalizando mapeo DTO a Entity para crear Producto");

        return productoEntity;
    }

    public ProductoResponseDTO mapEntityToResponseDto(ProductoEntity productoEntity) {
        log.info("📌 Iniciando mapeo Entity a DTO para crear Producto");

        ProductoResponseDTO productoResponseDTO = new ProductoResponseDTO();

        productoResponseDTO.setCodigo(productoEntity.getCodigo());
        productoResponseDTO.setNombre(productoEntity.getNombre());
        productoResponseDTO.setDescripcion(productoEntity.getDescripcion());
        productoResponseDTO.setCantidad(productoEntity.getCantidad());
        productoResponseDTO.setPrecio(productoEntity.getPrecio());
        productoResponseDTO.setProveedorId(productoEntity.getProveedorId());
        productoResponseDTO.setProveedorName(productoEntity.getProveedorName());

        // 🔹 Formatear fechas
        productoUtils.asignarFechasFormateadas(productoEntity, productoResponseDTO);

        log.info("📌 Finalizando mapeo Entity a DTO para crear Producto");

        return productoResponseDTO;
    }
}
