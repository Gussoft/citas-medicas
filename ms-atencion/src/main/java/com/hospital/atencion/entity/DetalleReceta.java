package com.hospital.atencion.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalles_receta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receta_id", nullable = false)
    @JsonBackReference
    private Receta receta;

    @Column(nullable = false, length = 150)
    private String medicamento;

    @Column(nullable = false, length = 100)
    private String dosis; // Ej: 500mg

    @Column(nullable = false, length = 100)
    private String frecuencia; // Ej: Cada 8 horas

    @Column(nullable = false, length = 100)
    private String duracion; // Ej: 7 dias

    @Column(nullable = false)
    private Integer cantidad;

}
