package com.cloud.jml.controller;

import com.cloud.jml.dto.ProductoPapeleraResponseDTO;
import com.cloud.jml.dto.ProductoRequestDTO;
import com.cloud.jml.dto.ProductoResponseDTO;
import com.cloud.jml.service.ProductoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
        log.info("🔥 ProductoController inicializado correctamente.");
    }

    // ─── Listar activos ───────────────────────────────────────────────────────
    @GetMapping("/list/all")
    public ResponseEntity<List<ProductoResponseDTO>> listarProductos() {
        log.info("📥 [SOLICITUD] Listar todos los productos activos");

        List<ProductoResponseDTO> productos = productoService.listarProductos();

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} productos", productos.size());

        return ResponseEntity.ok(productos);
    }

    // ─── Registrar ────────────────────────────────────────────────────────────
    @PostMapping("/register")
    public ResponseEntity<ProductoResponseDTO> crearProducto(@Valid @RequestBody ProductoRequestDTO productoRequestDTO) {
        log.info("📥 [SOLICITUD] Crear producto: {}", productoRequestDTO.getNombre());

        ProductoResponseDTO response = productoService.crearProducto(productoRequestDTO);

        log.info("📤 [RESPUESTA] Producto creado: {} (codigo: {})", response.getNombre(), response.getCodigo());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ─── Buscar por codigo ────────────────────────────────────────────────────
    @GetMapping("/codigo")
    public ResponseEntity<ProductoResponseDTO> obtenerProductoPorCodigo(@RequestParam("codigo") String codigo) {
        log.info("📥 [SOLICITUD] Buscar producto por codigo: {}", codigo);

        ProductoResponseDTO producto = productoService.obtenerProductoPorCodigo(codigo);

        log.info("📤 [RESPUESTA] Producto encontrado: {}", codigo);

        return ResponseEntity.ok(producto);
    }

    // ─── Buscar por nombre ────────────────────────────────────────────────────
    @GetMapping("/nombre")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorNombre(@RequestParam("nombre") String nombre) {
        log.info("📥 [SOLICITUD] Buscar producto por nombre: {}", nombre);

        List<ProductoResponseDTO> productos = productoService.obtenerProductoPorNombre(nombre);

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    // ─── Buscar por referencia ────────────────────────────────────────────────
    @GetMapping("/referencia")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorReferencia(@RequestParam("referencia") String referencia) {
        log.info("📥 [SOLICITUD] Buscar producto por referencia: {}", referencia);

        List<ProductoResponseDTO> productos = productoService.obtenerProductoPorReferencia(referencia);

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    // ─── Buscar por descripcion ───────────────────────────────────────────────
    @GetMapping("/descripcion")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorDescripcion(@RequestParam("descripcion") String descripcion) {
        log.info("📥 [SOLICITUD] Buscar producto por descripcion: {}", descripcion);

        List<ProductoResponseDTO> productos = productoService.obtenerProductoPorDescripcion(descripcion);

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    // ─── Buscar por marca ─────────────────────────────────────────────────────
    @GetMapping("/marca")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorMarca(@RequestParam("marca") String marca) {
        log.info("📥 [SOLICITUD] Buscar producto por marca: {}", marca);

        List<ProductoResponseDTO> productos = productoService.obtenerProductoPorMarca(marca);

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    // ─── Buscar por unidad de medida ──────────────────────────────────────────
    @GetMapping("/unidadMedida")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorUnidadDeMedida(@RequestParam("unidadMedida") String unidadMedida) {
        log.info("📥 [SOLICITUD] Buscar producto por unidadMedida: {}", unidadMedida);

        List<ProductoResponseDTO> productos = productoService.obtenerProductoPorUnidadDeMedida(unidadMedida);

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    // ─── Buscar por cantidad ──────────────────────────────────────────────────
    @GetMapping("/cantidad")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorCantidad(@RequestParam("cantidad") Long cantidad) {
        log.info("📥 [SOLICITUD] Buscar producto por cantidad: {}", cantidad);

        List<ProductoResponseDTO> productos = productoService.obtenerProductoPorCantidad(cantidad);

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    // ─── Buscar por precio ────────────────────────────────────────────────────
    @GetMapping("/precio")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorPrecio(@RequestParam("precio") BigDecimal precio) {
        log.info("📥 [SOLICITUD] Buscar producto por precio: {}", precio);

        List<ProductoResponseDTO> productos = productoService.obtenerProductoPorPrecio(precio);

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    // ─── Buscar por proveedorId ───────────────────────────────────────────────
    @GetMapping("/proveedorId")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorProveedorId(@RequestParam("proveedorId") Long proveedorId) {
        log.info("📥 [SOLICITUD] Buscar producto por proveedorId: {}", proveedorId);

        List<ProductoResponseDTO> productos = productoService.obtenerProductoPorProveedorId(proveedorId);

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    // ─── Buscar por proveedorName ─────────────────────────────────────────────
    @GetMapping("/proveedorName")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorProveedorName(@RequestParam("proveedorName") String proveedorName) {
        log.info("📥 [SOLICITUD] Buscar producto por proveedorName: {}", proveedorName);

        List<ProductoResponseDTO> productos = productoService.obtenerProductoPorProveedorName(proveedorName);

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    // ─── Buscar por creadoPor ─────────────────────────────────────────────────
    @GetMapping("/creadoPor")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorCreadoPor(@RequestParam("creadoPor") String creadoPor) {
        log.info("📥 [SOLICITUD] Buscar producto por creadoPor: {}", creadoPor);

        List<ProductoResponseDTO> productos = productoService.obtenerProductoPorCreadoPor(creadoPor);

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    // ─── Buscar por fecha de creacion ─────────────────────────────────────────
    @GetMapping("/fechaCreacion")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorFechaCreacion(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) {
        List<ProductoResponseDTO> productos = productoService.obtenerProductoPorFechaCreacion(fechaInicio, fechaFin);
        log.info("📥 [SOLICITUD] Buscar producto por fechaCreacion: fechaInicio: {}, fechaFin: {}", fechaInicio, fechaFin);

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    // ─── Buscar por fecha de actualizacion ───────────────────────────────────
    @GetMapping("/fechaActualizacion")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorFechaActualizacion(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) {
        List<ProductoResponseDTO> productos = productoService.obtenerProductoPorFechaActualizacion(fechaInicio, fechaFin);
        log.info("📥 [SOLICITUD] Buscar producto por de fechaActualizacion: fechaInicio: {}, fechaFin: {}", fechaInicio, fechaFin);

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    // ─── Actualizar ───────────────────────────────────────────────────────────
    @PutMapping("/update")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(@Valid @RequestBody ProductoRequestDTO productoRequestDTO) {
        log.info("📥 [SOLICITUD] Actualizar producto con codigo: {}", productoRequestDTO.getCodigo());

        ProductoResponseDTO response = productoService.actualizarProducto(productoRequestDTO);

        log.info("📤 [RESPUESTA] Producto actualizado: {}", response.getCodigo());

        return ResponseEntity.ok(response);
    }

    // ─── Soft delete (enviar a papelera) ──────────────────────────────────────
    @DeleteMapping("/delete")
    public ResponseEntity<Void> eliminarProducto(
            @RequestParam("codigo") String codigo,
            @RequestParam("eliminadoPorId") String eliminadoPorId,
            @RequestParam("eliminadoPorNombre") String eliminadoPorNombre) {

        log.info("📥 [SOLICITUD] Enviar a papelera producto con codigo: {}", codigo);

        productoService.eliminarProducto(codigo, eliminadoPorId, eliminadoPorNombre);

        log.info("📤 [RESPUESTA] Producto {} enviado a papelera por: {}", codigo, eliminadoPorNombre);

        return ResponseEntity.ok().build();
    }

    // ─── Listar papelera ──────────────────────────────────────────────────────
    @GetMapping("/trash")
    public ResponseEntity<List<ProductoPapeleraResponseDTO>> listarPapelera() {
        log.info("📥 [SOLICITUD] Listar productos en papelera");

        List<ProductoPapeleraResponseDTO> papelera = productoService.listarPapelera();

        if (papelera.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} productos en papelera", papelera.size());

        return ResponseEntity.ok(papelera);
    }

    // ─── Restaurar desde papelera ─────────────────────────────────────────────
    @PutMapping("/restore")
    public ResponseEntity<ProductoResponseDTO> restaurarProducto(@RequestParam("codigo") String codigo) {
        log.info("📥 [SOLICITUD] Restaurar producto con codigo: {}", codigo);

        ProductoResponseDTO response = productoService.restaurarProducto(codigo);

        log.info("📤 [RESPUESTA] Producto restaurado: {}", codigo);

        return ResponseEntity.ok(response);
    }

    // ─── Eliminar definitivamente ─────────────────────────────────────────────
    @DeleteMapping("/permanent-delete")
    public ResponseEntity<Void> eliminarDefinitivo(@RequestParam("codigo") String codigo) {
        log.info("📥 [SOLICITUD] Eliminar definitivamente producto con codigo: {}", codigo);

        productoService.eliminarDefinitivo(codigo);

        log.info("📤 [RESPUESTA] Producto {} eliminado definitivamente", codigo);

        return ResponseEntity.ok().build();
    }

    // ─── Stock ────────────────────────────────────────────────────────────────
    @PutMapping("/restar-stock/{codigo}")
    public ResponseEntity<ProductoResponseDTO> restarStock(
            @PathVariable String codigo,
            @RequestParam int cantidad) {
        log.info("📥 [SOLICITUD] [STOCK] Restar {} unidades al producto con codigo: {}", cantidad, codigo);

        ProductoResponseDTO response = productoService.restarStock(codigo, cantidad);

        log.info("📤 [RESPUESTA] [STOCK] Stock actualizado para el producto con codigo: {}", response.getCodigo());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/devolver-stock/{codigo}")
    public ResponseEntity<ProductoResponseDTO> devolverStock(
            @PathVariable String codigo,
            @RequestParam int cantidad) {
        log.info("📥 [SOLICITUD] [STOCK] Devolver {} unidades al producto con codigo: {}", cantidad, codigo);

        ProductoResponseDTO response = productoService.devolverStock(codigo, cantidad);

        log.info("📤 [RESPUESTA] [STOCK] Stock devuelto para el producto con codigo: {}", response.getCodigo());

        return ResponseEntity.ok(response);
    }

    // ─── Filtrar papelera por fecha de eliminacion ────────────────────────────
    @GetMapping("/trash/fecha")
    public ResponseEntity<List<ProductoPapeleraResponseDTO>> listarPapeleraPorFecha(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) {

        List<ProductoPapeleraResponseDTO> resultado = productoService.listarPapeleraPorFecha(fechaInicio, fechaFin);

        if (resultado.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(resultado);
    }

    // ─── Filtrar papelera por quien elimino ───────────────────────────────────
    @GetMapping("/trash/eliminadoPor")
    public ResponseEntity<List<ProductoPapeleraResponseDTO>> listarPapeleraPorEliminadoPor(
            @RequestParam("eliminadoPorId") String eliminadoPorId) {

        List<ProductoPapeleraResponseDTO> resultado = productoService.listarPapeleraPorEliminadoPor(eliminadoPorId);

        if (resultado.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(resultado);
    }
}
