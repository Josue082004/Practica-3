package com.Practica2.rest.models;

public class Proyecto {
    private Integer idProyecto;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private String estado;
    private double costo;
    private double produccionDiaria;
    private double inversionTotal;

    public Proyecto() {
    }

    public Proyecto(Integer idProyecto, String nombre, String fechaInicio, String fechaFin, String estado, double costo, double produccionDiaria, double inversionTotal) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.costo = costo;
        this.produccionDiaria = produccionDiaria;
        this.inversionTotal = inversionTotal;
    }
    
    public Integer getIdProyecto() {
        return this.idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getCosto() {
        return this.costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getProduccionDiaria() {
        return this.produccionDiaria;
    }

    public void setProduccionDiaria(double produccionDiaria) {
        this.produccionDiaria = produccionDiaria;
    }

    public double getInversionTotal() {
        return this.inversionTotal;
    }

    public void setInversionTotal(double inversionTotal) {
        this.inversionTotal = inversionTotal;
    }
}