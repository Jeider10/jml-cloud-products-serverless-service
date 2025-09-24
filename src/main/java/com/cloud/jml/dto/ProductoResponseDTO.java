package com.cloud.jml.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
public class ProductoResponseDTO {

    private Long codigo;
    private String nombre;
    private String descripcion;
    private Long cantidad;
    private Long precio;
    private Long proveedorId;
    private String proveedorName;
    private String fechaCreacion;
    private String fechaActualizacion;
}
