package com.hospital.atencion.repository;

import com.hospital.atencion.entity.Derivacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DerivacionRepository extends JpaRepository<Derivacion, Long> {

    List<Derivacion> findByAtencionId(Long atencionId);

    List<Derivacion> findByEstado(String estado);

}
