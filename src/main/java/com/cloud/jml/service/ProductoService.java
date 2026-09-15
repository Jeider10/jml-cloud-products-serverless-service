package com.cloud.jml.service;

import com.cloud.jml.dto.ProductoPapeleraResponseDTO;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    // ─── Listar activos ───────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listarProductos() {
        log.info("🔍 [CONSULTA] Recuperando todos los productos activos");

        List<ProductoEntity> entidades = productoRepository.findAllByEliminadoFalse();

        if (entidades.isEmpty()) {
            log.warn("⚠️ [RESULTADO] No se encontraron productos activos");
            return List.of();
        }

        List<ProductoResponseDTO> respuesta = entidades.stream()
                .map(mapper::mapEntityToResponseDto)
                .toList();

        log.info("✅ [FINALIZADO] Total de productos activos retornados: {}", respuesta.size());

        return respuesta;
    }

    // ─── Crear ────────────────────────────────────────────────────────────────
    @Transactional
    public ProductoResponseDTO crearProducto(ProductoRequestDTO productoRequestDTO) {
        log.info("🔍 [SOLICITUD] Creando producto: {}", productoRequestDTO.getNombre());

        Optional<ProductoEntity> existente = productoRepository.findByCodigo(productoRequestDTO.getCodigo());

        if (existente.isPresent() && !existente.get().isEliminado()) {
            log.warn("❌ [DUPLICADO] Producto activo ya existe con codigo: {}", productoRequestDTO.getCodigo());
            throw new ProductoDuplicadoException(productoRequestDTO.getCodigo());
        }

        ProductoEntity entidad = mapper.mapRequestDtoToEntity(productoRequestDTO);
        ProductoEntity guardado = productoUtils.guardarProductoBD(entidad);

        log.info("💾 [PERSISTENCIA] Producto creado: {}", guardado.getCodigo());

        return mapper.mapEntityToResponseDto(guardado);
    }

    // ─── Buscar por código ────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerProductoPorCodigo(String codigo) {
        log.info("🔍 [CONSULTA] Buscando producto con codigo: {}", codigo);

        ProductoEntity entidad = productoRepository.findByCodigoAndEliminadoFalse(codigo)
                .orElseThrow(() -> {
                    log.warn("❌ [RESULTADO] Producto no encontrado: {}", codigo);
                    return new ProductoNoEncontradoException(codigo);
                });

        log.info("✅ [FINALIZADO] Producto encontrado: {}", codigo);

        return mapper.mapEntityToResponseDto(entidad);
    }

    // ─── Buscar por nombre ────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorNombre(String nombre) {

        List<ProductoEntity> entidades = productoRepository.findByNombreContainingIgnoreCaseAndEliminadoFalse(nombre);

        if (entidades.isEmpty()) {
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Buscar por referencia ────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorReferencia(String referencia) {

        List<ProductoEntity> entidades = productoRepository.findByReferenciaContainingIgnoreCaseAndEliminadoFalse(referencia);

        if (entidades.isEmpty()) {
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Buscar por descripción ───────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorDescripcion(String descripcion) {

        List<ProductoEntity> entidades = productoRepository.findByDescripcionContainingIgnoreCaseAndEliminadoFalse(descripcion);

        if (entidades.isEmpty()) {
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Buscar por marca ─────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorMarca(String marca) {

        List<ProductoEntity> entidades = productoRepository.findByMarcaContainingIgnoreCaseAndEliminadoFalse(marca);

        if (entidades.isEmpty()) {
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Buscar por unidad de medida ──────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorUnidadDeMedida(String unidadMedida) {

        List<ProductoEntity> entidades = productoRepository.findByUnidadMedidaContainingIgnoreCaseAndEliminadoFalse(unidadMedida);

        if (entidades.isEmpty()) {
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Buscar por cantidad ──────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorCantidad(Long cantidad) {

        List<ProductoEntity> entidades = productoRepository.findByCantidadAndEliminadoFalse(cantidad);

        if (entidades.isEmpty()) {
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Buscar por precio ────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorPrecio(java.math.BigDecimal precio) {

        List<ProductoEntity> entidades = productoRepository.findByPrecioAndEliminadoFalse(precio);

        if (entidades.isEmpty()) {
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Buscar por proveedorId ───────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorProveedorId(Long proveedorId) {

        List<ProductoEntity> entidades = productoRepository.findByProveedorIdAndEliminadoFalse(proveedorId);

        if (entidades.isEmpty()) {
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Buscar por proveedorName ─────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorProveedorName(String proveedorName) {

        List<ProductoEntity> entidades = productoRepository.findByProveedorNameContainingIgnoreCaseAndEliminadoFalse(proveedorName);

        if (entidades.isEmpty()) {
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Buscar por creadoPor ─────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorCreadoPor(String creadoPor) {

        List<ProductoEntity> entidades = productoRepository.findByCreadoPorContainingIgnoreCaseAndEliminadoFalse(creadoPor);

        if (entidades.isEmpty()) {
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Buscar por fecha de creación ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorFechaCreacion(String fechaInicio, String fechaFin) {
        log.info("🔍 [CONSULTA] Iniciando busqueda de productos por rango de fecha de creacion: {} - {}", fechaInicio, fechaFin);

        // Parsear fechas con soporte flexible (solo fecha o fecha+hora)
        LocalDateTime inicio = productoUtils.parsearFechaInicio(fechaInicio);
        LocalDateTime fin = productoUtils.parsearFechaFin(fechaFin);

        List<ProductoEntity> entidades = productoRepository.findByFechaCreacionBetweenAndEliminadoFalse(inicio, fin);

        if (entidades.isEmpty()) {
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Buscar por fecha de actualización ───────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorFechaActualizacion(String fechaInicio, String fechaFin) {
        log.info("🔍 [CONSULTA] Iniciando busqueda de productos por rango de fecha de actualizacion: {} - {}", fechaInicio, fechaFin);

        LocalDateTime inicio = productoUtils.parsearFechaInicio(fechaInicio);
        LocalDateTime fin = productoUtils.parsearFechaFin(fechaFin);

        List<ProductoEntity> entidades = productoRepository.findByFechaActualizacionBetweenAndEliminadoFalse(inicio, fin);

        if (entidades.isEmpty()) {
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Actualizar ───────────────────────────────────────────────────────────
    @Transactional
    public ProductoResponseDTO actualizarProducto(ProductoRequestDTO productoRequestDTO) {
        log.info("🔍 [SOLICITUD] Actualizando producto con codigo: {}", productoRequestDTO.getCodigo());

        ProductoEntity entidad = productoUtils.validarExistenciaProducto(productoRequestDTO);
        mapper.actualizarDatosProductoExistente(productoRequestDTO, entidad);
        ProductoEntity actualizado = productoUtils.guardarProductoBD(entidad);

        log.info("✅ [FINALIZADO] Producto actualizado: {}", actualizado.getCodigo());

        return mapper.mapEntityToResponseDto(actualizado);
    }

    // ─── Soft delete (a papelera) ─────────────────────────────────────────────
    @Transactional
    public void eliminarProducto(String codigo, String eliminadoPorId, String eliminadoPorNombre) {
        log.info("🔍 [SOLICITUD] Enviando a papelera producto con codigo: {}", codigo);

        ProductoEntity entidad = productoRepository.findByCodigoAndEliminadoFalse(codigo)
                .orElseThrow(() -> new ProductoNoEncontradoException(codigo));

        entidad.setEliminado(true);
        entidad.setFechaEliminacion(LocalDateTime.now());
        entidad.setEliminadoPorId(eliminadoPorId);
        entidad.setEliminadoPorNombre(eliminadoPorNombre);

        productoUtils.guardarProductoBD(entidad);

        log.info("🗑️ [PAPELERA] Producto {} enviado a papelera por: {}", codigo, eliminadoPorNombre);
    }

    // ─── Listar papelera ──────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductoPapeleraResponseDTO> listarPapelera() {
        log.info("🔍 [CONSULTA] Listando productos en papelera");

        List<ProductoEntity> entidades = productoRepository.findAllByEliminadoTrue();

        if (entidades.isEmpty()) {
            log.warn("⚠️ [RESULTADO] No hay productos en papelera");
            return List.of();
        }

        List<ProductoPapeleraResponseDTO> respuesta = entidades.stream()
                .map(mapper::mapEntityToPapeleraDto)
                .toList();

        log.info("✅ [FINALIZADO] Total de productos en papelera: {}", respuesta.size());

        return respuesta;
    }

    // ─── Restaurar desde papelera ─────────────────────────────────────────────
    @Transactional
    public ProductoResponseDTO restaurarProducto(String codigo) {
        log.info("🔍 [SOLICITUD] Restaurando producto con codigo: {}", codigo);

        ProductoEntity entidad = productoRepository.findByCodigoAndEliminadoTrue(codigo)
                .orElseThrow(() -> {
                    log.warn("❌ [RESULTADO] Producto no encontrado en papelera: {}", codigo);
                    return new ProductoNoEncontradoException(codigo);
                });

        entidad.setEliminado(false);
        entidad.setFechaEliminacion(null);
        entidad.setEliminadoPorId(null);
        entidad.setEliminadoPorNombre(null);
        entidad.setFechaActualizacion(LocalDateTime.now());

        ProductoEntity restaurado = productoUtils.guardarProductoBD(entidad);

        log.info("✅ [FINALIZADO] Producto restaurado: {}", restaurado.getCodigo());

        return mapper.mapEntityToResponseDto(restaurado);
    }

    // ─── Eliminar definitivamente ─────────────────────────────────────────────
    @Transactional
    public void eliminarDefinitivo(String codigo) {
        log.info("🔍 [SOLICITUD] Eliminando definitivamente producto con codigo: {}", codigo);

        ProductoEntity entidad = productoRepository.findByCodigoAndEliminadoTrue(codigo)
                .orElseThrow(() -> {
                    log.warn("❌ [RESULTADO] Producto no encontrado en papelera: {}", codigo);
                    return new ProductoNoEncontradoException(codigo);
                });

        productoUtils.eliminarProductoBD(entidad);

        log.info("🗑️ [ELIMINADO] Producto eliminado definitivamente: {}", codigo);
    }

    // ─── Restar stock (usa findByCodigo sin filtro eliminado) ─────────────────
    @Transactional
    public ProductoResponseDTO restarStock(String codigo, int cantidad) {
        log.info("📦 [STOCK] Restando {} unidades al producto con codigo {}", cantidad, codigo);

        ProductoEntity productoEntity = productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ProductoNoEncontradoException(codigo));

        if (productoEntity.getCantidad() < cantidad) {
            log.warn("📦 [STOCK] Stock insuficiente. Disponible: {}, Solicitado: {}", productoEntity.getCantidad(), cantidad);
            throw new StockInsuficienteException(cantidad);
        }

        productoEntity.setCantidad(productoEntity.getCantidad() - cantidad);
        ProductoEntity actualizado = productoUtils.guardarProductoBD(productoEntity);

        log.info("✅ [STOCK] Nuevo stock para {}: {}", codigo, actualizado.getCantidad());

        return mapper.mapEntityToResponseDto(actualizado);
    }

    // ─── Devolver stock ───────────────────────────────────────────────────────
    @Transactional
    public ProductoResponseDTO devolverStock(String codigo, int cantidad) {
        log.info("📦 [STOCK] Devolviendo {} unidades al producto con codigo {}", cantidad, codigo);

        ProductoEntity productoEntity = productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ProductoNoEncontradoException(codigo));

        productoEntity.setCantidad(productoEntity.getCantidad() + cantidad);
        ProductoEntity actualizado = productoUtils.guardarProductoBD(productoEntity);

        log.info("✅ [STOCK] Nuevo stock para {}: {}", codigo, actualizado.getCantidad());

        return mapper.mapEntityToResponseDto(actualizado);
    }
}
