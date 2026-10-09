package com.hospital.atencion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtencionResponseDTO {

    private Long id;
    private Long citaId;
    private Long pacienteId;
    private Long doctorId;
    private LocalDateTime fechaAtencion;
    private String diagnostico;
    private String observaciones;
    private String estado;
    private List<RecetaResponseDTO> recetas;
    private List<DerivacionResponseDTO> derivaciones;

}
