package com.cloud.jml.controller;

import com.cloud.jml.dto.ProductoRequestDTO;
import com.cloud.jml.dto.ProductoResponseDTO;
import com.cloud.jml.service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/list/all")
    public ResponseEntity<List<ProductoResponseDTO>> listarProductos() {
        log.info("📥 [SOLICITUD] Listar todos los productos");

        List<ProductoResponseDTO> productos = productoService.listarProductos();

        log.info("📤 [RESPUESTA] Se retornan {} productos", productos.size());

        return ResponseEntity.ok(productos);
    }

    @PostMapping("/register")
    public ResponseEntity<ProductoResponseDTO> crearProducto(@RequestBody ProductoRequestDTO productoRequestDTO) {
        log.info("📥 [SOLICITUD] Crear producto: {}", productoRequestDTO.getNombre());

        ProductoResponseDTO crearProductoResponse = productoService.crearProducto(productoRequestDTO);

        log.info("📤 [RESPUESTA] Producto creado: {} con código: {}", crearProductoResponse.getNombre(), crearProductoResponse.getCodigo());

        return ResponseEntity.ok(crearProductoResponse);
    }

    @GetMapping("/codigo")
    public ResponseEntity<ProductoResponseDTO> obtenerProductoPorCodigo(@RequestParam("codigo") Long codigo) {
        log.info("📥 [SOLICITUD] Buscar producto por código: {}", codigo);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setCodigo(codigo);

        ProductoResponseDTO productoCodigo = productoService.obtenerProductoPorCodigo(productoRequestDTO);

        if (productoCodigo == null) {
            log.warn("📤 [RESPUESTA] Producto no encontrado con código: {}", codigo);
            return ResponseEntity.ok().body(null);
        }

        log.info("📤 [RESPUESTA] Producto encontrado con código: {}", productoCodigo.getCodigo());

        return ResponseEntity.ok(productoCodigo);
    }

    @GetMapping("/nombre")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorNombre(@RequestParam("nombre") String nombre) {
        log.info("📥 [SOLICITUD] Buscar producto por nombre: {}", nombre);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setNombre(nombre);

        List<ProductoResponseDTO> productosNombre = productoService.obtenerProductoPorNombre(productoRequestDTO);

        log.info("📤 [RESPUESTA] Se retornan {} productos con nombres: {}", productosNombre.size(), productoRequestDTO.getNombre());

        return ResponseEntity.ok(productosNombre);
    }

    @GetMapping("/descripcion")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorDescripcion(@RequestParam("descripcion") String descripcion) {
        log.info("📥 [SOLICITUD] Buscar producto por descripción: {}", descripcion);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setDescripcion(descripcion);

        List<ProductoResponseDTO> productosDescripcion = productoService.obtenerProductoPorDescripcion(productoRequestDTO);

        log.info("📤 [RESPUESTA] Se retornan {} productos con descripción: {}", productosDescripcion.size(), productoRequestDTO.getDescripcion());

        return ResponseEntity.ok(productosDescripcion);
    }

    @GetMapping("/cantidad")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorCantidad(@RequestParam("cantidad") Long cantidad) {
        log.info("📥 [SOLICITUD] Buscar producto por cantidad: {}", cantidad);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setCantidad(cantidad);

        List<ProductoResponseDTO> productosCantidad = productoService.obtenerProductoPorCantidad(productoRequestDTO);

        log.info("📤 [RESPUESTA] Se retornan {} productos con cantidad: {}", productosCantidad.size(), productoRequestDTO.getCantidad());

        return ResponseEntity.ok(productosCantidad);
    }

    @GetMapping("/precio")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorPrecio(@RequestParam("precio") Long precio) {
        log.info("📥 [SOLICITUD] Buscar producto por precio: {}", precio);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setPrecio(precio);

        List<ProductoResponseDTO> productosPrecio = productoService.obtenerProductoPorPrecio(productoRequestDTO);

        log.info("📤 [RESPUESTA] Se retornan {} productos con precio: {}", productosPrecio.size(), productoRequestDTO.getPrecio());

        return ResponseEntity.ok(productosPrecio);
    }

    @GetMapping("/proveedorId")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorProveedorId(@RequestParam("proveedorId") Long proveedorId) {
        log.info("📥 [SOLICITUD] Buscar producto por proveedorId: {}", proveedorId);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setProveedorId(proveedorId);

        List<ProductoResponseDTO> productosProveedorId = productoService.obtenerProductoPorProveedorId(productoRequestDTO);

        log.info("📤 [RESPUESTA] Se retornan {} productos con proveedorId: {}", productosProveedorId.size(), productoRequestDTO.getProveedorId());

        return ResponseEntity.ok(productosProveedorId);
    }

    @GetMapping("/proveedorName")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorProveedorName(@RequestParam("proveedorName") String proveedorName) {
        log.info("📥 [SOLICITUD] Buscar producto por proveedorName: {}", proveedorName);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setProveedorName(proveedorName);

        List<ProductoResponseDTO> productosProveedorName = productoService.obtenerProductoPorProveedorName(productoRequestDTO);

        log.info("📤 [RESPUESTA] Se retornan {} productos con proveedorName: {}", productosProveedorName.size(), productoRequestDTO.getProveedorName());

        return ResponseEntity.ok(productosProveedorName);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(@RequestBody ProductoRequestDTO productoRequestDTO) {
        log.info("📥 [SOLICITUD] Actualizar producto con código: {}", productoRequestDTO.getCodigo());

        ProductoResponseDTO productoResponseDTO = productoService.actualizarProducto(productoRequestDTO);

        log.info("📤 [RESPUESTA] Producto actualizado correctamente: {} con código: {}", productoRequestDTO.getNombre(), productoRequestDTO.getCodigo());

        return ResponseEntity.ok(productoResponseDTO);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> eliminarProducto(@RequestParam("codigo") Long codigo) {
        log.info("📥 [SOLICITUD] Eliminar producto con código: {}", codigo);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setCodigo(codigo);

        productoService.eliminarProducto(productoRequestDTO);

        log.info("📤 [RESPUESTA] Producto eliminado correctamente con código: {}", codigo);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/restar-stock/{codigo}")
    public ResponseEntity<ProductoResponseDTO> restarStock(
            @PathVariable Long codigo,
            @RequestParam int cantidad) {

        log.info("📤 [RESPUESTA] [📦 STOCK] Restando {} unidades al producto con código: {}", cantidad, codigo);

        ProductoResponseDTO productoResponseDTO = productoService.restarStock(codigo, cantidad);

        log.info("📤 [RESPUESTA] [📦 STOCK] Stock actualizado correctamente para el producto con código: {}", productoResponseDTO.getCodigo());

        return ResponseEntity.ok(productoResponseDTO);
    }
}
