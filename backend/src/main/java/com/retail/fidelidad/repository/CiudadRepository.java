package com.retail.fidelidad.repository;

import com.retail.fidelidad.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

    List<Ciudad> findByDepartamentoIdOrderByNombreAsc(Long departamentoId);
}
