package com.example.geolocator.services.db;

import android.content.Context;

import com.example.geolocator.models.Posicion;
import com.example.geolocator.models.VendedorAmbulante;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(
        entities = {Posicion.class},
        version = 1
)
@TypeConverters(LocalDateTimeConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "geoDB.db").allowMainThreadQueries().build();
            }
        }

        return INSTANCE;
    }

    public abstract PosicionDao posicionDao();
}
