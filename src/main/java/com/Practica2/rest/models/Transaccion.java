package com.Practica2.rest.models;

public class Transaccion {
    private Integer idTransaccion;
    private String tipo;
    private String fecha;
    private String descripcion;
    private String inversionista;
    public Transaccion() {
    }

    public Transaccion(Integer idTransaccion, String tipo, String fecha, String descripcion, String inversionista) {
        this.idTransaccion = idTransaccion;
        this.tipo = tipo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.inversionista = inversionista;
    }

    public Integer getIdTransaccion() {
        return this.idTransaccion;
    }

    public void setIdTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getInversionista() {
        return this.inversionista;
    }

    public void setInversionista(String inversionista) {
        this.inversionista = inversionista;
    }
}