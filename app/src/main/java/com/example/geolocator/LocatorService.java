package com.example.geolocator;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class LocatorService extends Service {

    private static final String TAG = "LocatorService";
    private LocationManager locationManager;
    private LocationListener locationListener;


    @Override
    public void onCreate(){
        super.onCreate();

        Log.i(TAG, "onCreate invoked");

        initLocationService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand invoked");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                0);

        Notification notification = new NotificationCompat.Builder(this, "MYCHANNEL")
                .setContentTitle("Locator Service")
                .setContentText("El servicio de localización esta siendo ejecutado")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    public void initLocationService(){
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener(){
            public void onLocationChanged(Location location){
                Log.i(TAG, location.toString());
            }

            public void onStatusChanged(String provider, int status, Bundle extras){}

            public void onProviderEnabled(String var1){}

            public void onProviderDisabled(String var1){}
        };

        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
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
