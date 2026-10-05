package com.cloud.jml.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos")
public class ProductoEntity {

    @Id
    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    private String referencia;
    private String descripcion;
    private String marca;
    private String unidadMedida;

    @Column(nullable = false)
    private Long cantidad;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal precio;

    // Precio de costo/compra — base para calcular ganancia bruta. Opcional; null = no registrado
    @Column(name = "precio_costo", precision = 15, scale = 2)
    private BigDecimal precioCosto;

    // Proveedor es obligatorio — todo producto debe tener proveedor asignado
    @Column(nullable = false)
    private String proveedorId;

    @Column(nullable = false)
    private String proveedorName;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "creado_por", length = 150)
    private String creadoPor;

    @Column(name = "actualizado_por", length = 150)
    private String actualizadoPor;

    // ─── Soft delete (papelera) ───────────────────────────────────────────────
    @Column(name = "eliminado", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean eliminado = false;

    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    @Column(name = "eliminado_por_id", length = 150)
    private String eliminadoPorId;

    @Column(name = "eliminado_por_nombre", length = 200)
    private String eliminadoPorNombre;
}
