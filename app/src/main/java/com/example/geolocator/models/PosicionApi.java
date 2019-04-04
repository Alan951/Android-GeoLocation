package com.example.geolocator.models;

import java.time.LocalDateTime;

public class PosicionApi {

    private Long idPosicion;
    private VendedorAmbulante vendedor;
    private Coordenada coordenada;
    private LocalDateTime fechaGen;
    private LocalDateTime fechaReg;

    public PosicionApi() {}

    public PosicionApi(Long idPosicion, VendedorAmbulante vendedor, Coordenada coordenada, LocalDateTime fechaGen,
                       LocalDateTime fechaReg) {
        this.idPosicion = idPosicion;
        this.vendedor = vendedor;
        this.coordenada = coordenada;
        this.fechaGen = fechaGen;
        this.fechaReg = fechaReg;
    }

    public PosicionApi(Long idPosicion, Coordenada coordenada, LocalDateTime fechaGen,
                       LocalDateTime fechaReg) {
        this.idPosicion = idPosicion;
        this.coordenada = coordenada;
        this.fechaGen = fechaGen;
        this.fechaReg = fechaReg;
    }

    public Long getIdPosicion() {
        return idPosicion;
    }

    public void setIdPosicion(Long idPosicion) {
        this.idPosicion = idPosicion;
    }

    public VendedorAmbulante getVendedor() {
        return vendedor;
    }

    public void setVendedor(VendedorAmbulante vendedor) {
        this.vendedor = vendedor;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }

    public LocalDateTime getFechaGen() {
        return fechaGen;
    }

    public void setFechaGen(LocalDateTime fechaGen) {
        this.fechaGen = fechaGen;
    }

    public LocalDateTime getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(LocalDateTime fechaReg) {
        this.fechaReg = fechaReg;
    }

    @Override
    public String toString() {
        return "PosicionApi{" +
                "idPosicion=" + idPosicion +
                ", vendedor=" + vendedor +
                ", coordenada=" + coordenada +
                ", fechaGen=" + fechaGen +
                ", fechaReg=" + fechaReg +
                '}';
    }
}
