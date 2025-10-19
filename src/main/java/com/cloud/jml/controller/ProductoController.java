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
@CrossOrigin(origins = "http://localhost:8080")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
        log.info("🔥 ProductoController inicializado correctamente.");
    }

    @GetMapping("/listar-productos")
    public ResponseEntity<List<ProductoResponseDTO>> listarProductos() {
        log.info("📥 [SOLICITUD] Listar todos los productos");

        List<ProductoResponseDTO> productos = productoService.listarProductos();

        log.info("📤 [RESPUESTA] Se retornan {} productos", productos.size());

        return ResponseEntity.ok(productos);
    }

    @PostMapping("/register")
    public ResponseEntity<ProductoResponseDTO> crearProducto(@RequestBody ProductoRequestDTO productoRequestDTO) {
        log.info("📝 [PETICIÓN] Crear producto: {}", productoRequestDTO.getNombre());

        ProductoResponseDTO crearProductoResponse = productoService.crearProducto(productoRequestDTO);

        log.info("📤 [RESPUESTA] Producto creado con nombre: {}", productoRequestDTO.getNombre());

        return ResponseEntity.ok(crearProductoResponse);
    }

    @GetMapping("/codigo")
    public ResponseEntity<ProductoResponseDTO> obtenerProductoPorCodigo(@RequestParam("codigo") Long codigo) {
        log.info("🔍 [SOLICITUD] Buscar producto por código: {}", codigo);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setCodigo(codigo);

        ProductoResponseDTO productoCodigo = productoService.obtenerProductoPorCodigo(productoRequestDTO);

        log.info("✅ [RESPUESTA] Producto encontrado con código: {}", codigo);

        return ResponseEntity.ok(productoCodigo);
    }

    @GetMapping("/nombre")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorNombre(@RequestParam("nombre") String nombre) {
        log.info("🔍 [SOLICITUD] Buscar producto por nombre: {}", nombre);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setNombre(nombre);

        List<ProductoResponseDTO> productosNombre = productoService.obtenerProductoPorNombre(productoRequestDTO);

        log.info("✅ [RESPUESTA] Se retornan {} productos con nombres: {}", productosNombre.size(), nombre);

        return ResponseEntity.ok(productosNombre);
    }

    @GetMapping("/descripcion")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorDescripcion(@RequestParam("descripcion") String descripcion) {
        log.info("🔍 [SOLICITUD] Buscar producto por descripción: {}", descripcion);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setDescripcion(descripcion);

        List<ProductoResponseDTO> productosDescripcion = productoService.obtenerProductoPorDescripcion(productoRequestDTO);

        log.info("✅ [RESPUESTA] Se retornan {} productos con descripción: {}", productosDescripcion.size(), descripcion);

        return ResponseEntity.ok(productosDescripcion);
    }

    @GetMapping("/cantidad")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorCantidad(@RequestParam("cantidad") Long cantidad) {
        log.info("🔍 [SOLICITUD] Buscar producto por cantidad: {}", cantidad);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setCantidad(cantidad);

        List<ProductoResponseDTO> productosCantidad = productoService.obtenerProductoPorCantidad(productoRequestDTO);

        log.info("✅ [RESPUESTA] Se retornan {} productos con cantidad: {}", productosCantidad.size(), cantidad);

        return ResponseEntity.ok(productosCantidad);
    }

    @GetMapping("/precio")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorPrecio(@RequestParam("precio") Long precio) {
        log.info("🔍 [SOLICITUD] Buscar producto por precio: {}", precio);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setPrecio(precio);

        List<ProductoResponseDTO> productosPrecio = productoService.obtenerProductoPorPrecio(productoRequestDTO);

        log.info("✅ [RESPUESTA] Se retornan {} productos con precio: {}", productosPrecio.size(), precio);

        return ResponseEntity.ok(productosPrecio);
    }

    @GetMapping("/proveedorId")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorProveedorId(@RequestParam("proveedorId") Long proveedorId) {
        log.info("🔍 [SOLICITUD] Buscar producto por proveedorId: {}", proveedorId);

        ProductoRequestDTO productoProveedorIdRequest = new ProductoRequestDTO();
        productoProveedorIdRequest.setProveedorId(proveedorId);

        List<ProductoResponseDTO> productosProveedorId = productoService.obtenerProductoPorProveedorId(productoProveedorIdRequest);

        log.info("✅ [RESPUESTA] Se retornan {} productos con proveedorId: {}", productosProveedorId.size(), proveedorId);

        return ResponseEntity.ok(productosProveedorId);
    }

    @GetMapping("/proveedorName")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorProveedorName(@RequestParam("proveedorName") String proveedorName) {
        log.info("🔍 [SOLICITUD] Buscar producto por proveedorName: {}", proveedorName);

        ProductoRequestDTO productoProveedorNameRequest = new ProductoRequestDTO();
        productoProveedorNameRequest.setProveedorName(proveedorName);

        List<ProductoResponseDTO> productosProveedorName = productoService.obtenerProductoPorProveedorName(productoProveedorNameRequest);

        log.info("✅ [RESPUESTA] Se retornan {} productos con proveedorName: {}", productosProveedorName.size(), proveedorName);

        return ResponseEntity.ok(productosProveedorName);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(@RequestBody ProductoRequestDTO productoRequestDTO) {
        log.info("📝 [PETICIÓN] Actualizar producto con código: {}", productoRequestDTO.getCodigo());

        ProductoResponseDTO productoResponseDTO = productoService.actualizarProducto(productoRequestDTO);

        log.info("✅ [RESPUESTA] Producto actualizado correctamente: {} con código: {}", productoRequestDTO.getNombre(), productoRequestDTO.getCodigo());

        return ResponseEntity.ok(productoResponseDTO);
    }

    @DeleteMapping("/eliminar-codigo")
    public ResponseEntity<Void> eliminarProducto(@RequestParam("codigo") Long codigo) {
        log.info("🗑️ [PETICIÓN] Eliminar producto con código: {}", codigo);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setCodigo(codigo);

        productoService.eliminarProducto(productoRequestDTO);

        log.info("✅ [RESPUESTA] Producto eliminado correctamente con código: {}", codigo);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{codigo}/restar-stock")
    public ResponseEntity<ProductoResponseDTO> restarStock(
            @PathVariable Long codigo,
            @RequestParam int cantidad) {
        log.info("📦 [STOCK] Restando {} unidades al producto con código: {}", cantidad, codigo);

        ProductoResponseDTO productoResponseDTO = productoService.restarStock(codigo, cantidad);

        log.info("✅ [STOCK] Stock actualizado correctamente para el producto con código: {}", codigo);

        return ResponseEntity.ok(productoResponseDTO);

//        try {
//            ProductoResponseDTO response = productoService.restarStock(codigo, cantidad);
//            return ResponseEntity.ok(response);
//        } catch (ProductoNoEncontradoException ex) {
//            log.warn("⚠️ Producto no encontrado con código: {}", codigo, ex);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        } catch (IllegalArgumentException ex) {
//            log.warn("⚠️ Stock insuficiente para producto con código: {}", codigo, ex);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        } catch (Exception ex) {
//            log.error("❌ Error al restar stock del producto: {}", ex.getMessage(), ex);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
    }

}
