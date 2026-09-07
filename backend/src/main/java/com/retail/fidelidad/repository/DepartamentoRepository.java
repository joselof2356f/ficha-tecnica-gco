package com.retail.fidelidad.repository;

import com.retail.fidelidad.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    List<Departamento> findByPaisIdOrderByNombreAsc(Long paisId);
}
