package com.example.geolocator.services;

import android.content.Context;
import android.location.Location;

import com.example.geolocator.models.Coordenada;
import com.example.geolocator.models.Posicion;
import com.example.geolocator.services.db.AppDatabase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class GeoService {

    private Context context;

    public GeoService(Context context){
        this.context = context;
    }

    public void onNewPosition(Location location){
        Posicion posicion = new Posicion();
        posicion.setCoordenada(new Coordenada(location.getLatitude(), location.getLongitude()));
        posicion.setFechaGen(LocalDateTime.now());

        Optional<Long> idVendedorOpt = VendedorAuthPref.getInstance(context).getIdVendedor();

        posicion.setIdVendedor(idVendedorOpt.isPresent() ? idVendedorOpt.get(): 1L );

        AppDatabase.getInstance(context).posicionDao().insertAll(posicion);
    }

    public void subirPosicion(Posicion posicion){

    }

    public void subirPosiciones(List<Posicion> posiciones){

    }


}
