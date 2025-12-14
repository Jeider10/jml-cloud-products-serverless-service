package com.cloud.jml.repository;

import com.cloud.jml.model.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    Optional<ProductoEntity> findByCodigo(Long codigo);

    List<ProductoEntity> findByNombreContainingIgnoreCase(String nombre);

    List<ProductoEntity> findByReferenciaContainingIgnoreCase(String referencia);

    List<ProductoEntity> findByDescripcionContainingIgnoreCase(String descripcion);

    List<ProductoEntity> findByMarcaContainingIgnoreCase(String marca);

    List<ProductoEntity> findByUnidadMedidaContainingIgnoreCase(String unidadMedida);

    List<ProductoEntity> findByCantidad(Long cantidad);

    List<ProductoEntity> findByPrecio(BigDecimal precio);

    List<ProductoEntity> findByProveedorId(Long proveedorId);

    List<ProductoEntity> findByProveedorNameContainingIgnoreCase(String proveedorName);
}
