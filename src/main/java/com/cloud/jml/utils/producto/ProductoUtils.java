package com.cloud.jml.utils.producto;

import com.cloud.jml.dto.ProductoRequestDTO;
import com.cloud.jml.exception.producto.ProductoDeletionException;
import com.cloud.jml.exception.producto.ProductoNoEncontradoException;
import com.cloud.jml.exception.producto.ProductoPersistenceException;
import com.cloud.jml.model.ProductoEntity;
import com.cloud.jml.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Slf4j
@Component // 🔹 Anotacion para indicar que es un componente de Spring
public class ProductoUtils {

    private final ProductoRepository productoRepository;

    public ProductoUtils(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
        log.info("🔥 ProductoUtils inicializado correctamente.");
    }

    public ProductoEntity validarExistenciaProducto(ProductoRequestDTO productoRequestDTO) {
        log.info("🔍 [SOLICITUD] Validando existencia de producto: codigo={}", productoRequestDTO.getCodigo());
        Optional<ProductoEntity> optionalProducto = productoRepository.findByCodigo(productoRequestDTO.getCodigo());

        if (optionalProducto.isPresent()) {
            ProductoEntity productoEntity = optionalProducto.get();
            log.info("✅ [FINALIZADO] Producto encontrado: codigo={}", productoEntity.getCodigo());
            return productoEntity;
        } else {
            log.warn("⚠️ [RESULTADO] Producto no encontrado: codigo={}", productoRequestDTO.getCodigo());
            throw new ProductoNoEncontradoException(productoRequestDTO.getCodigo());
        }
    }

    public LocalDateTime parsearFechaInicio(String fecha) {
        log.info("📅 [FECHA] Intentando parsear fecha de inicio: {}", fecha);

        if (fecha == null || fecha.isBlank()) {
            log.error("⚠️ [ERROR] La fecha de inicio recibida es nula o vacia");
            throw new IllegalArgumentException("La fecha de inicio es obligatoria");
        }
        try {
            LocalDateTime res = LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            log.info("✅ [PARSEADO] Fecha inicio procesada (Formato Completo): {}", res);
            return res;
        } catch (DateTimeParseException ignored) {}

        try {
            LocalDateTime res = LocalDateTime.parse(fecha);
            log.info("✅ [PARSEADO] Fecha inicio procesada (ISO): {}", res);
            return res;
        } catch (DateTimeParseException ignored) {}

        try {
            LocalDate localDate = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime res = localDate.atStartOfDay();
            log.info("✅ [PARSEADO] Fecha inicio procesada (Solo Fecha -> 00:00:00): {}", res);
            return res;
        } catch (DateTimeParseException e) {
            log.error("❌ [FORMATO INVALIDO] No se pudo parsear la fecha de inicio: {}", fecha);
            throw new IllegalArgumentException("Formato de fecha de inicio invalido. Use yyyy-MM-dd o yyyy-MM-dd HH:mm:ss");
        }
    }

    public LocalDateTime parsearFechaFin(String fecha) {
        log.info("📅 [FECHA] Intentando parsear fecha de fin: {}", fecha);

        if (fecha == null || fecha.isBlank()) {
            log.error("⚠️ [ERROR] La fecha de fin recibida es nula o vacia");
            throw new IllegalArgumentException("La fecha de fin es obligatoria");
        }
        try {
            LocalDateTime res = LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            log.info("✅ [PARSEADO] Fecha fin procesada (Formato Completo): {}", res);
            return res;
        } catch (DateTimeParseException ignored) {}

        try {
            LocalDateTime res = LocalDateTime.parse(fecha);
            log.info("✅ [PARSEADO] Fecha fin procesada (ISO): {}", res);
            return res;
        } catch (DateTimeParseException ignored) {}

        try {
            LocalDate localDate = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime res = localDate.atTime(23, 59, 59);
            log.info("✅ [PARSEADO] Fecha fin procesada (Solo Fecha -> 23:59:59): {}", res);
            return res;
        } catch (DateTimeParseException e) {
            log.error("❌ [FORMATO INVALIDO] No se pudo parsear la fecha de fin: {}", fecha);
            throw new IllegalArgumentException("Formato de fecha de fin invalido. Use yyyy-MM-dd o yyyy-MM-dd HH:mm:ss");
        }
    }

    public ProductoEntity guardarProductoBD(ProductoEntity productoEntity) {
        try {
            return productoRepository.save(productoEntity);

        } catch (DataIntegrityViolationException e) {
            log.error("🚨 Violacion de integridad al guardar el producto: {}", e.getMessage(), e);
            throw ProductoPersistenceException.integrityViolation(e);

        } catch (DataAccessException e) {
            log.error("🚨 Error de acceso a datos al guardar el producto: {}", e.getMessage(), e);
            throw ProductoPersistenceException.dataAccessError(e);

        } catch (Exception e) {
            log.error("🚨 Error inesperado al guardar el producto: {}", e.getMessage(), e);
            throw ProductoPersistenceException.unexpected(e);
        }
    }

    public void eliminarProductoBD(ProductoEntity productoEntity) {
        try {
            productoRepository.delete(productoEntity);

        } catch (DataIntegrityViolationException e) {
            log.error("🚨 Violacion de integridad al eliminar el producto: {}", e.getMessage(), e);
            throw ProductoDeletionException.integrityViolation(e);

        } catch (DataAccessException e) {
            log.error("🚨 Error de acceso a datos al eliminar el producto: {}", e.getMessage(), e);
            throw ProductoDeletionException.dataAccessError(e);

        } catch (Exception e) {
            log.error("🚨 Error inesperado al eliminar el producto: {}", e.getMessage(), e);
            throw ProductoDeletionException.unexpected(e);
        }
    }
}
