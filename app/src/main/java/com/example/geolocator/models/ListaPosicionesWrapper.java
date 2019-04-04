package com.example.geolocator.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ListaPosicionesWrapper {

    @JsonProperty
    private VendedorAmbulante vendedor;

    @JsonProperty
    private List<Posicion> posiciones;

    public ListaPosicionesWrapper(VendedorAmbulante vendedorAmbulante, List<Posicion> posiciones) {
        this.vendedor = vendedorAmbulante;
        this.posiciones = posiciones;
    }

    public ListaPosicionesWrapper() {
    }

    public VendedorAmbulante getVendedorAmbulante() {
        return vendedor;
    }

    public void setVendedorAmbulante(VendedorAmbulante vendedorAmbulante) {
        this.vendedor = vendedorAmbulante;
    }

    public List<Posicion> getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(List<Posicion> posiciones) {
        this.posiciones = posiciones;
    }

    @Override
    public String toString() {
        return "ListaPosicionesWrapper{" +
                "vendedorAmbulante=" + vendedor +
                ", posiciones=" + posiciones +
                '}';
    }
}
