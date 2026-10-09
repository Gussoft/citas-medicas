package com.hospital.atencion.client;

import com.hospital.atencion.client.dto.CitaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-citas", url = "${services.citas.url:http://localhost:8082}", fallback = CitaClientFallback.class)
public interface CitaClient {

    @GetMapping("/api/citas/{id}")
    CitaDTO obtenerCitaPorId(@PathVariable("id") Long id);

    @PatchMapping("/api/citas/{id}/estado")
    CitaDTO cambiarEstadoCita(@PathVariable("id") Long id, @RequestParam("estado") String estado);

}
