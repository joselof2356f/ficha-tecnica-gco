package com.FichaTecnica.Gco.controller;

import com.FichaTecnica.Gco.model.*;
import com.FichaTecnica.Gco.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Permite peticiones desde el frontend HTML
public class LoyaltyController {

    @Autowired private TipoIdentificacionRepository tipoIdRepo;
    @Autowired private PaisRepository paisRepo;
    @Autowired private DepartamentoRepository deptoRepo;
    @Autowired private CiudadRepository ciudadRepo;
    @Autowired private MarcaRepository marcaRepo;
    @Autowired private ClienteRepository clienteRepo;

    @GetMapping("/tipos-identificacion")
    public List<TipoIdentificacion> getTiposIdentificacion() { return tipoIdRepo.findAll(); }

    @GetMapping("/paises")
    public List<Pais> getPaises() { return paisRepo.findAll(); }

    @GetMapping("/departamentos/{paisId}")
    public List<Departamento> getDepartamentos(@PathVariable Long paisId) { 
        return deptoRepo.findByPaisId(paisId); 
    }

    @GetMapping("/ciudades/{deptoId}")
    public List<Ciudad> getCiudades(@PathVariable Long deptoId) { 
        return ciudadRepo.findByDepartamentoId(deptoId); 
    }

    @GetMapping("/marcas")
    public List<Marca> getMarcas() { return marcaRepo.findAll(); }

    @PostMapping("/clientes")
    public ResponseEntity<Cliente> registrarCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteRepo.save(cliente);
        return ResponseEntity.ok(nuevoCliente);
    }
}

