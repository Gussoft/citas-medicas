package com.hospital.paciente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidacionSeguroDTO {

    private Long pacienteId;
    private String numeroDocumento;
    private String pacienteNombreCompleto;
    private boolean apto;
    private String motivo;
    private String tipoSeguro;
    private String numeroAfiliacion;

}
