package com.hospital.paciente.repository;

import com.hospital.paciente.entity.SeguroTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeguroTrabajoRepository extends JpaRepository<SeguroTrabajo, Long> {

    Optional<SeguroTrabajo> findByPacienteId(Long pacienteId);

}
