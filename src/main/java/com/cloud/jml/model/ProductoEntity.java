package com.cloud.jml.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "productos")
public class ProductoEntity {

    @Id
    @Column(nullable = false)
    private Long codigo;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;
    private Long cantidad;
    private Long precio;

    @Column(nullable = false)
    private Long proveedorId;

    @Column(nullable = false)
    private String proveedorName;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}
