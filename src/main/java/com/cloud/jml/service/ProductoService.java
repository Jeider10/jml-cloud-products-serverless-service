package com.cloud.jml.service;

import com.cloud.jml.dto.ProductoRequestDTO;
import com.cloud.jml.dto.ProductoResponseDTO;
import com.cloud.jml.exception.producto.ProductoDuplicadoException;
import com.cloud.jml.exception.producto.ProductoNoEncontradoException;
import com.cloud.jml.exception.stock.StockInsuficienteException;
import com.cloud.jml.model.ProductoEntity;
import com.cloud.jml.repository.ProductoRepository;
import com.cloud.jml.utils.producto.ProductoMapper;
import com.cloud.jml.utils.producto.ProductoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper mapper;
    private final ProductoUtils productoUtils;

    public ProductoService(ProductoRepository productoRepository, ProductoMapper mapper, ProductoUtils productoUtils) {
        this.productoRepository = productoRepository;
        this.mapper = mapper;
        this.productoUtils = productoUtils;
        log.info("🔥 ProductoService inicializado correctamente.");
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listarProductos() {
        log.info("🔍 [CONSULTA] Recuperando todos los productos desde la base de datos");

        List<ProductoEntity> productoEntity = productoRepository.findAll();

        if (productoEntity.isEmpty()) {
            log.warn("⚠️ [RESULTADO] No se encontraron productos registrados en la base de datos");
            return List.of();
        }

        log.info("📦 [MAPEO] Transformando {} entidades de productos a DTOs", productoEntity.size());

        // convertir a stream
        Stream<ProductoEntity> streamProductos = productoEntity.stream();

        // mapear entidades a DTOs
        Stream<ProductoResponseDTO> streamProductosDTO = streamProductos.map(mapper::mapEntityToResponseDto);

        // recolectar en lista
        List<ProductoResponseDTO> productosResponse = streamProductosDTO.toList();

        log.info("✅ [FINALIZADO] Total de productos mapeados y retornados: {}", productosResponse.size());

        return productosResponse;
    }

    @Transactional
    public ProductoResponseDTO crearProducto(ProductoRequestDTO productoRequestDTO) {
        log.info("🔍 [CONSULTA] Inicio de creación de producto: {}", productoRequestDTO.getNombre());

        Optional<ProductoEntity> productoExistente = productoRepository.findByCodigo(productoRequestDTO.getCodigo());

        if (productoExistente.isPresent()) {
            log.warn("❌ [ERROR] Producto duplicado detectado: {}", productoRequestDTO.getCodigo());
            throw new ProductoDuplicadoException(productoRequestDTO.getCodigo());
        }

        log.info("📦 [MAPEO] Transformando DTO a entidad de producto");
        ProductoEntity productoEntity = mapper.mapRequestDtoToEntity(productoRequestDTO);
        log.info("📦 [MAPEO] Producto: {} mapeado a entidad con código: {}", productoEntity.getNombre(), productoEntity.getCodigo());

        ProductoEntity guardarProducto = productoUtils.guardarProductoBD(productoEntity);
        log.info("💾 [PERSISTENCIA] Producto: {} guardado exitosamente con código: {}", guardarProducto.getNombre(), guardarProducto.getCodigo());

        log.info("📦 [MAPEO] Transformando entidad de producto a DTO. (crearProducto)");
        ProductoResponseDTO productoResponseDTO = mapper.mapEntityToResponseDto(guardarProducto);
        log.info("📦 [MAPEO] Producto mapeado a DTO. código: {}, nombre: {}, descripción: {}",
                productoResponseDTO.getCodigo(), productoResponseDTO.getNombre(), productoResponseDTO.getDescripcion());

        log.info("✅ [FINALIZADO] Producto creado correctamente: {} con código {}", productoResponseDTO.getNombre(), productoResponseDTO.getCodigo());

        return productoResponseDTO;
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerProductoPorCodigo(ProductoRequestDTO productoRequestDTO) {
        log.info("🔍 [CONSULTA] Iniciando búsqueda de producto por código: {}", productoRequestDTO.getCodigo());

        Optional<ProductoEntity> optionalProducto = productoRepository.findByCodigo(productoRequestDTO.getCodigo());

        if (optionalProducto.isEmpty()) {
            log.warn("❌ [RESULTADO] Producto no encontrado con código: {}", productoRequestDTO.getCodigo());
            return null;
        }

        ProductoEntity productoEntity = optionalProducto.get();
        log.info("📦 [ENCONTRADO] Producto encontrado -> código: {}, nombre: {}, cantidad: {}",
                productoEntity.getCodigo(), productoEntity.getNombre(), productoEntity.getCantidad());

        log.info("📦 [MAPEO] Transformando entidad de producto a DTO. (obtenerProductoPorCodigo)");
        ProductoResponseDTO productoResponseDTO = mapper.mapEntityToResponseDto(productoEntity);
        log.info("📦 [MAPEO] Producto mapeado a DTO. código: {}", productoResponseDTO.getCodigo());

        log.info("✅ [FINALIZADO] Producto encontrado con código: {}", productoResponseDTO.getCodigo());

        return productoResponseDTO;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorNombre(ProductoRequestDTO productoRequestDTO) {
        log.info("🔍 [CONSULTA] Iniciando búsqueda de producto por nombre: {}", productoRequestDTO.getNombre());

        List<ProductoEntity> productoEntity = productoRepository.findByNombreContainingIgnoreCase(productoRequestDTO.getNombre());

        if (productoEntity.isEmpty()) {
            log.warn("❌ [RESULTADO] No se encontraron productos con el nombre: {}", productoRequestDTO.getNombre());
            return List.of();
        }

        log.info("📦 [MAPEO] Transformando {} entidades de productos a DTOs (nombre: {})", productoEntity.size(), productoRequestDTO.getNombre());

        // convertir a stream
        Stream<ProductoEntity> streamProductos = productoEntity.stream();

        // mapear entidades a DTOs
        Stream<ProductoResponseDTO> streamDto = streamProductos.map(mapper::mapEntityToResponseDto);

        // recolectar en lista
        List<ProductoResponseDTO> productosResponse = streamDto.toList();

        log.info("✅ [FINALIZADO] Productos encontrados con nombre: {}. Total encontrados: {}", productoRequestDTO.getNombre(), productosResponse.size());

        return productosResponse;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorReferencia(ProductoRequestDTO productoRequestDTO) {
        log.info("🔍 [CONSULTA] Iniciando búsqueda de producto por referencia: {}", productoRequestDTO.getReferencia());

        List<ProductoEntity> productoEntity = productoRepository.findByReferenciaContainingIgnoreCase(productoRequestDTO.getReferencia());

        if (productoEntity.isEmpty()) {
            log.warn("❌ [RESULTADO] No se encontraron productos con referencia: {}", productoRequestDTO.getReferencia());
            return List.of();
        }

        log.info("📦 [MAPEO] Transformando {} entidades de productos a DTOs (referencia: {})", productoEntity.size(), productoRequestDTO.getReferencia());

        // convertir a stream
        Stream<ProductoEntity> streamProductos = productoEntity.stream();

        // mapear entidades a DTOs
        Stream<ProductoResponseDTO> streamDto = streamProductos.map(mapper::mapEntityToResponseDto);

        // recolectar en lista
        List<ProductoResponseDTO> productosResponse = streamDto.toList();

        log.info("✅ [FINALIZADO] Productos encontrados con referencia: {}. Total encontrados: {}", productoRequestDTO.getReferencia(), productosResponse.size());

        return productosResponse;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorDescripcion(ProductoRequestDTO productoRequestDTO) {
        log.info("🔍 [CONSULTA] Iniciando búsqueda de producto por descripción: {}", productoRequestDTO.getDescripcion());

        List<ProductoEntity> productoEntity = productoRepository.findByDescripcionContainingIgnoreCase(productoRequestDTO.getDescripcion());

        if (productoEntity.isEmpty()) {
            log.warn("❌ [RESULTADO] No se encontraron productos con descripción: {}", productoRequestDTO.getDescripcion());
            return List.of();
        }

        log.info("📦 [MAPEO] Transformando {} entidades de productos a DTOs (descripción: {})", productoEntity.size(), productoRequestDTO.getDescripcion());

        // convertir a stream
        Stream<ProductoEntity> streamProductos = productoEntity.stream();

        // mapear entidades a DTOs
        Stream<ProductoResponseDTO> streamDto = streamProductos.map(mapper::mapEntityToResponseDto);

        // recolectar en lista
        List<ProductoResponseDTO> productosResponse = streamDto.toList();

        log.info("✅ [FINALIZADO] Productos encontrados con descripción: {}. Total encontrados: {}", productoRequestDTO.getDescripcion(), productoEntity.size());

        return productosResponse;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorMarca(ProductoRequestDTO productoRequestDTO) {
        log.info("🔍 [CONSULTA] Iniciando búsqueda de producto por marca: {}", productoRequestDTO.getMarca());

        List<ProductoEntity> productoEntity = productoRepository.findByMarcaContainingIgnoreCase(productoRequestDTO.getMarca());

        if (productoEntity.isEmpty()) {
            log.warn("❌ [RESULTADO] No se encontraron productos con marca: {}", productoRequestDTO.getMarca());
            return List.of();
        }

        log.info("📦 [MAPEO] Transformando {} entidades de productos a DTOs (marca: {})", productoEntity.size(), productoRequestDTO.getMarca());

        // convertir a stream
        Stream<ProductoEntity> streamProductos = productoEntity.stream();

        // mapear entidades a DTOs
        Stream<ProductoResponseDTO> streamDto = streamProductos.map(mapper::mapEntityToResponseDto);

        // recolectar en lista
        List<ProductoResponseDTO> productosResponse = streamDto.toList();

        log.info("✅ [FINALIZADO] Productos encontrados con marca: {}. Total encontrados: {}", productoRequestDTO.getMarca(), productoEntity.size());

        return productosResponse;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorUnidadDeMedida(ProductoRequestDTO productoRequestDTO) {
        log.info("🔍 [CONSULTA] Iniciando búsqueda de producto por unidad de medida: {}", productoRequestDTO.getUnidadMedida());

        List<ProductoEntity> productoEntity = productoRepository.findByUnidadMedidaContainingIgnoreCase(productoRequestDTO.getUnidadMedida());

        if (productoEntity.isEmpty()) {
            log.warn("❌ [RESULTADO] No se encontraron productos con unidad de medida: {}", productoRequestDTO.getUnidadMedida());
            return List.of();
        }

        log.info("📦 [MAPEO] Transformando {} entidades de productos a DTOs (unidad de medida: {})", productoEntity.size(), productoRequestDTO.getUnidadMedida());

        // convertir a stream
        Stream<ProductoEntity> streamProductos = productoEntity.stream();

        // mapear entidades a DTOs
        Stream<ProductoResponseDTO> streamDto = streamProductos.map(mapper::mapEntityToResponseDto);

        // recolectar en lista
        List<ProductoResponseDTO> productosResponse = streamDto.toList();

        log.info("✅ [FINALIZADO] Productos encontrados con unidad de medida: {}. Total encontrados: {}", productoRequestDTO.getUnidadMedida(), productoEntity.size());

        return productosResponse;
    }

    @Transactional
    public List<ProductoResponseDTO> obtenerProductoPorCantidad(ProductoRequestDTO productoRequestDTO) {
        log.info("🔍 [CONSULTA] Iniciando búsqueda de producto por cantidad: {}", productoRequestDTO.getCantidad());

        List<ProductoEntity> productoEntity = productoRepository.findByCantidad(productoRequestDTO.getCantidad());

        if (productoEntity.isEmpty()) {
            log.warn("❌ [RESULTADO] No se encontraron productos con cantidad: {}", productoRequestDTO.getCantidad());
            return List.of();
        }

        log.info("📦 [MAPEO] Transformando {} entidades de productos a DTOs (cantidad: {})", productoEntity.size(), productoRequestDTO.getCantidad());

        // convertir a stream
        Stream<ProductoEntity> streamProductos = productoEntity.stream();

        // mapear entidades a DTOs
        Stream<ProductoResponseDTO> streamDto = streamProductos.map(mapper::mapEntityToResponseDto);

        // recolectar en lista
        List<ProductoResponseDTO> productosResponse = streamDto.toList();

        log.info("✅ [FINALIZADO] Productos encontrados con cantidad: {}. Total encontrados: {}", productoRequestDTO.getCantidad(), productoEntity.size());

        return productosResponse;
    }

    @Transactional
    public List<ProductoResponseDTO> obtenerProductoPorPrecio(ProductoRequestDTO productoRequestDTO) {
        log.info("🔍 [CONSULTA] Iniciando búsqueda de producto por precio: {}", productoRequestDTO.getPrecio());

        List<ProductoEntity> productoEntity = productoRepository.findByPrecio(productoRequestDTO.getPrecio());

        if (productoEntity.isEmpty()) {
            log.warn("❌ [RESULTADO] No se encontraron productos con precio: {}", productoRequestDTO.getPrecio());
            return List.of();
        }

        log.info("📦 [MAPEO] Transformando {} entidades de productos a DTOs (precio: {})", productoEntity.size(), productoRequestDTO.getPrecio());

        // convertir a stream
        Stream<ProductoEntity> streamProductos = productoEntity.stream();

        // mapear entidades a DTOs
        Stream<ProductoResponseDTO> streamDto = streamProductos.map(mapper::mapEntityToResponseDto);

        // recolectar en lista
        List<ProductoResponseDTO> productosResponse = streamDto.toList();

        log.info("✅ [FINALIZADO] Productos encontrados con precio: {}. Total encontrados: {}", productoRequestDTO.getPrecio(), productoEntity.size());

        return productosResponse;
    }

    @Transactional
    public List<ProductoResponseDTO> obtenerProductoPorProveedorId(ProductoRequestDTO productoRequestDTO) {
        log.info("🔍 [CONSULTA] Iniciando búsqueda de producto por proveedorId: {}", productoRequestDTO.getProveedorId());

        List<ProductoEntity> productoEntity = productoRepository.findByProveedorId(productoRequestDTO.getProveedorId());

        if (productoEntity.isEmpty()) {
            log.warn("❌ [RESULTADO] No se encontraron productos con proveedorId: {}", productoRequestDTO.getProveedorId());
            return List.of();
        }

        log.info("📦 [MAPEO] Transformando {} entidades de productos a DTOs (proveedorId: {})", productoEntity.size(), productoRequestDTO.getProveedorId());

        // convertir a stream
        Stream<ProductoEntity> streamProductos = productoEntity.stream();

        // mapear entidades a DTOs
        Stream<ProductoResponseDTO> streamDto = streamProductos.map(mapper::mapEntityToResponseDto);

        // recolectar en lista
        List<ProductoResponseDTO> productosResponse = streamDto.toList();

        log.info("✅ [FINALIZADO] Productos encontrados con proveedorId: {}. Total encontrados: {}", productoRequestDTO.getProveedorId(), productoEntity.size());

        return productosResponse;
    }

    @Transactional
    public List<ProductoResponseDTO> obtenerProductoPorProveedorName(ProductoRequestDTO productoRequestDTO) {
        log.info("🔍 [CONSULTA] Iniciando búsqueda de producto por proveedorName: {}", productoRequestDTO.getProveedorName());

        List<ProductoEntity> productoEntity = productoRepository.findByProveedorNameContainingIgnoreCase(productoRequestDTO.getProveedorName());

        if (productoEntity.isEmpty()) {
            log.warn("❌ [RESULTADO] No se encontraron productos con proveedorName: {}", productoRequestDTO.getProveedorName());
            return List.of();
        }

        log.info("📦 [MAPEO] Transformando {} entidades de productos a DTOs (proveedorName: {})", productoEntity.size(), productoRequestDTO.getProveedorName());

        // convertir a stream
        Stream<ProductoEntity> streamProductos = productoEntity.stream();

        // mapear entidades a DTOs
        Stream<ProductoResponseDTO> streamDto = streamProductos.map(mapper::mapEntityToResponseDto);

        // recolectar en lista
        List<ProductoResponseDTO> productosResponse = streamDto.toList();

        log.info("✅ [FINALIZADO] Productos encontrados con proveedorName: {}. Total encontrados: {}", productoRequestDTO.getProveedorName(), productoEntity.size());

        return productosResponse;
    }

    @Transactional
    public ProductoResponseDTO actualizarProducto(ProductoRequestDTO productoRequestDTO) {
        log.info("🔍 [CONSULTA] Inicio de actualización de producto con código: {}", productoRequestDTO.getCodigo());

        // Paso 1: Validar existencia
        ProductoEntity productoEntity = productoUtils.validarExistenciaProducto(productoRequestDTO);

        // Paso 2: Actualizar datos
        mapper.actualizarDatosProductoExistente(productoRequestDTO, productoEntity);

        // Paso 3: Guardar cambios en la BD
        ProductoEntity actualizado = productoUtils.guardarProductoBD(productoEntity);
        log.info("💾 [PERSISTENCIA] Producto actualizado: {} con código: {}", actualizado.getNombre(), actualizado.getCodigo());

        // Paso 4: Mapear a DTO
        log.info("📦 [MAPEO] Transformando entidad de producto a DTO. (actualizarProducto)");
        ProductoResponseDTO productoResponseDTO = mapper.mapEntityToResponseDto(actualizado);
        log.info("📦 [MAPEO] Producto mapeado a DTO. código: {}, nombres: {}",
                productoEntity.getCodigo(), productoEntity.getNombre());

        log.info("✅ [FINALIZADO] Actualización de producto completada: {} con código: {}", productoResponseDTO.getNombre(), productoResponseDTO.getCodigo());

        return productoResponseDTO;
    }

    @Transactional
    public void eliminarProducto(ProductoRequestDTO productoRequestDTO) {
        log.info("🔍 [CONSULTA] Inicio de eliminación de producto con código: {}", productoRequestDTO.getCodigo());

        Optional<ProductoEntity> productoExistente = productoRepository.findByCodigo(productoRequestDTO.getCodigo());

        if (productoExistente.isPresent()) {
            ProductoEntity productoEntity = productoExistente.get();
            log.info("📦 [ENCONTRADO] Producto localizado -> {} con código: {}", productoEntity.getNombre(), productoEntity.getCodigo());

            productoUtils.eliminarProductoBD(productoEntity);
            log.info("🗑️ [ELIMINADO] Producto eliminado correctamente -> {} con código: {}", productoEntity.getNombre(), productoEntity.getCodigo());
        } else {
            log.warn("❌ [NO ENCONTRADO] Producto no encontrado con código: {}", productoRequestDTO.getCodigo());
            throw new ProductoNoEncontradoException(productoRequestDTO.getCodigo());
        }
    }

    @Transactional
    public ProductoResponseDTO restarStock(Long codigo, int cantidad) {
        log.info("📦 [CONSULTA] Iniciando proceso para restar {} unidades al producto con código {}", cantidad, codigo);

        Optional<ProductoEntity> optionalProducto = productoRepository.findByCodigo(codigo);

        if (optionalProducto.isEmpty()) {
            log.warn("❌ [NO ENCONTRADO] Producto no encontrado con código {} en la base.", codigo);
            throw new ProductoNoEncontradoException(codigo);
        }

        ProductoEntity productoEntity = optionalProducto.get();
        log.info("📦 [ENCONTRADO] Producto encontrado -> {} con código: {}", productoEntity.getNombre(), productoEntity.getCodigo());

        if (productoEntity.getCantidad() < cantidad) {
            log.warn("📦 [STOCK] Stock insuficiente. Disponible: {}, Solicitado: {} para producto {}",
                    productoEntity.getCantidad(), cantidad, codigo);
            throw new StockInsuficienteException(cantidad);
        }

        // Restar stock
        long nuevoStock = productoEntity.getCantidad() - cantidad;
        productoEntity.setCantidad(nuevoStock);

        log.info("📦 [STOCK] Stock actualizado correctamente para producto con código {}. Nuevo stock: {}", codigo, nuevoStock);

        ProductoEntity actualizado = productoUtils.guardarProductoBD(productoEntity);

        log.info("✅ [FINALIZADO] Producto con código {} guardado exitosamente con nuevo stock {}", codigo, actualizado.getCantidad());

        log.info("📦 [MAPEO] Transformando entidad de producto a DTO. (restarStock)");
        ProductoResponseDTO productoResponseDTO = mapper.mapEntityToResponseDto(actualizado);
        log.info("📦 [MAPEO] Producto mapeado a DTO. código: {}, cantidad: {}",
                productoEntity.getCodigo(), productoEntity.getCantidad());

        log.info("✅ [FINALIZADO] Actualización de producto completada con código: {} y cantidad: {}",
                productoResponseDTO.getCodigo(), productoResponseDTO.getCantidad());

        return productoResponseDTO;
    }
}
