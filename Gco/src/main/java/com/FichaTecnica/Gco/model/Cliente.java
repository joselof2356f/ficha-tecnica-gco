package com.FichaTecnica.Gco.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "tipo_identificacion_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TipoIdentificacion tipoIdentificacion;
    
    private String numeroIdentificacion;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String direccion;
    
    @ManyToOne
    @JoinColumn(name = "pais_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Pais pais;
    
    @ManyToOne
    @JoinColumn(name = "departamento_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Departamento departamento;
    
    @ManyToOne
    @JoinColumn(name = "ciudad_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Ciudad ciudad;
    
    @ManyToOne
    @JoinColumn(name = "marca_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Marca marca;
}


