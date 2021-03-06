package com.example.geolocator.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;



@Entity
public class Posicion {

    @PrimaryKey(autoGenerate = true)
    private Long persistIdPosicion;

    private Long idPosicion;

    private Long idVendedor;

    @JsonIgnore
    private Boolean registrado;

    @Embedded
    private Coordenada coordenada;

    private LocalDateTime fechaGen;

    private LocalDateTime fechaReg;

    public Posicion() {}

    public Posicion(Long persistIdPosicion, Long idPosicion, Long idVendedor, Boolean registrado, Coordenada coordenada, LocalDateTime fechaGen, LocalDateTime fechaReg) {
        this.persistIdPosicion = persistIdPosicion;
        this.idPosicion = idPosicion;
        this.idVendedor = idVendedor;
        this.registrado = registrado;
        this.coordenada = coordenada;
        this.fechaGen = fechaGen;
        this.fechaReg = fechaReg;
    }

    public Posicion(Long persistIdPosicion, Long idPosicion, Long idVendedor, Coordenada coordenada, LocalDateTime fechaGen,
                    LocalDateTime fechaReg) {
        this.idPosicion = idPosicion;
        this.idVendedor = idVendedor;
        this.coordenada = coordenada;
        this.fechaGen = fechaGen;
        this.fechaReg = fechaReg;
        this.persistIdPosicion = idPosicion;
    }

    public Posicion(Long idPosicion, Long idVendedor, Coordenada coordenada, LocalDateTime fechaGen,
                    LocalDateTime fechaReg) {
        this.idPosicion = idPosicion;
        this.idVendedor = idVendedor;
        this.coordenada = coordenada;
        this.fechaGen = fechaGen;
        this.fechaReg = fechaReg;
    }

    public Posicion(Long idPosicion, Coordenada coordenada, LocalDateTime fechaGen,
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

    public Long getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Long idVendedor) {
        this.idVendedor = idVendedor;
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

    public Long getPersistIdPosicion() {
        return persistIdPosicion;
    }

    public void setPersistIdPosicion(Long persistIdPosicion) {
        this.persistIdPosicion = persistIdPosicion;
    }

    public Boolean getRegistrado() {
        return registrado;
    }

    public void setRegistrado(Boolean registrado) {
        this.registrado = registrado;
    }

    @Override
    public String toString() {
        return "Posicion{" +
                "persistIdPosicion=" + persistIdPosicion +
                ", idPosicion=" + idPosicion +
                ", idVendedor=" + idVendedor +
                ", registrado=" + registrado +
                ", coordenada=" + coordenada +
                ", fechaGen=" + fechaGen +
                ", fechaReg=" + fechaReg +
                '}';
    }
}
