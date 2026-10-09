package com.hospital.atencion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DerivacionResponseDTO {

    private Long id;
    private Long atencionId;
    private String areaDestino;
    private String motivo;
    private String prioridad;
    private String estado;
    private LocalDateTime fechaDerivacion;

}
