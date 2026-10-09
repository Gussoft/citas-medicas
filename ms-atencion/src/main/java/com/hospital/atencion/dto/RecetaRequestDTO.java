package com.hospital.atencion.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecetaRequestDTO {

    private String indicacionesGenerales;

    @NotEmpty(message = "La receta debe contener al menos un medicamento")
    @Valid
    private List<DetalleRecetaRequestDTO> detalles;

}
