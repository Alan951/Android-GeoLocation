package com.example.geolocator;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geolocator.models.ListaPosicionesWrapper;
import com.example.geolocator.models.Posicion;
import com.example.geolocator.models.PosicionApi;
import com.example.geolocator.models.VendedorAmbulante;
import com.example.geolocator.services.VendedorAuthPref;
import com.example.geolocator.services.api.ApiService;
import com.example.geolocator.services.api.GeoServiceApi;
import com.example.geolocator.services.api.models.SuccesfulHandler;
import com.example.geolocator.services.db.AppDatabase;

import java.util.List;
import java.util.Optional;

public class MainActivity extends AppCompatActivity {

    private TextView txtEstadoServicio;
    private TextView txtCountPositions;
    private TextView txtVendedorName;
    private TextView btnRegistrarPosiciones;
    private Context context = this;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.txtEstadoServicio = findViewById(R.id.txtEstadoServicio);
        //this.txtCountPositions = findViewById(R.id.txtCountPositions);
        //this.txtCountPositions.setVisibility(View.INVISIBLE);
        this.txtVendedorName = findViewById(R.id.txtVendedorName);
        this.btnRegistrarPosiciones = findViewById(R.id.btnRegistrarPosiciones);

        this.btnRegistrarPosiciones.setText("Subir posiciones");

        checkStatus();

        VendedorAuthPref.getInstance(this).getIdVendedor().ifPresent((idVendedor) -> {
            Toast.makeText(this, "Obteniendo usuario con id = " + idVendedor, Toast.LENGTH_SHORT).show();

            ApiService.getInstance()
                    .getGeoService()
                    .getVendedor(idVendedor)
                    .enqueue(new Callback<VendedorAmbulante>() {
                @Override
                public void onResponse(Call<VendedorAmbulante> call, Response<VendedorAmbulante> response) {
                    if(response.isSuccessful()){
                        VendedorAmbulante vendedor = response.body();

                        txtVendedorName.setText(vendedor.getNombre());
                    }else if(response.code() == 404){ //El vendedor con ese id no existe
                        Toast.makeText(MainActivity.this, "El id del vendedor no existe", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<VendedorAmbulante> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error al realizar la petición", Toast.LENGTH_LONG).show();
                    txtVendedorName.setText(t.getMessage());
                }
            });
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch(requestCode){
            case 1: { //Solicitar permiso GPS
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startServiceIntent();
                }else{
                    this.txtEstadoServicio.setText("Sin autorización al GPS");
                }
            }
        }
    }

    protected void onIniciarServicio(View view){
        int havePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(havePermission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            startServiceIntent();
        }
    }

    private void startServiceIntent(){
        if(!VendedorAuthPref.getInstance(this).getIdVendedor().isPresent()){
            Toast.makeText(this, "No existe identificador", Toast.LENGTH_SHORT).show();
            openDialogReqIdVendedor();
            return;
        }else{
            Log.d(TAG, "IdVendedor: " + VendedorAuthPref.getInstance(this).getIdVendedor().get());
        }

        Intent serviceIntent = new Intent(this, LocatorService.class);
        //startService(serviceIntent);
        ContextCompat.startForegroundService(this, serviceIntent);

        checkStatus();
    }

    protected void onDetenerServicio(View view){
        Log.i("MainActivity", "Detener servicio");

        Intent serviceIntent = new Intent(this, LocatorService.class);
        stopService(serviceIntent);

        checkStatus();
    }

    private void checkStatus(){
        List<Posicion> unregPos = AppDatabase.getInstance(this).posicionDao().getUnregisteredPositions();

        if(unregPos.size() >= 0){
            this.btnRegistrarPosiciones.setText("Subir posiciones (" + unregPos.size() + ")");
        }

        boolean serviceExists = ServiceUtils.isServiceRunning(LocatorService.class, this);

        if(serviceExists){
            txtEstadoServicio.setText("En ejecución");
        }else{
            txtEstadoServicio.setText("Detenido");
        }
    }

    private void openDialogReqIdVendedor() {
        EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        VendedorAuthPref
                .getInstance(this)
                .getIdVendedor()
                .ifPresent((id) -> {
            Log.d(TAG, "Id load: " + id);
            editText.setText(id + "");
        });

        AlertDialog dialog = new AlertDialog.Builder(this).setCancelable(false)
                .setMessage("Ingresa un identificador (Long)")
                .setTitle("Identificador")
                .setView(editText)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    Long idVendedor = null;

                    if(editText.getText().toString().isEmpty()){
                        Toast.makeText(MainActivity.this, "Debes de ingresar un numero", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try{
                        idVendedor = Long.parseLong(editText.getText().toString());

                        boolean saved = VendedorAuthPref.getInstance(getApplicationContext()).saveIdVendedor(idVendedor.longValue());
                        Log.d(TAG, "Saved: " + saved);

                        Toast.makeText(MainActivity.this, "Identificador guardado", Toast.LENGTH_SHORT).show();

                    }catch(NumberFormatException nfe){
                        Toast.makeText(MainActivity.this, "Debes de ingresar un numero", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    dialogInterface.dismiss();

                }).create();

        dialog.show();


    }

    /*protected void onShowPositionsInConsole(View view){
        List<Posicion> posiciones = AppDatabase.getInstance(this).posicionDao().getPositions();

        Log.i("MainActivity", posiciones.toString());

        this.txtCountPositions.setText("Cantidad de posiciones registradas = " + posiciones.size());
        if(this.txtCountPositions.getVisibility() != View.VISIBLE)
            this.txtCountPositions.setVisibility(View.VISIBLE);
    }*/

    protected void onGetLastPosition(View view){
        ApiService.getInstance().getGeoService().getUltimaPosicion().enqueue(new Callback<List<PosicionApi>>() {
            @Override
            public void onResponse(Call<List<PosicionApi>> call, Response<List<PosicionApi>> response) {
                if(response.isSuccessful()){
                    List<PosicionApi> lastPositionOfVendor = response.body();

                    Log.i("MainActivity", "Posicion obtenida: " + lastPositionOfVendor.toString());
                }else{
                    Log.i("MainActivity", "No successful: " + response.raw().toString());
                }

            }

            @Override
            public void onFailure(Call<List<PosicionApi>> call, Throwable t) {
                Log.e("MainActivity", "Error onGetLastPosition", t);
            }
        });

    }

    protected void onRegistrarPosiciones(View view){
        if(!ServiceUtils.isConnectedOnWifi(this)){
            Toast.makeText(context, "No estas conectado a internet", Toast.LENGTH_SHORT).show();
            return;
        }


        List<Posicion> unregisteredPositions = AppDatabase.getInstance(this).posicionDao().getUnregisteredPositions();

        if(unregisteredPositions.size() == 0){
            Toast.makeText(this, "No existen posiciones sin registrar", Toast.LENGTH_SHORT).show();
            return;
        }

        ListaPosicionesWrapper posList = new ListaPosicionesWrapper();
        VendedorAmbulante vendedor = new VendedorAmbulante();
        vendedor.setIdVendedor(1L);

        posList.setVendedorAmbulante(vendedor);
        posList.setPosiciones(unregisteredPositions);

        Log.i(TAG, "posList: " + posList);

        ApiService.getInstance().getGeoService().guardarPosiciones(posList).enqueue(new Callback<SuccesfulHandler>() {
            @Override
            public void onResponse(Call<SuccesfulHandler> call, Response<SuccesfulHandler> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "onTestPositions (obj): " + response.body().getDetails());

                    unregisteredPositions.forEach((pos) -> {
                        pos.setRegistrado(true);
                    });

                    Log.i(TAG, "Posiciones marcadas como registrados");

                    AppDatabase
                            .getInstance(context)
                            .posicionDao().updatePositions(unregisteredPositions.stream().toArray(Posicion[]::new));

                    checkStatus();

                }else{
                    Log.e(TAG, "onTestPositions - noSuccesful: " + response.raw().toString() );
                    Toast.makeText(context, "Error al registrar las posiciones", Toast.LENGTH_SHORT).show();
                    checkStatus();
                }
            }

            @Override
            public void onFailure(Call<SuccesfulHandler> call, Throwable t) {
                Log.e(TAG, "onTestPositions - onFailure", t);
                Toast.makeText(context, "Sin acceso al servidor", Toast.LENGTH_SHORT).show();
                checkStatus();
            }
        });
    }
}
