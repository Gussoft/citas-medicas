package com.hospital.paciente.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "seguros_trabajo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeguroTrabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private Paciente paciente;

    @Column(name = "numero_afiliacion", length = 50)
    private String numeroAfiliacion;

    @Column(length = 150)
    private String empresa;

    @Column(name = "tipo_seguro", nullable = false, length = 50)
    private String tipoSeguro;

    @Column(name = "fecha_vigencia_inicio", nullable = false)
    private LocalDate fechaVigenciaInicio;

    @Column(name = "fecha_vigencia_fin", nullable = false)
    private LocalDate fechaVigenciaFin;

    @Column(nullable = false, length = 30)
    private String estado;

}
