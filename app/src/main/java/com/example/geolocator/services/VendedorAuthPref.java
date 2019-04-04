package com.example.geolocator.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.geolocator.models.VendedorAmbulante;

import java.util.Optional;

public class VendedorAuthPref {

    private static final String PREF_NAME = "VendAuthPref";

    private static VendedorAuthPref instance;

    private SharedPreferences prefs;
    private Context context;

    private VendedorAuthPref(Context context){
        this.context = context;

        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static VendedorAuthPref getInstance(Context context){
        if(instance == null){
            instance = new VendedorAuthPref(context);
        }

        return instance;
    }

    public boolean saveIdVendedor(long id){
        return this.prefs.edit().putLong("idVendedor", id).commit();
    }

    public Optional<Long> getIdVendedor(){
        Long idVendedor = this.prefs.getLong("idVendedor", -1);

        if(idVendedor > 0){
            return Optional.of(idVendedor);
        }

        return Optional.empty();
    }

}
