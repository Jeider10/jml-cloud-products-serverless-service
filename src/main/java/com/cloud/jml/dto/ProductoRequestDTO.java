package com.cloud.jml.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentoss
public class ProductoRequestDTO {

    private Long codigo;
    private String nombre;
    private String referencia;
    private String descripcion;
    private String marca;
    private String unidadMedida;
    private Long cantidad;
    private Long precio;
    private Long proveedorId;
    private String proveedorName;
}
