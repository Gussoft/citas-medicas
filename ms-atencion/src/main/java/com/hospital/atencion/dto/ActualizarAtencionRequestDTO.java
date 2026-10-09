package com.hospital.atencion.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActualizarAtencionRequestDTO {

    @NotBlank(message = "El diagnostico no puede estar vacio")
    private String diagnostico;

    private String observaciones;

    private String estado; // EN_PROCESO, FINALIZADA

}
