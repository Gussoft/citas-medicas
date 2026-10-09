package com.hospital.atencion.service;

import com.hospital.atencion.dto.ActualizarAtencionRequestDTO;
import com.hospital.atencion.dto.AtencionResponseDTO;
import com.hospital.atencion.dto.DerivacionRequestDTO;
import com.hospital.atencion.dto.DerivacionResponseDTO;
import com.hospital.atencion.dto.IniciarAtencionRequestDTO;
import com.hospital.atencion.dto.RecetaRequestDTO;
import com.hospital.atencion.dto.RecetaResponseDTO;

import java.util.List;

public interface AtencionService {

    AtencionResponseDTO iniciarAtencion(IniciarAtencionRequestDTO request);

    AtencionResponseDTO obtenerAtencionPorId(Long id);

    List<AtencionResponseDTO> listarTodas();

    AtencionResponseDTO actualizarAtencion(Long id, ActualizarAtencionRequestDTO request);

    RecetaResponseDTO emitirReceta(Long atencionId, RecetaRequestDTO request);

    List<RecetaResponseDTO> listarRecetasPorAtencion(Long atencionId);

    DerivacionResponseDTO crearDerivacion(Long atencionId, DerivacionRequestDTO request);

    List<DerivacionResponseDTO> listarDerivacionesPorAtencion(Long atencionId);

    List<AtencionResponseDTO> listarHistorialPorPaciente(Long pacienteId);

}
