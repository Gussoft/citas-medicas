package com.hospital.atencion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleRecetaResponseDTO {

    private Long id;
    private String medicamento;
    private String dosis;
    private String frecuencia;
    private String duracion;
    private Integer cantidad;

}
