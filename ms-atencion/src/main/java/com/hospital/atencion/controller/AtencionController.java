package com.hospital.atencion.controller;

import com.hospital.atencion.dto.ActualizarAtencionRequestDTO;
import com.hospital.atencion.dto.AtencionResponseDTO;
import com.hospital.atencion.dto.DerivacionRequestDTO;
import com.hospital.atencion.dto.DerivacionResponseDTO;
import com.hospital.atencion.dto.IniciarAtencionRequestDTO;
import com.hospital.atencion.dto.RecetaRequestDTO;
import com.hospital.atencion.dto.RecetaResponseDTO;
import com.hospital.atencion.service.AtencionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/atenciones")
@RequiredArgsConstructor
public class AtencionController {

    private final AtencionService atencionService;

    @PostMapping
    public ResponseEntity<AtencionResponseDTO> iniciarAtencion(
            @Valid @RequestBody IniciarAtencionRequestDTO request) {
        AtencionResponseDTO response = atencionService.iniciarAtencion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AtencionResponseDTO>> listarTodas() {
        List<AtencionResponseDTO> lista = atencionService.listarTodas();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtencionResponseDTO> obtenerPorId(@PathVariable Long id) {
        AtencionResponseDTO response = atencionService.obtenerAtencionPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtencionResponseDTO> actualizarAtencion(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarAtencionRequestDTO request) {
        AtencionResponseDTO response = atencionService.actualizarAtencion(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/recetas")
    public ResponseEntity<RecetaResponseDTO> emitirReceta(
            @PathVariable Long id,
            @Valid @RequestBody RecetaRequestDTO request) {
        RecetaResponseDTO response = atencionService.emitirReceta(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}/recetas")
    public ResponseEntity<List<RecetaResponseDTO>> listarRecetas(@PathVariable Long id) {
        List<RecetaResponseDTO> recetas = atencionService.listarRecetasPorAtencion(id);
        return ResponseEntity.ok(recetas);
    }

    @PostMapping("/{id}/derivaciones")
    public ResponseEntity<DerivacionResponseDTO> crearDerivacion(
            @PathVariable Long id,
            @Valid @RequestBody DerivacionRequestDTO request) {
        DerivacionResponseDTO response = atencionService.crearDerivacion(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}/derivaciones")
    public ResponseEntity<List<DerivacionResponseDTO>> listarDerivaciones(@PathVariable Long id) {
        List<DerivacionResponseDTO> derivaciones = atencionService.listarDerivacionesPorAtencion(id);
        return ResponseEntity.ok(derivaciones);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<AtencionResponseDTO>> listarHistorialPorPaciente(
            @PathVariable Long pacienteId) {
        List<AtencionResponseDTO> historial = atencionService.listarHistorialPorPaciente(pacienteId);
        return ResponseEntity.ok(historial);
    }

}
