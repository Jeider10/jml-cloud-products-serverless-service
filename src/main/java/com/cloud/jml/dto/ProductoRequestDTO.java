package com.cloud.jml.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
public class ProductoRequestDTO {

    // FIX: Se agregaron validaciones Jakarta Bean Validation para evitar datos invalidos
    @NotNull(message = "El campo 'codigo' es obligatorio")
    private Long codigo;

    @NotBlank(message = "El campo 'nombre' es obligatorio")
    @Size(max = 100, message = "El campo 'nombre' no puede exceder 100 caracteres")
    private String nombre;

    @Size(max = 100, message = "El campo 'referencia' no puede exceder 100 caracteres")
    private String referencia;

    @Size(max = 200, message = "El campo 'descripcion' no puede exceder 200 caracteres")
    private String descripcion;

    @Size(max = 100, message = "El campo 'marca' no puede exceder 100 caracteres")
    private String marca;

    @Size(max = 50, message = "El campo 'unidadMedida' no puede exceder 50 caracteres")
    private String unidadMedida;

    private Long cantidad;
    private BigDecimal precio;
    private Long proveedorId;

    @Size(max = 100, message = "El campo 'proveedorName' no puede exceder 100 caracteres")
    private String proveedorName;
}
