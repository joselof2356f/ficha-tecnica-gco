package com.retail.fidelidad.controller;

import com.retail.fidelidad.model.*;
import com.retail.fidelidad.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Expone los catálogos (tablas maestras) que el Frontend consume
 * para llenar los <select> del formulario, incluyendo la lógica
 * en cascada País -> Departamento -> Ciudad.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CatalogoController {

    @Autowired
    private TipoIdentificacionRepository tipoIdentificacionRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping("/tipos-identificacion")
    public List<TipoIdentificacion> listarTiposIdentificacion() {
        return tipoIdentificacionRepository.findAll();
    }

    @GetMapping("/paises")
    public List<Pais> listarPaises() {
        return paisRepository.findAll();
    }

    /**
     * Devuelve los departamentos de un país específico.
     * Ej: GET /api/departamentos?paisId=1
     */
    @GetMapping("/departamentos")
    public List<Departamento> listarDepartamentosPorPais(@RequestParam Long paisId) {
        return departamentoRepository.findByPaisIdOrderByNombreAsc(paisId);
    }

    /**
     * Devuelve las ciudades de un departamento específico.
     * Ej: GET /api/ciudades?departamentoId=1
     */
    @GetMapping("/ciudades")
    public List<Ciudad> listarCiudadesPorDepartamento(@RequestParam Long departamentoId) {
        return ciudadRepository.findByDepartamentoIdOrderByNombreAsc(departamentoId);
    }

    @GetMapping("/marcas")
    public List<Marca> listarMarcas() {
        return marcaRepository.findAll();
    }
}
