package com.cloud.jml.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentoss
public class ProductoRequestDTO {

    private Long codigo;
    private String nombre;
    private String descripcion;
    private Long cantidad;
    private Long precio;
    private Long proveedorId;
    private String proveedorName;

    // Getters y Setters
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getProveedorName() {
        return proveedorName;
    }

    public void setProveedorName(String proveedorName) {
        this.proveedorName = proveedorName;
    }
}
