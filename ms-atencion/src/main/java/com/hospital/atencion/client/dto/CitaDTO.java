package com.hospital.atencion.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaDTO {

    private Long id;
    private Long pacienteId;
    private Long doctorId;
    private Long especialidadId;
    private LocalDateTime fechaHora;
    private String estado;
    private String motivo;

}
