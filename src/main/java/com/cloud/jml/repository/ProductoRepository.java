package com.cloud.jml.repository;

import com.cloud.jml.model.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    Optional<ProductoEntity> findByCodigo(String codigo);

    List<ProductoEntity> findByNombre(String nombre);

    List<ProductoEntity> findByDescripcion(String descripcion);

    List<ProductoEntity> findByCantidad(String cantidad);

    List<ProductoEntity> findByPrecio(String precio);

    List<ProductoEntity> findByProveedor(String proveedor);

    List<ProductoEntity> findByFechaCreacion(LocalDateTime fechaCreacion);
}
