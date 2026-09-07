package com.retail.fidelidad.dto;

/**
 * DTO de respuesta que se envía al Frontend tras un registro exitoso.
 */
public class ClienteResponseDTO {

    private Long id;
    private String mensaje;
    private String nombreCompleto;
    private String marca;

    public ClienteResponseDTO() {
    }

    public ClienteResponseDTO(Long id, String mensaje, String nombreCompleto, String marca) {
        this.id = id;
        this.mensaje = mensaje;
        this.nombreCompleto = nombreCompleto;
        this.marca = marca;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
