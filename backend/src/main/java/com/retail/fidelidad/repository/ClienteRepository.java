package com.retail.fidelidad.repository;

import com.retail.fidelidad.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByNumeroIdentificacion(String numeroIdentificacion);

    Optional<Cliente> findByNumeroIdentificacion(String numeroIdentificacion);
}
