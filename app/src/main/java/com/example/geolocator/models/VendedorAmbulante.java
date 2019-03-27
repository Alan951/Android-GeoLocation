package com.example.geolocator.models;

import java.time.LocalDateTime;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VendedorAmbulante {

    @PrimaryKey
    private Long idVendedor;

    private String nombre;

    private String descripcion;

    private Integer estatus;

    private LocalDateTime fechaReg;

    public VendedorAmbulante() {}

    public VendedorAmbulante(Long idVendedor, String nombre, String descripcion, Integer estatus,
                             LocalDateTime fechaReg) {
        this.idVendedor = idVendedor;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estatus = estatus;
        this.fechaReg = fechaReg;
    }

    public Long getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Long idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public LocalDateTime getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(LocalDateTime fechaReg) {
        this.fechaReg = fechaReg;
    }

    @Override
    public String toString() {
        return "VendedorAmbulante{" +
                "idVendedor=" + idVendedor +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estatus=" + estatus +
                ", fechaReg=" + fechaReg +
                '}';
    }
}
