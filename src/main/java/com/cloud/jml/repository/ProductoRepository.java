package com.cloud.jml.repository;

import com.cloud.jml.model.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    Optional<ProductoEntity> findByCodigo(Long codigo);

    List<ProductoEntity> findByNombre(String nombre);

    List<ProductoEntity> findByNombreContainingIgnoreCase(String nombre);

    List<ProductoEntity> findByDescripcion(String descripcion);

    List<ProductoEntity> findByDescripcionContainingIgnoreCase(String descripcion);

    List<ProductoEntity> findByCantidad(Long cantidad);

    List<ProductoEntity> findByPrecio(Long precio);

    List<ProductoEntity> findByProveedorId(Long proveedorId);

    List<ProductoEntity> findByProveedorName(String proveedorName);

    List<ProductoEntity> findByProveedorNameContainingIgnoreCase(String proveedorName);
}
