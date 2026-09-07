package com.retail.fidelidad.repository;

import com.retail.fidelidad.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
}
