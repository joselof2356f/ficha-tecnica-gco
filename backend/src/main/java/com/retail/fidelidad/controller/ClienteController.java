package com.retail.fidelidad.controller;

import com.retail.fidelidad.dto.ClienteRequestDTO;
import com.retail.fidelidad.dto.ClienteResponseDTO;
import com.retail.fidelidad.model.*;
import com.retail.fidelidad.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller encargado del registro de Clientes al programa de fidelidad.
 */
@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

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

    /**
     * Registra un nuevo cliente en el programa de fidelidad.
     * POST /api/clientes
     */
    @PostMapping
    public ResponseEntity<?> registrarCliente(@Valid @RequestBody ClienteRequestDTO request) {

        // Validar que el número de identificación no esté ya registrado
        if (clienteRepository.existsByNumeroIdentificacion(request.getNumeroIdentificacion())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Ya existe un cliente registrado con ese número de identificación"));
        }

        // Resolver las entidades relacionadas a partir de los IDs recibidos
        TipoIdentificacion tipoIdentificacion = tipoIdentificacionRepository.findById(request.getTipoIdentificacionId())
                .orElse(null);
        Pais pais = paisRepository.findById(request.getPaisId()).orElse(null);
        Departamento departamento = departamentoRepository.findById(request.getDepartamentoId()).orElse(null);
        Ciudad ciudad = ciudadRepository.findById(request.getCiudadId()).orElse(null);
        Marca marca = marcaRepository.findById(request.getMarcaId()).orElse(null);

        Map<String, String> errores = new HashMap<>();
        if (tipoIdentificacion == null) errores.put("tipoIdentificacionId", "Tipo de identificación no encontrado");
        if (pais == null) errores.put("paisId", "País no encontrado");
        if (departamento == null) errores.put("departamentoId", "Departamento no encontrado");
        if (ciudad == null) errores.put("ciudadId", "Ciudad no encontrada");
        if (marca == null) errores.put("marcaId", "Marca no encontrada");

        if (!errores.isEmpty()) {
            return ResponseEntity.badRequest().body(errores);
        }

        // Construir y guardar la entidad Cliente
        Cliente cliente = new Cliente();
        cliente.setTipoIdentificacion(tipoIdentificacion);
        cliente.setNumeroIdentificacion(request.getNumeroIdentificacion());
        cliente.setNombres(request.getNombres());
        cliente.setApellidos(request.getApellidos());
        cliente.setFechaNacimiento(request.getFechaNacimiento());
        cliente.setDireccion(request.getDireccion());
        cliente.setPais(pais);
        cliente.setDepartamento(departamento);
        cliente.setCiudad(ciudad);
        cliente.setMarca(marca);

        Cliente clienteGuardado = clienteRepository.save(cliente);

        ClienteResponseDTO response = new ClienteResponseDTO(
                clienteGuardado.getId(),
                "¡Registro exitoso! Bienvenido(a) al programa de fidelidad.",
                clienteGuardado.getNombres() + " " + clienteGuardado.getApellidos(),
                clienteGuardado.getMarca().getNombre()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Lista todos los clientes registrados (útil para pruebas/administración).
     * GET /api/clientes
     */
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    /**
     * Manejo simple de errores de validación (@Valid) para devolver
     * mensajes legibles al Frontend en lugar de un stacktrace.
     */
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarErroresValidacion(
            org.springframework.web.bind.MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errores);
    }
}
