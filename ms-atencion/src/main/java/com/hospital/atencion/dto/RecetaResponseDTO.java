package com.hospital.atencion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecetaResponseDTO {

    private Long id;
    private Long atencionId;
    private LocalDateTime fechaEmision;
    private String indicacionesGenerales;
    private String estado;
    private List<DetalleRecetaResponseDTO> detalles;

}
