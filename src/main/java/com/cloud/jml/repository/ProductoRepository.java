package com.cloud.jml.repository;

import com.cloud.jml.model.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<ProductoEntity, String> {

    // ─── Activos (eliminado = false) ─────────────────────────────────────────
    Optional<ProductoEntity> findByCodigoAndEliminadoFalse(String codigo);

    List<ProductoEntity> findAllByEliminadoFalse();

    List<ProductoEntity> findByNombreContainingIgnoreCaseAndEliminadoFalse(String nombre);

    List<ProductoEntity> findByReferenciaContainingIgnoreCaseAndEliminadoFalse(String referencia);

    List<ProductoEntity> findByDescripcionContainingIgnoreCaseAndEliminadoFalse(String descripcion);

    List<ProductoEntity> findByMarcaContainingIgnoreCaseAndEliminadoFalse(String marca);

    List<ProductoEntity> findByUnidadMedidaContainingIgnoreCaseAndEliminadoFalse(String unidadMedida);

    List<ProductoEntity> findByCantidadAndEliminadoFalse(Long cantidad);

    List<ProductoEntity> findByPrecioAndEliminadoFalse(BigDecimal precio);

    List<ProductoEntity> findByProveedorIdAndEliminadoFalse(Long proveedorId);

    List<ProductoEntity> findByProveedorNameContainingIgnoreCaseAndEliminadoFalse(String proveedorName);

    List<ProductoEntity> findByCreadoPorContainingIgnoreCaseAndEliminadoFalse(String creadoPor);

    List<ProductoEntity> findByFechaCreacionBetweenAndEliminadoFalse(LocalDateTime inicio, LocalDateTime fin);

    List<ProductoEntity> findByFechaActualizacionBetweenAndEliminadoFalse(LocalDateTime inicio, LocalDateTime fin);

    // ─── Papelera (eliminado = true) ─────────────────────────────────────────
    List<ProductoEntity> findAllByEliminadoTrue();

    Optional<ProductoEntity> findByCodigoAndEliminadoTrue(String codigo);

    // ─── Stock: se busca sin filtro de eliminado para consistencia ────────────
    Optional<ProductoEntity> findByCodigo(String codigo);
}
