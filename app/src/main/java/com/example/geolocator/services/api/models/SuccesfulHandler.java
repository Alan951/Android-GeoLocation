package com.example.geolocator.services.api.models;

import java.time.LocalDateTime;

public class SuccesfulHandler {

    private String accion;
    private Object detalles;

    private LocalDateTime timestamp;

    public SuccesfulHandler() {}

    public SuccesfulHandler(String accion, Object details) {
        this.accion = accion;
        this.detalles = details;
        this.timestamp = LocalDateTime.now();
    }

    public SuccesfulHandler(String accion) {
        this.accion = accion;
        this.timestamp = LocalDateTime.now();
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Object getDetails() {
        return detalles;
    }

    public void setDetails(Object details) {
        this.detalles = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "SuccesfulHandler [accion=" + accion + ", details=" + detalles + ", timestamp=" + timestamp + "]";
    }

}
