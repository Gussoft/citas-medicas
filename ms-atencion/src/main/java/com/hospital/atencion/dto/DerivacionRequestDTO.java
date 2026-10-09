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
public class DerivacionRequestDTO {

    @NotBlank(message = "El area de destino es obligatoria (ej: Laboratorio, Rayos X, Cardiologia)")
    private String areaDestino;

    @NotBlank(message = "El motivo de la derivacion es obligatorio")
    private String motivo;

    private String prioridad; // NORMAL, URGENTE

}
