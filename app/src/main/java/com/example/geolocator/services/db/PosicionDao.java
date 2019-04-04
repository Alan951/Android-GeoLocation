package com.example.geolocator.services.db;

import com.example.geolocator.models.Posicion;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PosicionDao {

    @Insert
    void insertAll(Posicion... posicions);

    @Query("SELECT * FROM Posicion")
    List<Posicion> getPositions();

}
