package com.cloud.jml.service;

import com.cloud.jml.dto.ProductoDTO;
import com.cloud.jml.exception.ProductoDuplicadoException;
import com.cloud.jml.exception.ProductoNoEncontradoException;
import com.cloud.jml.model.ProductoEntity;
import com.cloud.jml.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
        log.info("🔥 ProveedorService inicializado correctamente.");
    }

    @Transactional
    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        log.info("📌 Inicio de creación de Producto: {}", productoDTO.getNombre());

        // Verificar si ya existe por codigo
        Optional<ProductoEntity> existente = obtenerProductoPorCodigo(productoDTO);
        if (existente.isPresent()) {
            log.warn("⚠️ Producto duplicado: {}", productoDTO.getCodigo());
            throw new ProductoDuplicadoException(productoDTO.getCodigo());
        }

        // Mapeo de DTO a Entity
        ProductoEntity productoEntity = mapDtoToEntity(productoDTO);
        log.debug("🔹 Producto mapeado a Entity: {}", productoEntity);

        // Guardamos en la base de datos
        ProductoEntity guardado = productoRepository.save(productoEntity);
        log.info("✅ Producto guardado con ID: {}", guardado.getId());

        // Convertimos de nuevo a DTO
        ProductoDTO response = mapEntityToDto(guardado);
        log.debug("🔹 Producto convertido nuevamente a DTO: {}", response);

        log.info("📌 Finalizó creación de Producto: {}", response.getNombre());

        return response;
    }

    @Transactional
    public Optional<ProductoEntity> obtenerProductoPorCodigo(ProductoDTO productoDTO) {
        log.info("📌 Inicio de búsqueda de Producto por codigo: {}", productoDTO.getCodigo());

        Optional<ProductoEntity> byCodigo = productoRepository.findByCodigo(productoDTO.getCodigo());

        if (byCodigo.isPresent()) {
            log.info("✅ Producto encontrado con codigo: {}", byCodigo.get().getCodigo());
        } else {
            log.warn("⚠️ No se encontró Producto con codigo: {}", productoDTO.getCodigo());
        }

        log.info("📌 Finaliza búsqueda de Producto por codigo: {}", productoDTO.getCodigo());

        return byCodigo;
    }

    @Transactional
    public List<ProductoEntity> obtenerProductoPorNombre(ProductoDTO productoDTO) {
        log.info("📌 Inicio de búsqueda de Producto por nombre: {}", productoDTO.getNombre());

        List<ProductoEntity> proveedores = productoRepository.findByNombre(productoDTO.getNombre());

        if (proveedores.isEmpty()) {
            log.warn("⚠️ No se encontró Producto con nombre: {}", productoDTO.getNombre());
        } else {
            log.info("✅ Se encontraron {} Producto(s) con el nombre: {}", proveedores.size(), productoDTO.getNombre());
        }

        log.info("📌 Finaliza búsqueda de cliente por nombre: {}", productoDTO.getNombre());

        return proveedores;
    }

    @Transactional
    public List<ProductoEntity> obtenerProductoPorDescripcion(ProductoDTO productoDTO) {
        log.info("📌 Inicio de búsqueda de Producto por descripcion: {}", productoDTO.getDescripcion());

        List<ProductoEntity> proveedores = productoRepository.findByDescripcion(productoDTO.getDescripcion());

        if (proveedores.isEmpty()) {
            log.warn("⚠️ No se encontró Producto con descripcion: {}", productoDTO.getDescripcion());
        } else {
            log.info("✅ Se encontraron {} Producto(s) con la descripcion: {}", proveedores.size(), productoDTO.getDescripcion());
        }

        log.info("📌 Finaliza búsqueda de Producto por descripcion: {}", productoDTO.getDescripcion());

        return proveedores;
    }

    @Transactional
    public List<ProductoEntity> obtenerProductoPorCantidad(ProductoDTO productoDTO) {
        log.info("📌 Inicio de búsqueda de Producto por cantidad: {}", productoDTO.getCantidad());

        List<ProductoEntity> proveedores = productoRepository.findByCantidad(productoDTO.getCantidad());

        if (proveedores.isEmpty()) {
            log.warn("⚠️ No se encontró Producto con cantidad: {}", productoDTO.getCantidad());
        } else {
            log.info("✅ Se encontraron {} Producto(s) con la cantidad: {}", proveedores.size(), productoDTO.getCantidad());
        }

        log.info("📌 Finaliza búsqueda de Producto por cantidad: {}", productoDTO.getCantidad());

        return proveedores;
    }

    @Transactional
    public List<ProductoEntity> obtenerProductoPorPrecio(ProductoDTO productoDTO) {
        log.info("📌 Inicio de búsqueda de Producto por precio: {}", productoDTO.getPrecio());

        List<ProductoEntity> proveedores = productoRepository.findByPrecio(productoDTO.getPrecio());

        if (proveedores.isEmpty()) {
            log.warn("⚠️ No se encontró Producto con precio: {}", productoDTO.getPrecio());
        } else {
            log.info("✅ Se encontraron {} Producto(s) con precio: {}", proveedores.size(), productoDTO.getPrecio());
        }

        log.info("📌 Finaliza búsqueda de Producto por precio: {}", productoDTO.getPrecio());

        return proveedores;
    }

    @Transactional
    public List<ProductoEntity> obtenerProductoPorProveedor(ProductoDTO productoDTO) {
        log.info("📌 Inicio de búsqueda de Producto por proveedor: {}", productoDTO.getProveedor());

        List<ProductoEntity> proveedores = productoRepository.findByProveedor(productoDTO.getProveedor());

        if (proveedores.isEmpty()) {
            log.warn("⚠️ No se encontró Producto con proveedor: {}", productoDTO.getProveedor());
        } else {
            log.info("✅ Se encontraron {} Producto(s) con proveedor: {}", proveedores.size(), productoDTO.getProveedor());
        }

        log.info("📌 Finaliza búsqueda de Producto por proveedor: {}", productoDTO.getProveedor());

        return proveedores;
    }

    @Transactional
    public List<ProductoEntity> obtenerProductoPorFechaCreacion(ProductoDTO productoDTO) {
        log.info("📌 Inicio de búsqueda de Producto por fechaCreacion: {}", productoDTO.getFechaCreacion());

        List<ProductoEntity> proveedores = productoRepository.findByFechaCreacion(productoDTO.getFechaCreacion());

        if (proveedores.isEmpty()) {
            log.warn("⚠️ No se encontró Producto con fechaCreacion: {}", productoDTO.getFechaCreacion());
        } else {
            log.info("✅ Se encontraron {} Producto(s) con fechaCreacion: {}", proveedores.size(), productoDTO.getFechaCreacion());
        }

        log.info("📌 Finaliza búsqueda de Producto por fechaCreacion: {}", productoDTO.getFechaCreacion());

        return proveedores;
    }

    @Transactional
    public List<ProductoEntity> listarProductos() {
        log.info("📌 Inicio de búsqueda de todos los Productos");

        List<ProductoEntity> allProveedor = productoRepository.findAll();

        log.info("✅ Se encontraron {} Productos", allProveedor.size());

        return allProveedor;
    }

    @Transactional
    public ProductoDTO actualizarProducto(ProductoDTO productoDTO) {
        log.info("📌 Inicio de actualización de Producto: {} con codigo: {}", productoDTO.getNombre(), productoDTO.getCodigo());

        Optional<ProductoEntity> productoOpt = obtenerProductoPorCodigo(productoDTO);

        if (productoOpt.isEmpty()) {
            log.warn("⚠️ No se encontró Producto con codigo: {}", productoDTO.getCodigo());
            throw new ProductoNoEncontradoException(productoDTO.getCodigo());
        }

        // Verificar si ya existe por codigo
        ProductoEntity productoEntity = productoOpt.get();

        actualizarDatosProducto(productoDTO, productoEntity);

        ProductoEntity actualizado = productoRepository.save(productoEntity);
        log.info("✅ Producto actualizado con codigo: {}", actualizado.getCodigo());

        ProductoDTO response = mapEntityToDto(actualizado);
        log.debug("🔹 Producto actualizado convertido a DTO: {}", response);
        log.info("📌 Finalizó actualización de Producto: {} con codigo: {}", response.getNombre(), response.getCodigo());

        return response;
    }

    @Transactional
    public void eliminarProducto(ProductoDTO productoDTO) {
        log.info("📌 Inicio de eliminación de Producto con codigo: {}", productoDTO.getCodigo());

        // Verificar si ya existe por codigo
        ProductoEntity productoEntity = validarExistenciaProducto(productoDTO);

        productoRepository.delete(productoEntity);

        log.info("✅ Producto eliminado con codigo: {}", productoDTO.getCodigo());
    }

    @Transactional
    private ProductoEntity mapDtoToEntity(ProductoDTO productoDTO) {
        log.info("📌 Iniciando mapeo DTO a Entity para crear producto");

        ProductoEntity productoEntity = new ProductoEntity();

        productoEntity.setCodigo(productoDTO.getCodigo());
        productoEntity.setNombre(productoDTO.getNombre());
        productoEntity.setDescripcion(productoDTO.getDescripcion());
        productoEntity.setCantidad(productoDTO.getCantidad());
        productoEntity.setPrecio(productoDTO.getPrecio());
        productoEntity.setProveedor(productoDTO.getProveedor());
        productoEntity.setFechaCreacion(LocalDateTime.now());

        log.info("📌 Finalizando mapeo DTO a Entity para crear producto");

        return productoEntity;
    }

    private ProductoDTO mapEntityToDto(ProductoEntity productoEntity) {
        log.info("📌 Iniciando mapeo Entity a DTO para crear producto");

        ProductoDTO productoDTO = new ProductoDTO();

        productoDTO.setCodigo(productoEntity.getCodigo());
        productoDTO.setNombre(productoEntity.getNombre());
        productoDTO.setDescripcion(productoEntity.getDescripcion());
        productoDTO.setCantidad(productoEntity.getCantidad());
        productoDTO.setPrecio(productoEntity.getPrecio());
        productoDTO.setProveedor(productoEntity.getProveedor());
        productoDTO.setFechaCreacion(productoEntity.getFechaCreacion());
        productoDTO.setFechaActualizacion(productoEntity.getFechaActualizacion());

        log.info("📌 Finalizando mapeo Entity a DTO para crear producto");

        return productoDTO;
    }

    private void actualizarDatosProducto(ProductoDTO productoDTO, ProductoEntity productoEntity) {
        // Actualizamos solo los campos permitidos
        productoEntity.setNombre(productoDTO.getNombre());
        productoEntity.setDescripcion(productoDTO.getDescripcion());
        productoEntity.setCantidad(productoDTO.getCantidad());
        productoEntity.setPrecio(productoDTO.getPrecio());
        productoEntity.setProveedor(productoDTO.getProveedor());

        // Actualizamos la fecha de actualización
        productoEntity.setFechaActualizacion(LocalDateTime.now());
    }

    private ProductoEntity validarExistenciaProducto(ProductoDTO productoDTO) {
        Optional<ProductoEntity> productoOpt = obtenerProductoPorCodigo(productoDTO);

        if (productoOpt.isPresent()) {
            log.info("✅ Producto: {} encontrado con codigo: {}", productoDTO.getNombre(), productoDTO.getCodigo());
            return productoOpt.get();
        } else {
            log.warn("⚠️ No se encontró Producto: {} con codigo: {}", productoDTO.getNombre(), productoDTO.getCodigo());
            throw new ProductoNoEncontradoException(productoDTO.getCodigo());
        }
    }
}
