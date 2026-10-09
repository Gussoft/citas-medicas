package com.hospital.atencion.repository;

import com.hospital.atencion.entity.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtencionRepository extends JpaRepository<Atencion, Long> {

    List<Atencion> findByPacienteIdOrderByFechaAtencionDesc(Long pacienteId);

    Optional<Atencion> findByCitaId(Long citaId);

    boolean existsByCitaId(Long citaId);

}
