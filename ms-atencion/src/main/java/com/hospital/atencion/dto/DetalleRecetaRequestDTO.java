package com.hospital.atencion.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleRecetaRequestDTO {

    @NotBlank(message = "El nombre del medicamento es obligatorio")
    private String medicamento;

    @NotBlank(message = "La dosis es obligatoria (ej: 500mg)")
    private String dosis;

    @NotBlank(message = "La frecuencia es obligatoria (ej: cada 8 horas)")
    private String frecuencia;

    @NotBlank(message = "La duracion es obligatoria (ej: 7 dias)")
    private String duracion;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad minima a prescribir es 1")
    private Integer cantidad;

}
