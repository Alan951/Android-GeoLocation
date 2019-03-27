package com.example.geolocator;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.xml.sax.Locator;

public class MainActivity extends AppCompatActivity {

    private TextView txtEstadoServicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.txtEstadoServicio = findViewById(R.id.txtEstadoServicio);
        checkStatus();
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

        Log.i("MainActivity", "Iniciar servicio");
    }

    private void startServiceIntent(){
        Intent serviceIntent = new Intent(this, LocatorService.class);
        startService(serviceIntent);

        checkStatus();
    }

    protected void onDetenerServicio(View view){
        Log.i("MainActivity", "Detener servicio");

        Intent serviceIntent = new Intent(this, LocatorService.class);
        stopService(serviceIntent);

        checkStatus();
    }

    private void checkStatus(){
        boolean serviceExists = ServiceUtils.isServiceRunning(LocatorService.class, this);

        if(serviceExists){
            txtEstadoServicio.setText("En ejecución");
        }else{
            txtEstadoServicio.setText("Detenido");
        }
    }
}
