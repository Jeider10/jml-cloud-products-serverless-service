package com.cloud.jml.utils.producto;

import com.cloud.jml.dto.ProductoRequestDTO;
import com.cloud.jml.exception.producto.ProductoNoEncontradoException;
import com.cloud.jml.exception.producto.ProductoPersistenceException;
import com.cloud.jml.model.ProductoEntity;
import com.cloud.jml.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component // 🔹 Anotación para indicar que es un componente de Spring
public class ProductoUtils {

    private final ProductoRepository productoRepository;

    public ProductoUtils(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
        log.info("🔥 ProductoUtils inicializado correctamente.");
    }

    public ProductoEntity validarExistenciaProducto(ProductoRequestDTO productoRequestDTO) {
        log.info("🔍 [SOLICITUD] Validando existencia de producto: código={}", productoRequestDTO.getCodigo());
        Optional<ProductoEntity> optionalProducto = productoRepository.findByCodigo(productoRequestDTO.getCodigo());

        if (optionalProducto.isPresent()) {
            ProductoEntity productoEntity = optionalProducto.get();
            log.info("✅ [FINALIZADO] Producto encontrado: código={}", productoEntity.getCodigo());
            return productoEntity;
        } else {
            log.warn("⚠️ [RESULTADO] Producto no encontrado: código={}", productoRequestDTO.getCodigo());
            throw new ProductoNoEncontradoException(productoRequestDTO.getCodigo());
        }
    }

    public ProductoEntity guardarProductoBD(ProductoEntity productoEntity) {
        try {
            return productoRepository.save(productoEntity);

        } catch (DataIntegrityViolationException e) {
            log.error("🚨 Violación de integridad al guardar el producto: {}", e.getMessage(), e);
            throw new ProductoPersistenceException("Error de integridad en base de datos al guardar el producto", e);

        } catch (DataAccessException e) {
            log.error("🚨 Error de acceso a datos al guardar el producto: {}", e.getMessage(), e);
            throw new ProductoPersistenceException("Error al guardar el producto en la base de datos", e);

        } catch (Exception e) {
            log.error("🚨 Error inesperado al guardar el producto: {}", e.getMessage(), e);
            throw new ProductoPersistenceException("Error inesperado al registrar el producto", e);
        }
    }

    public void eliminarProductoBD(ProductoEntity productoEntity) {
        try {
            productoRepository.delete(productoEntity);

        } catch (DataIntegrityViolationException e) {
            log.error("🚨 Violación de integridad al eliminar el producto: {}", e.getMessage(), e);
            throw new ProductoPersistenceException("Error de integridad en base de datos al eliminar el producto", e);

        } catch (DataAccessException e) {
            log.error("🚨 Error de acceso a datos al eliminar el producto: {}", e.getMessage(), e);
            throw new ProductoPersistenceException("Error al eliminar el producto en la base de datos", e);

        } catch (Exception e) {
            log.error("🚨 Error inesperado al eliminar el producto: {}", e.getMessage(), e);
            throw new ProductoPersistenceException("Error inesperado al eliminar el producto", e);
        }
    }
}
