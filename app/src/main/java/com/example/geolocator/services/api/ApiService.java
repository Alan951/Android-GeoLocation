package com.example.geolocator.services.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiService {

    private static ApiService instance;
    private Retrofit retrofit;
    private GeoServiceApi geoService;

    private ApiService(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.101:8080/api/")
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .build();

        this.geoService = retrofit.create(GeoServiceApi.class);
    }

    public static ApiService getInstance(){
        if(instance == null){
            instance = new ApiService();
        }

        return instance;
    }

    public GeoServiceApi getGeoService(){
        return this.geoService;
    }

}
