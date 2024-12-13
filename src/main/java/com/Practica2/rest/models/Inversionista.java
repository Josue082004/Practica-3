package com.Practica2.rest.models;

public class Inversionista {

    private Integer idInversionista;
    private String nombre;
    private String apellido;
    private String correo;

    public Inversionista() {
    }

    public Inversionista(Integer idInversionista ,String nombre, String apellido, String correo) {
        this.idInversionista = idInversionista;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
    }

    public Integer getIdInversionista() {
        return this.idInversionista;
    }

    public void setIdInversionista(Integer idInversionista) {
        this.idInversionista = idInversionista;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}

