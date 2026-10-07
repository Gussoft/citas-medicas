package com.hospital.paciente.dto;

import jakarta.validation.Valid;
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
public class PacienteRequestDTO {

    @NotBlank(message = "El numero de documento es obligatorio")
    private String numeroDocumento;

    @NotBlank(message = "Los nombres son obligatorios")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    private String genero;
    private String telefono;
    private String direccion;
    private String estado;

    @Valid
    private SeguroTrabajoDTO seguroTrabajo;

}
