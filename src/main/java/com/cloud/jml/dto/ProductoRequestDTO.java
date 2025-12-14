package com.cloud.jml.dto;

import lombok.*;

import java.math.BigDecimal;

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
    private BigDecimal precio;
    private Long proveedorId;
    private String proveedorName;
}
