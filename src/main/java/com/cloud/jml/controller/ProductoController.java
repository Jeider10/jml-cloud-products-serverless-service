package com.cloud.jml.controller;

import com.cloud.jml.dto.ProductoDTO;
import com.cloud.jml.model.ProductoEntity;
import com.cloud.jml.service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://localhost:8080")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/register")
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO productoDTO) {
        log.info("📌 Iniciando petición para crear Producto: {}", productoDTO.getNombre());

        ProductoDTO response = productoService.crearProducto(productoDTO);

        log.info("📌 Finaliza petición para crear Producto: {}", productoDTO.getNombre());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/codigo")
    public ResponseEntity<ProductoEntity> obtenerProductoPorCodigo(@RequestParam("codigo") String codigo) {
        log.info("📌 Iniciando petición para buscar Proveedor por codigo: {}", codigo);

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setCodigo(codigo);

        Optional<ProductoEntity> response = productoService.obtenerProductoPorCodigo(productoDTO);

        log.info("📌 Finaliza petición de buscar Proveedor por codigo: {}", codigo);

        return response.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/nombre")
    public ResponseEntity<List<ProductoEntity>> obtenerProductoPorNombre(@RequestParam("nombre") String nombre) {
        log.info("📌 Iniciando petición para buscar Proveedor por nombre: {}", nombre);

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombre(nombre);

        List<ProductoEntity> response = productoService.obtenerProductoPorNombre(productoDTO);

        log.info("📌 Finaliza petición de buscar Producto por nombre: {}", nombre);

        return response.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok(response);
    }

    @GetMapping("/descripcion")
    public ResponseEntity<List<ProductoEntity>> obtenerProductoPorDescripcion(@RequestParam("descripcion") String descripcion) {
        log.info("📌 Iniciando petición para buscar Producto por descripcion: {}", descripcion);

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setDescripcion(descripcion);

        List<ProductoEntity> response = productoService.obtenerProductoPorDescripcion(productoDTO);

        log.info("📌 Finaliza petición de buscar Producto por descripcion: {}", descripcion);

        return response.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok(response);
    }

    @GetMapping("/cantidad")
    public ResponseEntity<List<ProductoEntity>> obtenerProductoPorCantidad(@RequestParam("cantidad") String cantidad) {
        log.info("📌 Iniciando petición para buscar Producto por cantidad: {}", cantidad);

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setCantidad(cantidad);

        List<ProductoEntity> response = productoService.obtenerProductoPorCantidad(productoDTO);

        log.info("📌 Finaliza petición de buscar Producto por cantidad: {}", cantidad);

        return response.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok(response);
    }

    @GetMapping("/precio")
    public ResponseEntity<List<ProductoEntity>> obtenerProductoPorPrecio(@RequestParam("precio") String precio) {
        log.info("📌 Iniciando petición para buscar Producto por precio: {}", precio);

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setPrecio(precio);

        List<ProductoEntity> response = productoService.obtenerProductoPorPrecio(productoDTO);

        log.info("📌 Finaliza petición de buscar Producto por precio: {}", precio);

        return response.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok(response);
    }

    @GetMapping("/proveedor")
    public ResponseEntity<List<ProductoEntity>> obtenerProductoPorProveedor(@RequestParam("proveedor") String proveedor) {
        log.info("📌 Iniciando petición para buscar Producto por proveedor: {}", proveedor);

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setProveedor(proveedor);

        List<ProductoEntity> response = productoService.obtenerProductoPorProveedor(productoDTO);

        log.info("📌 Finaliza petición de buscar Producto por proveedor: {}", proveedor);

        return response.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok(response);
    }

    @GetMapping("/fechaCreacion")
    public ResponseEntity<List<ProductoEntity>> obtenerProductoPorProveedor(@RequestParam("fechaCreacion") LocalDateTime fechaCreacion) {
        log.info("📌 Iniciando petición para buscar Producto por fechaCreacion: {}", fechaCreacion);

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setFechaCreacion(fechaCreacion);

        List<ProductoEntity> response = productoService.obtenerProductoPorProveedor(productoDTO);

        log.info("📌 Finaliza petición de buscar Producto por fechaCreacion: {}", fechaCreacion);

        return response.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok(response);
    }

    @GetMapping("/listar-productos")
    public ResponseEntity<List<ProductoEntity>> listarProductos() {
        log.info("📌 Iniciando petición para listar todos los Productos");

        List<ProductoEntity> productos = productoService.listarProductos();

        log.info("📌 Finaliza petición para listar todos los Productos");

        return ResponseEntity.ok(productos);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ProductoDTO> actualizarProducto(@RequestBody ProductoDTO productoDTO) {
        log.info("📌 Iniciando petición para actualizar Producto con codigo: {}", productoDTO.getCodigo());

        ProductoDTO response = productoService.actualizarProducto(productoDTO);

        if (response == null) {
            log.warn("⚠️ No se pudo actualizar el Producto. Codigo no encontrado: {}", productoDTO.getCodigo());
            return ResponseEntity.notFound().build();
        }

        log.info("📌 Finaliza petición de actualización de Producto con codigo: {}", productoDTO.getCodigo());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminar-codigo")
    public ResponseEntity<Void> eliminarProducto(@RequestParam("codigo") String codigo) {
        log.info("📌 Iniciando petición para eliminar Producto con nic: {}", codigo);

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setCodigo(codigo);

        try {
            productoService.eliminarProducto(productoDTO);
            log.info("📌 Finalizó petición de eliminación de Producto con codigo: {}", productoDTO.getCodigo());
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            log.warn("⚠️ Error al eliminar Producto: {}", e.getMessage());
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
