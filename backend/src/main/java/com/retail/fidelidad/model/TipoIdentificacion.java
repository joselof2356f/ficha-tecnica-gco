package com.retail.fidelidad.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_identificacion")
public class TipoIdentificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String codigo; // Ej: CC, CE, TI, PA

    @Column(nullable = false, length = 60)
    private String nombre; // Ej: Cédula de Ciudadanía

    public TipoIdentificacion() {
    }

    public TipoIdentificacion(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
