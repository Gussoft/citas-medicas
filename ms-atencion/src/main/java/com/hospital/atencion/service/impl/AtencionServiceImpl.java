package com.hospital.atencion.service.impl;

import com.hospital.atencion.dto.ActualizarAtencionRequestDTO;
import com.hospital.atencion.dto.AtencionResponseDTO;
import com.hospital.atencion.dto.DerivacionRequestDTO;
import com.hospital.atencion.dto.DerivacionResponseDTO;
import com.hospital.atencion.dto.DetalleRecetaRequestDTO;
import com.hospital.atencion.dto.DetalleRecetaResponseDTO;
import com.hospital.atencion.dto.IniciarAtencionRequestDTO;
import com.hospital.atencion.dto.RecetaRequestDTO;
import com.hospital.atencion.dto.RecetaResponseDTO;
import com.hospital.atencion.entity.Atencion;
import com.hospital.atencion.entity.Derivacion;
import com.hospital.atencion.entity.DetalleReceta;
import com.hospital.atencion.entity.Receta;
import com.hospital.atencion.exception.BadRequestException;
import com.hospital.atencion.exception.ResourceNotFoundException;
import com.hospital.atencion.repository.AtencionRepository;
import com.hospital.atencion.repository.DerivacionRepository;
import com.hospital.atencion.repository.RecetaRepository;
import com.hospital.atencion.service.AtencionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AtencionServiceImpl implements AtencionService {

    private final AtencionRepository atencionRepository;
    private final RecetaRepository recetaRepository;
    private final DerivacionRepository derivacionRepository;

    @Override
    @Transactional
    public AtencionResponseDTO iniciarAtencion(IniciarAtencionRequestDTO request) {
        if (atencionRepository.existsByCitaId(request.getCitaId())) {
            throw new BadRequestException("Ya existe un registro de atencion para la cita ID: " + request.getCitaId());
        }

        Atencion atencion = Atencion.builder()
                .citaId(request.getCitaId())
                .pacienteId(request.getPacienteId())
                .doctorId(request.getDoctorId())
                .fechaAtencion(LocalDateTime.now())
                .diagnostico(request.getDiagnostico())
                .observaciones(request.getObservaciones())
                .estado("EN_PROCESO")
                .build();

        Atencion guardada = atencionRepository.save(atencion);
        return mapearAtencionADTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public AtencionResponseDTO obtenerAtencionPorId(Long id) {
        Atencion atencion = atencionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atencion no encontrada con ID: " + id));
        return mapearAtencionADTO(atencion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AtencionResponseDTO> listarTodas() {
        return atencionRepository.findAll().stream()
                .map(this::mapearAtencionADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AtencionResponseDTO actualizarAtencion(Long id, ActualizarAtencionRequestDTO request) {
        Atencion atencion = atencionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atencion no encontrada con ID: " + id));

        atencion.setDiagnostico(request.getDiagnostico());
        if (request.getObservaciones() != null) {
            atencion.setObservaciones(request.getObservaciones());
        }
        if (request.getEstado() != null && !request.getEstado().isBlank()) {
            atencion.setEstado(request.getEstado().toUpperCase());
        }

        Atencion actualizada = atencionRepository.save(atencion);
        return mapearAtencionADTO(actualizada);
    }

    @Override
    @Transactional
    public RecetaResponseDTO emitirReceta(Long atencionId, RecetaRequestDTO request) {
        Atencion atencion = atencionRepository.findById(atencionId)
                .orElseThrow(() -> new ResourceNotFoundException("Atencion no encontrada con ID: " + atencionId));

        Receta receta = Receta.builder()
                .atencion(atencion)
                .fechaEmision(LocalDateTime.now())
                .indicacionesGenerales(request.getIndicacionesGenerales())
                .estado("EMITIDA")
                .detalles(new ArrayList<>())
                .build();

        if (request.getDetalles() != null) {
            for (DetalleRecetaRequestDTO dDTO : request.getDetalles()) {
                DetalleReceta detalle = DetalleReceta.builder()
                        .receta(receta)
                        .medicamento(dDTO.getMedicamento())
                        .dosis(dDTO.getDosis())
                        .frecuencia(dDTO.getFrecuencia())
                        .duracion(dDTO.getDuracion())
                        .cantidad(dDTO.getCantidad())
                        .build();
                receta.getDetalles().add(detalle);
            }
        }

        Receta guardada = recetaRepository.save(receta);
        return mapearRecetaADTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecetaResponseDTO> listarRecetasPorAtencion(Long atencionId) {
        if (!atencionRepository.existsById(atencionId)) {
            throw new ResourceNotFoundException("Atencion no encontrada con ID: " + atencionId);
        }
        return recetaRepository.findByAtencionId(atencionId).stream()
                .map(this::mapearRecetaADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DerivacionResponseDTO crearDerivacion(Long atencionId, DerivacionRequestDTO request) {
        Atencion atencion = atencionRepository.findById(atencionId)
                .orElseThrow(() -> new ResourceNotFoundException("Atencion no encontrada con ID: " + atencionId));

        String prioridad = (request.getPrioridad() != null && !request.getPrioridad().isBlank())
                ? request.getPrioridad().toUpperCase()
                : "NORMAL";

        Derivacion derivacion = Derivacion.builder()
                .atencion(atencion)
                .areaDestino(request.getAreaDestino())
                .motivo(request.getMotivo())
                .prioridad(prioridad)
                .estado("PENDIENTE")
                .fechaDerivacion(LocalDateTime.now())
                .build();

        Derivacion guardada = derivacionRepository.save(derivacion);
        return mapearDerivacionADTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DerivacionResponseDTO> listarDerivacionesPorAtencion(Long atencionId) {
        if (!atencionRepository.existsById(atencionId)) {
            throw new ResourceNotFoundException("Atencion no encontrada con ID: " + atencionId);
        }
        return derivacionRepository.findByAtencionId(atencionId).stream()
                .map(this::mapearDerivacionADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AtencionResponseDTO> listarHistorialPorPaciente(Long pacienteId) {
        return atencionRepository.findByPacienteIdOrderByFechaAtencionDesc(pacienteId).stream()
                .map(this::mapearAtencionADTO)
                .collect(Collectors.toList());
    }

    // === Metodos auxiliares de mapeo ===

    private AtencionResponseDTO mapearAtencionADTO(Atencion a) {
        List<RecetaResponseDTO> recetasDTO = a.getRecetas() != null
                ? a.getRecetas().stream().map(this::mapearRecetaADTO).collect(Collectors.toList())
                : Collections.emptyList();

        List<DerivacionResponseDTO> derivacionesDTO = a.getDerivaciones() != null
                ? a.getDerivaciones().stream().map(this::mapearDerivacionADTO).collect(Collectors.toList())
                : Collections.emptyList();

        return AtencionResponseDTO.builder()
                .id(a.getId())
                .citaId(a.getCitaId())
                .pacienteId(a.getPacienteId())
                .doctorId(a.getDoctorId())
                .fechaAtencion(a.getFechaAtencion())
                .diagnostico(a.getDiagnostico())
                .observaciones(a.getObservaciones())
                .estado(a.getEstado())
                .recetas(recetasDTO)
                .derivaciones(derivacionesDTO)
                .build();
    }

    private RecetaResponseDTO mapearRecetaADTO(Receta r) {
        List<DetalleRecetaResponseDTO> detallesDTO = r.getDetalles() != null
                ? r.getDetalles().stream().map(d -> DetalleRecetaResponseDTO.builder()
                        .id(d.getId())
                        .medicamento(d.getMedicamento())
                        .dosis(d.getDosis())
                        .frecuencia(d.getFrecuencia())
                        .duracion(d.getDuracion())
                        .cantidad(d.getCantidad())
                        .build()).collect(Collectors.toList())
                : Collections.emptyList();

        return RecetaResponseDTO.builder()
                .id(r.getId())
                .atencionId(r.getAtencion() != null ? r.getAtencion().getId() : null)
                .fechaEmision(r.getFechaEmision())
                .indicacionesGenerales(r.getIndicacionesGenerales())
                .estado(r.getEstado())
                .detalles(detallesDTO)
                .build();
    }

    private DerivacionResponseDTO mapearDerivacionADTO(Derivacion d) {
        return DerivacionResponseDTO.builder()
                .id(d.getId())
                .atencionId(d.getAtencion() != null ? d.getAtencion().getId() : null)
                .areaDestino(d.getAreaDestino())
                .motivo(d.getMotivo())
                .prioridad(d.getPrioridad())
                .estado(d.getEstado())
                .fechaDerivacion(d.getFechaDerivacion())
                .build();
    }

}
