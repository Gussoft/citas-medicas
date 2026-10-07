package com.hospital.paciente.service;

import com.hospital.paciente.dto.PacienteRequestDTO;
import com.hospital.paciente.dto.PacienteResponseDTO;
import com.hospital.paciente.dto.ValidacionSeguroDTO;

import java.util.List;

public interface PacienteService {

    PacienteResponseDTO registrarPaciente(PacienteRequestDTO request);

    List<PacienteResponseDTO> listarPacientes();

    PacienteResponseDTO obtenerPacientePorId(Long id);

    PacienteResponseDTO obtenerPacientePorDocumento(String documento);

    ValidacionSeguroDTO validarSeguroPaciente(Long id);

    PacienteResponseDTO actualizarPaciente(Long id, PacienteRequestDTO request);

    void eliminarPaciente(Long id);

}
