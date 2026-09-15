package com.cloud.jml.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
public class ProductoPapeleraResponseDTO {

    private String codigo;
    private String nombre;
    private String descripcion;
    private String marca;
    private Long cantidad;
    private BigDecimal precio;
    private String proveedorName;
    private String creadoPor;
    private String fechaCreacion;
    private String fechaEliminacion;
    private String eliminadoPorId;
    private String eliminadoPorNombre;
}
