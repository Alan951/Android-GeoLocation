package com.example.geolocator;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.geolocator.services.GeoService;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class LocatorService extends Service {

    private static final String TAG = "LocatorService";
    private LocationManager locationManager;
    private LocationListener locationListener;
    private GeoService geoService;


    @Override
    public void onCreate(){
        super.onCreate();

        Log.i(TAG, "onCreate invoked");

        initLocationService();
        geoService = new GeoService(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand invoked");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, "MYCHANNEL")
                .setContentTitle("Locator Service")
                .setContentText("El servicio de localización esta siendo ejecutado")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel("MYCHANNEL", "Locator Service", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notiManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            notiManager.createNotificationChannel(nc);
        }

        startForeground(1, notification);

        Log.i(TAG, "notificationSended");

        return START_NOT_STICKY;
    }

    public void initLocationService(){
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener(){
            public void onLocationChanged(Location location){
                Log.i(TAG, location.toString());

                geoService.onNewPosition(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras){}

            public void onProviderEnabled(String var1){}

            public void onProviderDisabled(String var1){}
        };

        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60 * 10, 0, locationListener);
        }catch(SecurityException e){
            Log.i(TAG, "Error", e);
            stopSelf();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.locationManager.removeUpdates(locationListener);
        this.locationManager = null;


        Log.i(TAG, "onDestroyInvoked");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
