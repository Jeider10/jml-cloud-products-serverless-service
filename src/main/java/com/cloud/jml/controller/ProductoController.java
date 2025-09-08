package com.cloud.jml.controller;

import com.cloud.jml.dto.ProductoRequestDTO;
import com.cloud.jml.dto.ProductoResponseDTO;
import com.cloud.jml.exception.ProductoNoEncontradoException;
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
    public ResponseEntity<ProductoResponseDTO> crearProducto(@RequestBody ProductoRequestDTO productoRequestDTO) {
        log.info("📌 Iniciando petición para crear Producto: {}", productoRequestDTO.getNombre());

        ProductoResponseDTO crearProductoResponse = productoService.crearProducto(productoRequestDTO);

        log.info("📌 Finaliza petición para crear Producto: {}", productoRequestDTO.getNombre());

        return ResponseEntity.ok(crearProductoResponse);
    }

    @GetMapping("/listar-productos")
    public ResponseEntity<List<ProductoResponseDTO>> listarProductos() {
        log.info("📌 Iniciando petición para listar todos los Productos");

        List<ProductoResponseDTO> productos = productoService.listarProductos();

        log.info("📌 Finaliza petición para listar todos los Productos");

        return ResponseEntity.ok(productos);
    }

    @GetMapping("/codigo")
    public ResponseEntity<ProductoResponseDTO> obtenerProductoPorCodigo(@RequestParam("codigo") Long codigo) {
        log.info("📌 Iniciando petición para buscar Producto por código: {}", codigo);

        Optional<ProductoResponseDTO> codigoResponse = productoService.obtenerProductoPorCodigo(codigo);

        ResponseEntity<ProductoResponseDTO> productoCodigoResponse;

        if (codigoResponse.isPresent()) {
            productoCodigoResponse = ResponseEntity.ok(codigoResponse.get());
            log.info("📌 Finaliza petición de buscar Producto por código: {}", codigo);
        } else {
            productoCodigoResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            log.warn("⚠️ Producto no encontrado con código: {}", codigo);
        }

        log.info("📌 Finaliza petición de buscar Producto por código: {}", codigo);

        return productoCodigoResponse;
    }

    @GetMapping("/nombre")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorNombre(@RequestParam("nombre") String nombre) {
        log.info("📌 Iniciando petición para buscar Producto por nombre: {}", nombre);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setNombre(nombre);

        List<ProductoResponseDTO> nombreResponse = productoService.obtenerProductoPorNombre(productoRequestDTO);

        ResponseEntity<List<ProductoResponseDTO>> productoNombreResponse;

        if (nombreResponse.isEmpty()) {
            productoNombreResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            log.warn("⚠️ Producto no encontrado con nombre: {}", nombre);
        } else {
            productoNombreResponse = ResponseEntity.ok(nombreResponse);
            log.info("✅ Productos encontrados con nombre: {}. Total: {}", nombre, nombreResponse.size());
        }

        log.info("📌 Finaliza petición de buscar Producto por nombre: {}", nombre);

        return productoNombreResponse;
    }

    @GetMapping("/descripcion")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorDescripcion(@RequestParam("descripcion") String descripcion) {
        log.info("📌 Iniciando petición para buscar Producto por descripcion: {}", descripcion);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setDescripcion(descripcion);

        List<ProductoResponseDTO> descripcionResponse = productoService.obtenerProductoPorDescripcion(productoRequestDTO);

        ResponseEntity<List<ProductoResponseDTO>> productoDescripcionResponse;

        if (descripcionResponse.isEmpty()) {
            productoDescripcionResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            log.warn("⚠️ Producto no encontrado con descripcion: {}", descripcion);
        } else {
            productoDescripcionResponse = ResponseEntity.ok(descripcionResponse);
            log.info("✅ Productos encontrados con descripcion: {}. Total: {}", descripcion, descripcionResponse.size());
        }

        log.info("📌 Finaliza petición de buscar Producto por descripcion: {}", descripcion);

        return productoDescripcionResponse;
    }

    @GetMapping("/cantidad")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorCantidad(@RequestParam("cantidad") String cantidad) {
        log.info("📌 Iniciando petición para buscar Producto por cantidad: {}", cantidad);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setCantidad(cantidad);

        List<ProductoResponseDTO> cantidadResponse = productoService.obtenerProductoPorCantidad(productoRequestDTO);

        ResponseEntity<List<ProductoResponseDTO>> productoCantidadResponse;

        if (cantidadResponse.isEmpty()) {
            productoCantidadResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            log.warn("⚠️ Producto no encontrado con cantidad: {}", cantidad);
        } else {
            productoCantidadResponse = ResponseEntity.ok(cantidadResponse);
            log.info("✅ Productos encontrados con cantidad: {}. Total: {}", cantidad, cantidadResponse.size());
        }

        log.info("📌 Finaliza petición de buscar Producto por cantidad: {}", cantidad);

        return productoCantidadResponse;
    }

    @GetMapping("/precio")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorPrecio(@RequestParam("precio") String precio) {
        log.info("📌 Iniciando petición para buscar Producto por precio: {}", precio);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setPrecio(precio);

        List<ProductoResponseDTO> precioResponse = productoService.obtenerProductoPorPrecio(productoRequestDTO);

        ResponseEntity<List<ProductoResponseDTO>> productoPrecioResponse;

        if (precioResponse.isEmpty()) {
            productoPrecioResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            log.warn("⚠️ Producto no encontrado con precio: {}", precio);
        } else {
            productoPrecioResponse = ResponseEntity.ok(precioResponse);
            log.info("✅ Productos encontrados con precio: {}. Total: {}", precio, precioResponse.size());
        }

        log.info("📌 Finaliza petición de buscar Producto por precio: {}", precio);

        return productoPrecioResponse;
    }

    @GetMapping("/proveedorId")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorProveedorId(@RequestParam("proveedorId") Long proveedorId) {
        log.info("📌 Iniciando petición para buscar Producto por proveedorId: {}", proveedorId);

        ProductoRequestDTO productoProveedorIdRequest = new ProductoRequestDTO();
        productoProveedorIdRequest.setProveedorId(proveedorId);

        List<ProductoResponseDTO> proveedorIdResponse = productoService.obtenerProductoPorProveedorId(productoProveedorIdRequest);

        ResponseEntity<List<ProductoResponseDTO>> productoProveedorIdResponse;

        if (proveedorIdResponse.isEmpty()) {
            productoProveedorIdResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            log.warn("⚠️ Producto no encontrado con proveedorId: {}", proveedorId);
        } else {
            productoProveedorIdResponse = ResponseEntity.ok(proveedorIdResponse);
            log.info("✅ Productos encontrados con proveedorId: {}. Total: {}", proveedorId, proveedorIdResponse.size());
        }

        log.info("📌 Finaliza petición de buscar Producto por proveedorId: {}", proveedorId);

        return productoProveedorIdResponse;
    }

    @GetMapping("/proveedorName")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorProveedorName(@RequestParam("proveedorName") String proveedorName) {
        log.info("📌 Iniciando petición para buscar Producto por proveedorName: {}", proveedorName);

        ProductoRequestDTO productoProveedorNameRequest = new ProductoRequestDTO();
        productoProveedorNameRequest.setProveedorName(proveedorName);

        List<ProductoResponseDTO> proveedorNameResponse = productoService.obtenerProductoPorProveedorName(productoProveedorNameRequest);

        ResponseEntity<List<ProductoResponseDTO>> productoProveedorNameResponse;

        if (proveedorNameResponse.isEmpty()) {
            productoProveedorNameResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            log.warn("⚠️ Producto no encontrado con proveedorName: {}", proveedorName);
        } else {
            productoProveedorNameResponse = ResponseEntity.ok(proveedorNameResponse);
            log.info("✅ Productos encontrados con proveedorName: {}. Total: {}", proveedorName, proveedorNameResponse.size());
        }

        log.info("📌 Finaliza petición de buscar Producto por proveedorName: {}", proveedorName);

        return productoProveedorNameResponse;
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(@RequestBody ProductoRequestDTO productoRequestDTO) {
        log.info("📌 Iniciando petición para actualizar Producto con codigo: {}", productoRequestDTO.getCodigo());

        ProductoResponseDTO response;

        try {
            response = productoService.actualizarProducto(productoRequestDTO);
            log.info("📌 Finaliza petición de actualización de Producto con codigo: {}", productoRequestDTO.getCodigo());
            return ResponseEntity.ok(response);
        } catch (ProductoNoEncontradoException ex) {
            log.warn("⚠️ No se pudo actualizar el Producto. Código no encontrado: {}", productoRequestDTO.getCodigo(), ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            log.error("❌ Error al actualizar Producto: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/eliminar-codigo")
    public ResponseEntity<Void> eliminarProducto(@RequestParam("codigo") Long codigo) {
        log.info("📌 Iniciando petición para eliminar Producto con codigo: {}", codigo);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setCodigo(codigo);

        try {
            productoService.eliminarProducto(productoRequestDTO);
            log.info("📌 Finalizó petición de eliminación de Producto con codigo: {}", productoRequestDTO.getCodigo());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.warn("⚠️ Error al eliminar Producto: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
