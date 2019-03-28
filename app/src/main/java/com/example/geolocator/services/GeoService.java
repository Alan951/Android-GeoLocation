package com.example.geolocator.services;

import android.content.Context;
import android.location.Location;
import android.os.Build;

import com.example.geolocator.models.Coordenada;
import com.example.geolocator.models.Posicion;
import com.example.geolocator.services.db.AppDatabase;
import com.example.geolocator.services.db.DbService;

import java.time.LocalDateTime;

public class GeoService {

    private Context context;

    public GeoService(Context context){
        this.context = context;
    }

    public void onNewPosition(Location location){
        Posicion posicion = new Posicion();
        posicion.setCoordenada(new Coordenada(location.getLatitude(), location.getLongitude()));
        posicion.setFechaGen(LocalDateTime.now());
        posicion.setIdVendedor(1L);

        AppDatabase.getInstance(context).posicionDao().insertAll(posicion);
        
    }


}
