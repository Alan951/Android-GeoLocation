package com.example.geolocator;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

public class ServiceUtils {

    public static boolean isServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isConnectedOnWifi(Context context){
        ConnectivityManager connMng = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE));

        Network network = connMng.getActiveNetwork();

        NetworkCapabilities capabilities = connMng.getNetworkCapabilities(network);

        boolean isConnectedToInternet =
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);

        boolean isConnectedOverWifi =
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);

        return isConnectedOverWifi && isConnectedToInternet;
    }

}
