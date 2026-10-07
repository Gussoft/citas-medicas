package com.hospital.paciente.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeguroTrabajoDTO {

    private Long id;
    private String numeroAfiliacion;
    private String empresa;

    @NotBlank(message = "El tipo de seguro es obligatorio")
    private String tipoSeguro;

    @NotNull(message = "La fecha de inicio de vigencia es obligatoria")
    private LocalDate fechaVigenciaInicio;

    @NotNull(message = "La fecha de fin de vigencia es obligatoria")
    private LocalDate fechaVigenciaFin;

    @NotBlank(message = "El estado del seguro es obligatorio")
    private String estado;

}
