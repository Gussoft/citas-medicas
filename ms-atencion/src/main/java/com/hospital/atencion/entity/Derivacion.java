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

import java.time.LocalDateTime;

@Entity
@Table(name = "derivaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Derivacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atencion_id", nullable = false)
    @JsonBackReference
    private Atencion atencion;

    @Column(name = "area_destino", nullable = false, length = 100)
    private String areaDestino; // Laboratorio, Rayos X, otra especialidad

    @Column(nullable = false, columnDefinition = "TEXT")
    private String motivo;

    @Column(nullable = false, length = 30)
    private String prioridad; // NORMAL, URGENTE

    @Column(nullable = false, length = 30)
    private String estado; // PENDIENTE, ATENDIDA

    @Column(name = "fecha_derivacion", nullable = false)
    private LocalDateTime fechaDerivacion;

}
