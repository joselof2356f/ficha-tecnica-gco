package com.FichaTecnica.Gco.repository;

import com.FichaTecnica.Gco.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {}

