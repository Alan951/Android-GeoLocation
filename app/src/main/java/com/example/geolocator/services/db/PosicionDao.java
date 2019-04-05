package com.example.geolocator.services.db;

import com.example.geolocator.models.Posicion;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PosicionDao {

    @Insert
    void insertAll(Posicion... posicions);

    @Update
    void updatePositions(Posicion... posicion);

    @Query("SELECT * FROM Posicion")
    List<Posicion> getPositions();

    @Query("SELECT * FROM Posicion WHERE registrado == 0")
    List<Posicion> getUnregisteredPositions();

    @Query("SELECT * FROM Posicion WHERE registrado == 1")
    List<Posicion> getRegisteredPositions();
}
