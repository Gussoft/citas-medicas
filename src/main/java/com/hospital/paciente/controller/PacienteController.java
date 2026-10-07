package com.hospital.paciente.controller;

import com.hospital.paciente.dto.PacienteRequestDTO;
import com.hospital.paciente.dto.PacienteResponseDTO;
import com.hospital.paciente.dto.ValidacionSeguroDTO;
import com.hospital.paciente.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> registrarPaciente(@Valid @RequestBody PacienteRequestDTO request) {
        PacienteResponseDTO response = pacienteService.registrarPaciente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarPacientes() {
        List<PacienteResponseDTO> lista = pacienteService.listarPacientes();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> obtenerPorId(@PathVariable Long id) {
        PacienteResponseDTO response = pacienteService.obtenerPacientePorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<PacienteResponseDTO> obtenerPorDocumento(@PathVariable String documento) {
        PacienteResponseDTO response = pacienteService.obtenerPacientePorDocumento(documento);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/seguro/validar")
    public ResponseEntity<ValidacionSeguroDTO> validarSeguro(@PathVariable Long id) {
        ValidacionSeguroDTO validacion = pacienteService.validarSeguroPaciente(id);
        return ResponseEntity.ok(validacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> actualizarPaciente(
            @PathVariable Long id,
            @Valid @RequestBody PacienteRequestDTO request) {
        PacienteResponseDTO response = pacienteService.actualizarPaciente(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }

}
