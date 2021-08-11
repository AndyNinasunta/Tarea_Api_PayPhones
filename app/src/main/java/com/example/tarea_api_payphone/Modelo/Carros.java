package com.example.tarea_api_payphone.Modelo;

public class Carros {

    private String descripcion;
    private double costo;
    private int iva;

    public Carros() {
    }

    public Carros(String descripcion, double costo, int iva) {
        this.descripcion = descripcion;
        this.costo = costo;
        this.iva = iva;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }
}
