package com.cloud.jml.service;

import com.cloud.jml.dto.ProductoRequestDTO;
import com.cloud.jml.dto.ProductoResponseDTO;
import com.cloud.jml.exception.ProductoDuplicadoException;
import com.cloud.jml.exception.ProductoNoEncontradoException;
import com.cloud.jml.model.ProductoEntity;
import com.cloud.jml.repository.ProductoRepository;
import com.cloud.jml.utils.ProductoMapper;
import com.cloud.jml.utils.ProductoUtils;
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

    @Transactional
    public ProductoResponseDTO crearProducto(ProductoRequestDTO productoRequestDTO) {
        log.info("📌 Inicio de creación de Producto: {}", productoRequestDTO.getNombre());

        // Verificar si ya existe por codigo
        Optional<ProductoEntity> existente = productoRepository.findByCodigo(productoRequestDTO.getCodigo());
        if (existente.isPresent()) {
            log.warn("⚠️ Producto duplicado: {}", productoRequestDTO.getCodigo());
            throw new ProductoDuplicadoException(productoRequestDTO.getCodigo());
        }

        // Mapeo de DTO a Entity
        ProductoEntity productoEntity = mapper.mapRequestDtoToEntity(productoRequestDTO);

        // Guardamos en la base de datos
        ProductoEntity guardado = productoRepository.save(productoEntity);
        log.info("✅ Producto creado correctamente: {}", productoRequestDTO.getNombre());

        // Convertimos de nuevo a DTO
        ProductoResponseDTO productoResponseDTO = mapper.mapEntityToResponseDto(guardado);
        log.info("📌 Finaliza creación de Producto: {}", productoResponseDTO.getNombre());

        return productoResponseDTO;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listarProductos() {
        log.info("📌 Iniciando búsqueda de todos los Productos.");

        // Paso 1: Obtener entidades desde la BD
        List<ProductoEntity> productoEntity = productoRepository.findAll();

        // Paso 2: Convertir a Stream
        Stream<ProductoEntity> streamProductos = productoEntity.stream();

        // Paso 3: Mapear cada entidad a DTO
        Stream<ProductoResponseDTO> streamProductosDTO = streamProductos.map(mapper::mapEntityToResponseDto);

        // Paso 4: Convertir a lista final
        List<ProductoResponseDTO> productosResponse = streamProductosDTO.toList();

        log.info("📌 Finaliza búsqueda de todos los Productos. Total encontrados: {}", productosResponse.size());

        return productosResponse;
    }

    @Transactional(readOnly = true)
    public Optional<ProductoResponseDTO> obtenerProductoPorCodigo(Long codigo) {
        log.info("📌 Iniciando búsqueda de Producto por código: {}", codigo);

        Optional<ProductoEntity> productoEntity = productoRepository.findByCodigo(codigo);

        if (productoEntity.isPresent()) {
            Optional<ProductoResponseDTO> productoResponseDTO = productoEntity.map(mapper::mapEntityToResponseDto);
            log.info("✅ Producto encontrado con código: {}", codigo);
            return productoResponseDTO;
        } else {
            Optional<ProductoResponseDTO> productoResponseDTO = Optional.empty();
            log.warn("⚠️ Producto no encontrado con código: {}", codigo);
            return productoResponseDTO;
        }
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorNombre(ProductoRequestDTO productoRequestDTO) {
        log.info("📌 Iniciando búsqueda de Producto por nombre: {}", productoRequestDTO.getNombre());

        // Paso 1: Buscar entidades por nombre
        List<ProductoEntity> productoEntity = productoRepository.findByNombreContainingIgnoreCase(productoRequestDTO.getNombre());

        // Paso 2: Validar si está vacío
        if (productoEntity.isEmpty()) {
            log.warn("⚠️ No se encontraron productos con el nombre: {}", productoRequestDTO.getNombre());
            return List.of(); // Retorna lista vacía
        }

        // Paso 3: Convertir a Stream
        Stream<ProductoEntity> streamProductos = productoEntity.stream();

        // Paso 4: Mapear cada entidad a DTO
        Stream<ProductoResponseDTO> streamDto = streamProductos.map(mapper::mapEntityToResponseDto);

        // Paso 5: Convertir a lista final
        List<ProductoResponseDTO> productosResponse = streamDto.toList();

        log.info("📌 Finaliza búsqueda de Producto por nombre: {}. Total encontrados: {}",
                productoRequestDTO.getNombre(), productosResponse.size());

        return productosResponse;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorDescripcion(ProductoRequestDTO productoRequestDTO) {
        log.info("📌 Iniciando búsqueda de Producto por descripcion: {}", productoRequestDTO.getDescripcion());

        // Paso 1: Buscar entidades por nombre
        List<ProductoEntity> productoEntity = productoRepository.findByDescripcionContainingIgnoreCase(productoRequestDTO.getDescripcion());

        // Paso 2: Validar si está vacío
        if (productoEntity.isEmpty()) {
            log.warn("⚠️ No se encontraron productos con descripcion: {}", productoRequestDTO.getDescripcion());
            return List.of(); // Retorna lista vacía
        }

        // Paso 3: Convertir a Stream
        Stream<ProductoEntity> streamProductos = productoEntity.stream();

        // Paso 4: Mapear cada entidad a DTO
        Stream<ProductoResponseDTO> streamDto = streamProductos.map(mapper::mapEntityToResponseDto);

        // Paso 5: Convertir a lista final
        List<ProductoResponseDTO> productosResponse = streamDto.toList();

        log.info("📌 Finaliza búsqueda de Producto por descripcion: {}. Total encontrados: {}",
                productoRequestDTO.getDescripcion(), productosResponse.size());

        return productosResponse;
    }

    @Transactional
    public List<ProductoResponseDTO> obtenerProductoPorCantidad(ProductoRequestDTO productoRequestDTO) {
        log.info("📌 Iniciando búsqueda de Producto por cantidad: {}", productoRequestDTO.getCantidad());

        // Paso 1: Buscar entidades por nombre
        List<ProductoEntity> productoEntity = productoRepository.findByCantidad(productoRequestDTO.getCantidad());

        // Paso 2: Validar si está vacío
        if (productoEntity.isEmpty()) {
            log.warn("⚠️ No se encontraron productos con cantidad: {}", productoRequestDTO.getCantidad());
            return List.of(); // Retorna lista vacía
        }

        // Paso 3: Convertir a Stream
        Stream<ProductoEntity> streamProductos = productoEntity.stream();

        // Paso 4: Mapear cada entidad a DTO
        Stream<ProductoResponseDTO> streamDto = streamProductos.map(mapper::mapEntityToResponseDto);

        // Paso 5: Convertir a lista final
        List<ProductoResponseDTO> productosResponse = streamDto.toList();

        log.info("📌 Finaliza búsqueda de Producto por cantidad: {}. Total encontrados: {}",
                productoRequestDTO.getCantidad(), productosResponse.size());

        return productosResponse;
    }

    @Transactional
    public List<ProductoResponseDTO> obtenerProductoPorPrecio(ProductoRequestDTO productoRequestDTO) {
        log.info("📌 Iniciando búsqueda de Producto por precio: {}", productoRequestDTO.getPrecio());

        // Paso 1: Buscar entidades por nombre
        List<ProductoEntity> productoEntity = productoRepository.findByPrecio(productoRequestDTO.getPrecio());

        // Paso 2: Validar si está vacío
        if (productoEntity.isEmpty()) {
            log.warn("⚠️ No se encontraron productos con precio: {}", productoRequestDTO.getPrecio());
            return List.of(); // Retorna lista vacía
        }

        // Paso 3: Convertir a Stream
        Stream<ProductoEntity> streamProductos = productoEntity.stream();

        // Paso 4: Mapear cada entidad a DTO
        Stream<ProductoResponseDTO> streamDto = streamProductos.map(mapper::mapEntityToResponseDto);

        // Paso 5: Convertir a lista final
        List<ProductoResponseDTO> productosResponse = streamDto.toList();

        log.info("📌 Finaliza búsqueda de Producto por precio: {}. Total encontrados: {}",
                productoRequestDTO.getPrecio(), productosResponse.size());

        return productosResponse;
    }

    @Transactional
    public List<ProductoResponseDTO> obtenerProductoPorProveedorId(ProductoRequestDTO productoRequestDTO) {
        log.info("📌 Iniciando búsqueda de Producto por proveedorId: {}", productoRequestDTO.getProveedorId());

        // Paso 1: Buscar entidades por nombre
        List<ProductoEntity> productoEntity = productoRepository.findByProveedorId(productoRequestDTO.getProveedorId());

        // Paso 2: Validar si está vacío
        if (productoEntity.isEmpty()) {
            log.warn("⚠️ No se encontraron productos con proveedorId: {}", productoRequestDTO.getProveedorId());
            return List.of(); // Retorna lista vacía
        }

        // Paso 3: Convertir a Stream
        Stream<ProductoEntity> streamProductos = productoEntity.stream();

        // Paso 4: Mapear cada entidad a DTO
        Stream<ProductoResponseDTO> streamDto = streamProductos.map(mapper::mapEntityToResponseDto);

        // Paso 5: Convertir a lista final
        List<ProductoResponseDTO> productosResponse = streamDto.toList();

        log.info("📌 Finaliza búsqueda de Producto por proveedorId: {}. Total encontrados: {}",
                productoRequestDTO.getProveedorId(), productosResponse.size());

        return productosResponse;
    }

    @Transactional
    public List<ProductoResponseDTO> obtenerProductoPorProveedorName(ProductoRequestDTO productoRequestDTO) {
        log.info("📌 Iniciando búsqueda de Producto por proveedorName: {}", productoRequestDTO.getProveedorName());

        // Paso 1: Buscar entidades por nombre
        List<ProductoEntity> productoEntity = productoRepository.findByProveedorNameContainingIgnoreCase(productoRequestDTO.getProveedorName());

        // Paso 2: Validar si está vacío
        if (productoEntity.isEmpty()) {
            log.warn("⚠️ No se encontraron productos con proveedorName: {}", productoRequestDTO.getProveedorName());
            return List.of(); // Retorna lista vacía
        }

        // Paso 3: Convertir a Stream
        Stream<ProductoEntity> streamProductos = productoEntity.stream();

        // Paso 4: Mapear cada entidad a DTO
        Stream<ProductoResponseDTO> streamDto = streamProductos.map(mapper::mapEntityToResponseDto);

        // Paso 5: Convertir a lista final
        List<ProductoResponseDTO> productosResponse = streamDto.toList();

        log.info("📌 Finaliza búsqueda de Producto por proveedorName: {}. Total encontrados: {}",
                productoRequestDTO.getProveedorName(), productosResponse.size());

        return productosResponse;
    }

    @Transactional
    public ProductoResponseDTO actualizarProducto(ProductoRequestDTO productoRequestDTO) {
        log.info("📌 Iniciando actualización de Producto con codigo: {}", productoRequestDTO.getCodigo());

        // Paso 1: Validar existencia
        ProductoEntity productoEntity = productoUtils.validarExistenciaProducto(productoRequestDTO);

        // Paso 2: Actualizar datos
        productoUtils.actualizarDatosProducto(productoRequestDTO, productoEntity);

        // Paso 3: Guardar cambios en la BD
        ProductoEntity actualizado = productoRepository.save(productoEntity);
        log.info("✅ Producto actualizado con código: {}", productoRequestDTO.getNombre());

        // Paso 4: Mapear a DTO
        ProductoResponseDTO productoResponseDTO = mapper.mapEntityToResponseDto(actualizado);
        log.info("📌 Finaliza actualización de Producto: {} con codigo: {}", productoResponseDTO.getNombre(), productoResponseDTO.getCodigo());

        return productoResponseDTO;
    }

    @Transactional
    public void eliminarProducto(ProductoRequestDTO productoRequestDTO) {
        log.info("📌 Iniciando eliminación de Producto con codigo: {}", productoRequestDTO.getCodigo());

        Optional<ProductoEntity> productoOptional = productoRepository.findByCodigo(productoRequestDTO.getCodigo());

        if (productoOptional.isPresent()) {
            ProductoEntity productoEntity = productoOptional.get();
            productoRepository.delete(productoEntity);
            log.info("✅ Producto eliminado con codigo: {}", productoRequestDTO.getCodigo());
        } else {
            log.warn("⚠️ No se encontró el Producto con codigo: {}", productoRequestDTO.getCodigo());
            throw new ProductoNoEncontradoException(productoRequestDTO.getCodigo());
        }
    }

    @Transactional
    public ProductoResponseDTO restarStock(Long codigo, int cantidad) {
        log.info("📦 Iniciando proceso para restar {} unidades al producto con código {}", cantidad, codigo);

        Optional<ProductoEntity> optionalProducto = productoRepository.findByCodigo(codigo);

        if (optionalProducto.isEmpty()) {
            log.warn("⚠️ Producto no encontrado con código en la base: {}", codigo);
            throw new ProductoNoEncontradoException(codigo);
        }

        ProductoEntity producto = optionalProducto.get();

        if (producto.getCantidad() < cantidad) {
            log.warn("⚠️ Stock insuficiente. Disponible: {}, Solicitado: {} para producto {}",
                    producto.getCantidad(), cantidad, codigo);
            throw new IllegalArgumentException("Stock insuficiente");
        }

        // Restar stock
        Long nuevoStock = producto.getCantidad() - cantidad;
        producto.setCantidad(nuevoStock);

        log.info("✅ Stock actualizado correctamente para producto {}. Nuevo stock: {}", codigo, nuevoStock);

        ProductoEntity actualizado = productoRepository.save(producto);

        log.info("📌 Producto {} guardado exitosamente con nuevo stock {}", codigo, actualizado.getCantidad());

        return mapper.mapEntityToResponseDto(actualizado);
    }


//    @Transactional
//    public List<ProductoResponseDTO> obtenerProductoPorFechaCreacion(ProductoRequestDTO productoRequestDTO) {
//        return productoRepository.findByFechaCreacion(productoResponseDTO.getFechaCreacion())
//                .stream()
//                .map(this::mapEntityToDto)
//                .collect(Collectors.toList());
//    }
//
}
