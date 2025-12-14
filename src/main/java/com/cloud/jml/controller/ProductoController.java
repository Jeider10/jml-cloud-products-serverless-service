package com.cloud.jml.controller;

import com.cloud.jml.dto.ProductoRequestDTO;
import com.cloud.jml.dto.ProductoResponseDTO;
import com.cloud.jml.service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        log.info("📥 [SOLICITUD] Listar todos los productos.");

        List<ProductoResponseDTO> productos = productoService.listarProductos();

        if (productos == null || productos.isEmpty()) {
            log.warn("⚠️ [RESPUESTA] No se encontraron productos registrados.");
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} productos.", productos.size());

        return ResponseEntity.ok(productos);
    }

    @PostMapping("/register")
    public ResponseEntity<ProductoResponseDTO> crearProducto(@RequestBody ProductoRequestDTO productoRequestDTO) {
        log.info("📥 [SOLICITUD] Crear producto: {}", productoRequestDTO.getNombre());

        ProductoResponseDTO crearProductoResponse = productoService.crearProducto(productoRequestDTO);

        if (crearProductoResponse == null || crearProductoResponse.getCodigo() == null) {
            log.warn("⚠️ [RESPUESTA] No se pudo crear el producto: {}", productoRequestDTO.getNombre());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        log.info("📤 [RESPUESTA] Producto creado exitosamente: {} (código: {}).", crearProductoResponse.getNombre(), crearProductoResponse.getCodigo());

        return ResponseEntity.status(HttpStatus.CREATED).body(crearProductoResponse);
    }

    @GetMapping("/codigo")
    public ResponseEntity<ProductoResponseDTO> obtenerProductoPorCodigo(@RequestParam("codigo") Long codigo) {
        log.info("📥 [SOLICITUD] Buscar producto por código: {}", codigo);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setCodigo(codigo);

        ProductoResponseDTO productoCodigo = productoService.obtenerProductoPorCodigo(productoRequestDTO);

        if (productoCodigo == null) {
            log.warn("⚠️ [RESPUESTA] Producto no encontrado con código: {}", codigo);
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Producto encontrado: {} (código: {}).", productoCodigo.getNombre(), productoCodigo.getCodigo());

        return ResponseEntity.ok(productoCodigo);
    }

    @GetMapping("/nombre")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorNombre(@RequestParam("nombre") String nombre) {
        log.info("📥 [SOLICITUD] Buscar productos por nombre: {}", nombre);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setNombre(nombre);

        List<ProductoResponseDTO> productosNombre = productoService.obtenerProductoPorNombre(productoRequestDTO);

        if (productosNombre == null || productosNombre.isEmpty()) {
            log.warn("⚠️ [RESPUESTA] No se encontraron productos con nombre: {}", nombre);
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} productos con nombre: {}", productosNombre.size(), nombre);

        return ResponseEntity.ok(productosNombre);
    }

    @GetMapping("/referencia")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorReferencia(@RequestParam("referencia") String referencia) {
        log.info("📥 [SOLICITUD] Buscar productos por referencia: {}", referencia);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setReferencia(referencia);

        List<ProductoResponseDTO> productosReferencia = productoService.obtenerProductoPorReferencia(productoRequestDTO);

        if (productosReferencia == null || productosReferencia.isEmpty()) {
            log.warn("⚠️ [RESPUESTA] No se encontraron productos con referencia: {}", referencia);
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} productos con referencia: {}", productosReferencia.size(), referencia);

        return ResponseEntity.ok(productosReferencia);
    }

    @GetMapping("/descripcion")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorDescripcion(@RequestParam("descripcion") String descripcion) {
        log.info("📥 [SOLICITUD] Buscar productos por descripción: {}", descripcion);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setDescripcion(descripcion);

        List<ProductoResponseDTO> productosDescripcion = productoService.obtenerProductoPorDescripcion(productoRequestDTO);

        if (productosDescripcion == null || productosDescripcion.isEmpty()) {
            log.warn("⚠️ [RESPUESTA] No se encontraron productos con descripción: {}", descripcion);
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} productos con descripción: {}", productosDescripcion.size(), descripcion);

        return ResponseEntity.ok(productosDescripcion);
    }

    @GetMapping("/marca")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorMarca(@RequestParam("marca") String marca) {
        log.info("📥 [SOLICITUD] Buscar productos por marca: {}", marca);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setMarca(marca);

        List<ProductoResponseDTO> productosMarca = productoService.obtenerProductoPorMarca(productoRequestDTO);

        if (productosMarca == null || productosMarca.isEmpty()) {
            log.warn("⚠️ [RESPUESTA] No se encontraron productos con marca: {}", marca);
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} productos con marca: {}", productosMarca.size(), marca);

        return ResponseEntity.ok(productosMarca);
    }

    @GetMapping("/unidadMedida")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorUnidadDeMedida(@RequestParam("unidadMedida") String unidadMedida) {
        log.info("📥 [SOLICITUD] Buscar productos por unidad de medida: {}", unidadMedida);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setUnidadMedida(unidadMedida);

        List<ProductoResponseDTO> productosUnidadDeMedida = productoService.obtenerProductoPorUnidadDeMedida(productoRequestDTO);

        if (productosUnidadDeMedida == null || productosUnidadDeMedida.isEmpty()) {
            log.warn("⚠️ [RESPUESTA] No se encontraron productos con unidad de medida: {}", unidadMedida);
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} productos con unidad de medida: {}", productosUnidadDeMedida.size(), unidadMedida);

        return ResponseEntity.ok(productosUnidadDeMedida);
    }

    @GetMapping("/cantidad")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorCantidad(@RequestParam("cantidad") Long cantidad) {
        log.info("📥 [SOLICITUD] Buscar productos por cantidad: {}", cantidad);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setCantidad(cantidad);

        List<ProductoResponseDTO> productosCantidad = productoService.obtenerProductoPorCantidad(productoRequestDTO);

        if (productosCantidad == null || productosCantidad.isEmpty()) {
            log.warn("⚠️ [RESPUESTA] No se encontraron productos con cantidad: {}", cantidad);
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} productos con cantidad: {}", productosCantidad.size(), cantidad);

        return ResponseEntity.ok(productosCantidad);
    }

    @GetMapping("/precio")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorPrecio(@RequestParam("precio") Long precio) {
        log.info("📥 [SOLICITUD] Buscar productos por precio: {}", precio);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setPrecio(precio);

        List<ProductoResponseDTO> productosPrecio = productoService.obtenerProductoPorPrecio(productoRequestDTO);

        if (productosPrecio == null || productosPrecio.isEmpty()) {
            log.warn("⚠️ [RESPUESTA] No se encontraron productos con precio: {}", precio);
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} productos con precio: {}", productosPrecio.size(), precio);

        return ResponseEntity.ok(productosPrecio);
    }

    @GetMapping("/proveedorId")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorProveedorId(@RequestParam("proveedorId") Long proveedorId) {
        log.info("📥 [SOLICITUD] Buscar productos por proveedorId: {}", proveedorId);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setProveedorId(proveedorId);

        List<ProductoResponseDTO> productosProveedorId = productoService.obtenerProductoPorProveedorId(productoRequestDTO);

        if (productosProveedorId == null || productosProveedorId.isEmpty()) {
            log.warn("⚠️ [RESPUESTA] No se encontraron productos con proveedorId: {}", proveedorId);
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} productos con proveedorId: {}", productosProveedorId.size(), proveedorId);

        return ResponseEntity.ok(productosProveedorId);
    }

    @GetMapping("/proveedorName")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorProveedorName(@RequestParam("proveedorName") String proveedorName) {
        log.info("📥 [SOLICITUD] Buscar productos por proveedorName: {}", proveedorName);

        ProductoRequestDTO productoRequestDTO = new ProductoRequestDTO();
        productoRequestDTO.setProveedorName(proveedorName);

        List<ProductoResponseDTO> productosProveedorName = productoService.obtenerProductoPorProveedorName(productoRequestDTO);

        if (productosProveedorName == null || productosProveedorName.isEmpty()) {
            log.warn("⚠️ [RESPUESTA] No se encontraron productos con proveedorName: {}", proveedorName);
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} productos con proveedorName: {}", productosProveedorName.size(), proveedorName);

        return ResponseEntity.ok(productosProveedorName);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(@RequestBody ProductoRequestDTO productoRequestDTO) {
        log.info("📥 [SOLICITUD] Actualizar producto con código: {}", productoRequestDTO.getCodigo());

        ProductoResponseDTO productoResponseDTO = productoService.actualizarProducto(productoRequestDTO);

        if (productoResponseDTO == null || productoResponseDTO.getCodigo() == null) {
            log.warn("⚠️ [RESPUESTA] No se pudo actualizar el producto con código: {}", productoRequestDTO.getCodigo());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        log.info("📤 [RESPUESTA] Producto actualizado correctamente: {} (código: {}).", productoResponseDTO.getNombre(), productoResponseDTO.getCodigo());

        return ResponseEntity.ok(productoResponseDTO);
    }

    @PutMapping("/restar-stock/{codigo}")
    public ResponseEntity<ProductoResponseDTO> restarStock(
            @PathVariable Long codigo,
            @RequestParam int cantidad) {

        log.info("📥 [SOLICITUD] [📦 STOCK] Restar {} unidades al producto con código: {}", cantidad, codigo);

        ProductoResponseDTO productoResponseDTO = productoService.restarStock(codigo, cantidad);

        if (productoResponseDTO == null || productoResponseDTO.getCodigo() == null) {
            log.warn("⚠️ [RESPUESTA] No se pudo restar stock al producto con código: {}", codigo);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        log.info("📤 [RESPUESTA] [📦 STOCK] Stock actualizado correctamente para el producto con código: {}", productoResponseDTO.getCodigo());

        return ResponseEntity.ok(productoResponseDTO);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> eliminarProducto(@RequestParam("codigo") Long codigo) {
        log.info("📥 [SOLICITUD] Eliminar producto con código: {}", codigo);

        ProductoRequestDTO request = new ProductoRequestDTO();
        request.setCodigo(codigo);

        productoService.eliminarProducto(request);

        log.info("📤 [RESPUESTA] Producto eliminado correctamente con código: {}", codigo);

        return ResponseEntity.ok().build();
    }
}
