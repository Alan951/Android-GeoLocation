package com.example.geolocator.services.db;

import android.content.Context;

import androidx.room.Room;

public class DbService {

    private static DbService instance;
    private AppDatabase db;

    private DbService(Context context){
        this.db = Room.databaseBuilder(
                context,
                AppDatabase.class,
                "database-geolocation").build();


    }

    public static DbService getInstance(Context context){
        if(instance == null){
            instance = new DbService(context);
        }

        return instance;
    }

    public AppDatabase getTransactor(){
        return this.db;
    }

}
