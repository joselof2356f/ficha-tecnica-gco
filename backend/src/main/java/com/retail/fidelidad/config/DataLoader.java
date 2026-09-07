package com.retail.fidelidad.config;

import com.retail.fidelidad.model.*;
import com.retail.fidelidad.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Carga datos iniciales (semilla) en la base de datos H2 en memoria
 * cada vez que arranca la aplicación. Se usa CommandLineRunner en lugar
 * de data.sql para poder encadenar correctamente las relaciones (FKs)
 * generadas automáticamente por Hibernate.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final TipoIdentificacionRepository tipoIdentificacionRepository;
    private final MarcaRepository marcaRepository;
    private final PaisRepository paisRepository;
    private final DepartamentoRepository departamentoRepository;
    private final CiudadRepository ciudadRepository;

    public DataLoader(TipoIdentificacionRepository tipoIdentificacionRepository,
                       MarcaRepository marcaRepository,
                       PaisRepository paisRepository,
                       DepartamentoRepository departamentoRepository,
                       CiudadRepository ciudadRepository) {
        this.tipoIdentificacionRepository = tipoIdentificacionRepository;
        this.marcaRepository = marcaRepository;
        this.paisRepository = paisRepository;
        this.departamentoRepository = departamentoRepository;
        this.ciudadRepository = ciudadRepository;
    }

    @Override
    public void run(String... args) {
        cargarTiposIdentificacion();
        cargarMarcas();
        cargarUbicaciones();
        System.out.println(">>> Datos iniciales cargados correctamente en H2 <<<");
    }

    private void cargarTiposIdentificacion() {
        tipoIdentificacionRepository.save(new TipoIdentificacion("CC", "Cédula de Ciudadanía"));
        tipoIdentificacionRepository.save(new TipoIdentificacion("CE", "Cédula de Extranjería"));
        tipoIdentificacionRepository.save(new TipoIdentificacion("TI", "Tarjeta de Identidad"));
        tipoIdentificacionRepository.save(new TipoIdentificacion("PA", "Pasaporte"));
        tipoIdentificacionRepository.save(new TipoIdentificacion("NIT", "NIT"));
    }

    private void cargarMarcas() {
        marcaRepository.save(new Marca("Americanino"));
        marcaRepository.save(new Marca("American Eagle"));
        marcaRepository.save(new Marca("Chevignon"));
        marcaRepository.save(new Marca("Esprit"));
        marcaRepository.save(new Marca("Naf Naf"));
        marcaRepository.save(new Marca("Rifle"));
    }

    private void cargarUbicaciones() {
        // 1 País
        Pais colombia = paisRepository.save(new Pais("Colombia"));

        // 2 Departamentos asociados a ese país
        Departamento antioquia = departamentoRepository.save(new Departamento("Antioquia", colombia));
        Departamento cundinamarca = departamentoRepository.save(new Departamento("Cundinamarca", colombia));

        // 2 Ciudades por cada departamento
        ciudadRepository.save(new Ciudad("Medellín", antioquia));
        ciudadRepository.save(new Ciudad("Envigado", antioquia));

        ciudadRepository.save(new Ciudad("Bogotá", cundinamarca));
        ciudadRepository.save(new Ciudad("Soacha", cundinamarca));
    }
}
