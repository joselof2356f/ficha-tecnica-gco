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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TipoIdentificacion getTipoIdentificacion() { return tipoIdentificacion; }
    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) { this.tipoIdentificacion = tipoIdentificacion; }
    public String getNumeroIdentificacion() { return numeroIdentificacion; }
    public void setNumeroIdentificacion(String numeroIdentificacion) { this.numeroIdentificacion = numeroIdentificacion; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public Pais getPais() { return pais; }
    public void setPais(Pais pais) { this.pais = pais; }
    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }
    public Ciudad getCiudad() { return ciudad; }
    public void setCiudad(Ciudad ciudad) { this.ciudad = ciudad; }
    public Marca getMarca() { return marca; }
    public void setMarca(Marca marca) { this.marca = marca; }
}


