package com.example.geolocator.services.api;

import com.example.geolocator.models.ListaPosicionesWrapper;
import com.example.geolocator.models.PosicionApi;
import com.example.geolocator.models.VendedorAmbulante;
import com.example.geolocator.services.api.models.SuccesfulHandler;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GeoServiceApi {

    @POST("posiciones")
    Call<SuccesfulHandler> guardarPosiciones(@Body ListaPosicionesWrapper posiciones);

    @POST("testPosiciones")
    Call<SuccesfulHandler> testPosiciones(@Body ListaPosicionesWrapper posiciones);

    @GET("vendedores/posicion")
    Call<List<PosicionApi>> getUltimaPosicion();

    @GET("vendedor/{idVendedor}")
    Call<VendedorAmbulante> getVendedor(@Path("idVendedor") Long idVendedor);

}
