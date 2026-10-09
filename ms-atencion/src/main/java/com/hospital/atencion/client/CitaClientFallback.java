package com.hospital.atencion.client;

import com.hospital.atencion.client.dto.CitaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CitaClientFallback implements CitaClient {

    @Override
    public CitaDTO obtenerCitaPorId(Long id) {
        log.warn("[Circuit Breaker - Fallback] ms-citas no disponible al consultar cita ID: {}. Se continuara de forma manual.", id);
        return CitaDTO.builder()
                .id(id)
                .estado("DESCONOCIDO (FALLBACK)")
                .motivo("ms-citas no disponible")
                .build();
    }

    @Override
    public CitaDTO cambiarEstadoCita(Long id, String estado) {
        log.warn("[Circuit Breaker - Fallback] ms-citas no disponible al actualizar estado de cita ID: {} a {}. Se continuara la atencion con advertencia.", id, estado);
        return CitaDTO.builder()
                .id(id)
                .estado(estado + " (PENDIENTE DE SINCRONIZAR)")
                .motivo("ms-citas temporalmente fuera de linea")
                .build();
    }

}
