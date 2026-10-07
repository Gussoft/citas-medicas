package com.hospital.paciente.service.impl;

import com.hospital.paciente.dto.PacienteRequestDTO;
import com.hospital.paciente.dto.PacienteResponseDTO;
import com.hospital.paciente.dto.SeguroTrabajoDTO;
import com.hospital.paciente.dto.ValidacionSeguroDTO;
import com.hospital.paciente.entity.Paciente;
import com.hospital.paciente.entity.SeguroTrabajo;
import com.hospital.paciente.exception.BadRequestException;
import com.hospital.paciente.exception.ResourceNotFoundException;
import com.hospital.paciente.repository.PacienteRepository;
import com.hospital.paciente.repository.SeguroTrabajoRepository;
import com.hospital.paciente.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final SeguroTrabajoRepository seguroTrabajoRepository;

    @Override
    @Transactional
    public PacienteResponseDTO registrarPaciente(PacienteRequestDTO request) {
        if (pacienteRepository.existsByNumeroDocumento(request.getNumeroDocumento())) {
            throw new BadRequestException("Ya existe un paciente con el numero de documento: " + request.getNumeroDocumento());
        }

        Paciente paciente = Paciente.builder()
                .numeroDocumento(request.getNumeroDocumento())
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .fechaNacimiento(request.getFechaNacimiento())
                .genero(request.getGenero())
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .estado(request.getEstado() != null ? request.getEstado() : "ACTIVO")
                .build();

        if (request.getSeguroTrabajo() != null) {
            SeguroTrabajoDTO seguroDTO = request.getSeguroTrabajo();
            SeguroTrabajo seguro = SeguroTrabajo.builder()
                    .paciente(paciente)
                    .numeroAfiliacion(seguroDTO.getNumeroAfiliacion())
                    .empresa(seguroDTO.getEmpresa())
                    .tipoSeguro(seguroDTO.getTipoSeguro())
                    .fechaVigenciaInicio(seguroDTO.getFechaVigenciaInicio())
                    .fechaVigenciaFin(seguroDTO.getFechaVigenciaFin())
                    .estado(seguroDTO.getEstado() != null ? seguroDTO.getEstado() : "APTO")
                    .build();
            paciente.setSeguroTrabajo(seguro);
        }

        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        return mapearAResponseDTO(pacienteGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> listarPacientes() {
        return pacienteRepository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteResponseDTO obtenerPacientePorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));
        return mapearAResponseDTO(paciente);
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteResponseDTO obtenerPacientePorDocumento(String documento) {
        Paciente paciente = pacienteRepository.findByNumeroDocumento(documento)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con documento: " + documento));
        return mapearAResponseDTO(paciente);
    }

    @Override
    @Transactional(readOnly = true)
    public ValidacionSeguroDTO validarSeguroPaciente(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));

        String nombreCompleto = paciente.getNombres() + " " + paciente.getApellidos();

        if (!"ACTIVO".equalsIgnoreCase(paciente.getEstado())) {
            return ValidacionSeguroDTO.builder()
                    .pacienteId(paciente.getId())
                    .numeroDocumento(paciente.getNumeroDocumento())
                    .pacienteNombreCompleto(nombreCompleto)
                    .apto(false)
                    .motivo("El paciente se encuentra en estado " + paciente.getEstado())
                    .build();
        }

        SeguroTrabajo seguro = paciente.getSeguroTrabajo();
        if (seguro == null) {
            seguro = seguroTrabajoRepository.findByPacienteId(paciente.getId()).orElse(null);
        }

        if (seguro == null) {
            return ValidacionSeguroDTO.builder()
                    .pacienteId(paciente.getId())
                    .numeroDocumento(paciente.getNumeroDocumento())
                    .pacienteNombreCompleto(nombreCompleto)
                    .apto(false)
                    .motivo("El paciente no cuenta con un seguro de trabajo registrado")
                    .build();
        }

        LocalDate hoy = LocalDate.now();
        boolean dentroDeVigencia = !hoy.isBefore(seguro.getFechaVigenciaInicio()) && !hoy.isAfter(seguro.getFechaVigenciaFin());
        boolean estadoApto = "APTO".equalsIgnoreCase(seguro.getEstado());

        if (!dentroDeVigencia) {
            return ValidacionSeguroDTO.builder()
                    .pacienteId(paciente.getId())
                    .numeroDocumento(paciente.getNumeroDocumento())
                    .pacienteNombreCompleto(nombreCompleto)
                    .tipoSeguro(seguro.getTipoSeguro())
                    .numeroAfiliacion(seguro.getNumeroAfiliacion())
                    .apto(false)
                    .motivo("El seguro no se encuentra vigente. Rango de vigencia: " + seguro.getFechaVigenciaInicio() + " a " + seguro.getFechaVigenciaFin())
                    .build();
        }

        if (!estadoApto) {
            return ValidacionSeguroDTO.builder()
                    .pacienteId(paciente.getId())
                    .numeroDocumento(paciente.getNumeroDocumento())
                    .pacienteNombreCompleto(nombreCompleto)
                    .tipoSeguro(seguro.getTipoSeguro())
                    .numeroAfiliacion(seguro.getNumeroAfiliacion())
                    .apto(false)
                    .motivo("El seguro se encuentra en estado " + seguro.getEstado())
                    .build();
        }

        return ValidacionSeguroDTO.builder()
                .pacienteId(paciente.getId())
                .numeroDocumento(paciente.getNumeroDocumento())
                .pacienteNombreCompleto(nombreCompleto)
                .tipoSeguro(seguro.getTipoSeguro())
                .numeroAfiliacion(seguro.getNumeroAfiliacion())
                .apto(true)
                .motivo("Seguro " + seguro.getTipoSeguro() + " vigente y en estado APTO")
                .build();
    }

    @Override
    @Transactional
    public PacienteResponseDTO actualizarPaciente(Long id, PacienteRequestDTO request) {
        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));

        if (!pacienteExistente.getNumeroDocumento().equals(request.getNumeroDocumento()) &&
                pacienteRepository.existsByNumeroDocumento(request.getNumeroDocumento())) {
            throw new BadRequestException("Ya existe otro paciente con el documento: " + request.getNumeroDocumento());
        }

        pacienteExistente.setNumeroDocumento(request.getNumeroDocumento());
        pacienteExistente.setNombres(request.getNombres());
        pacienteExistente.setApellidos(request.getApellidos());
        pacienteExistente.setFechaNacimiento(request.getFechaNacimiento());
        pacienteExistente.setGenero(request.getGenero());
        pacienteExistente.setTelefono(request.getTelefono());
        pacienteExistente.setDireccion(request.getDireccion());
        if (request.getEstado() != null) {
            pacienteExistente.setEstado(request.getEstado());
        }

        if (request.getSeguroTrabajo() != null) {
            SeguroTrabajoDTO seguroDTO = request.getSeguroTrabajo();
            SeguroTrabajo seguro = pacienteExistente.getSeguroTrabajo();
            if (seguro == null) {
                seguro = new SeguroTrabajo();
                seguro.setPaciente(pacienteExistente);
                pacienteExistente.setSeguroTrabajo(seguro);
            }
            seguro.setNumeroAfiliacion(seguroDTO.getNumeroAfiliacion());
            seguro.setEmpresa(seguroDTO.getEmpresa());
            seguro.setTipoSeguro(seguroDTO.getTipoSeguro());
            seguro.setFechaVigenciaInicio(seguroDTO.getFechaVigenciaInicio());
            seguro.setFechaVigenciaFin(seguroDTO.getFechaVigenciaFin());
            if (seguroDTO.getEstado() != null) {
                seguro.setEstado(seguroDTO.getEstado());
            }
        }

        Paciente pacienteActualizado = pacienteRepository.save(pacienteExistente);
        return mapearAResponseDTO(pacienteActualizado);
    }

    @Override
    @Transactional
    public void eliminarPaciente(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));
        pacienteRepository.delete(paciente);
    }

    private PacienteResponseDTO mapearAResponseDTO(Paciente paciente) {
        SeguroTrabajoDTO seguroDTO = null;
        if (paciente.getSeguroTrabajo() != null) {
            SeguroTrabajo s = paciente.getSeguroTrabajo();
            seguroDTO = SeguroTrabajoDTO.builder()
                    .id(s.getId())
                    .numeroAfiliacion(s.getNumeroAfiliacion())
                    .empresa(s.getEmpresa())
                    .tipoSeguro(s.getTipoSeguro())
                    .fechaVigenciaInicio(s.getFechaVigenciaInicio())
                    .fechaVigenciaFin(s.getFechaVigenciaFin())
                    .estado(s.getEstado())
                    .build();
        }

        return PacienteResponseDTO.builder()
                .id(paciente.getId())
                .numeroDocumento(paciente.getNumeroDocumento())
                .nombres(paciente.getNombres())
                .apellidos(paciente.getApellidos())
                .fechaNacimiento(paciente.getFechaNacimiento())
                .genero(paciente.getGenero())
                .telefono(paciente.getTelefono())
                .direccion(paciente.getDireccion())
                .estado(paciente.getEstado())
                .seguroTrabajo(seguroDTO)
                .build();
    }

}
