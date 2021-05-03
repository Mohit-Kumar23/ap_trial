package com.hp.appoindone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.ServiceCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationServices extends Service {

    @Override
    public void onCreate(){
        super.onCreate();
    }

    String pincode;
    public LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if(locationResult!=null && locationResult.getLastLocation() != null){
                double latitude = locationResult.getLastLocation().getLatitude();
                double longtitude = locationResult.getLastLocation().getLongitude();
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address>addresses = geocoder.getFromLocation(latitude,longtitude,1);
                    pincode = addresses.get(0).getPostalCode();
                    if (pincode!=null){
                        Log.i("heetpin",String.valueOf(pincode));
                        stopLocationService();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not Yet Implemented");
    }
    @SuppressLint("MissingPermission")
    private void startLocationService(){
        Log.i("heet","pincode");
        Toast.makeText(getApplicationContext(),"startLocation",Toast.LENGTH_SHORT).show();
        String channelID = "location_notification_channel";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),channelID);
        builder.setSmallIcon(R.mipmap.appoindone_logo);
        builder.setContentTitle("Location Service");
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentText("Running");
        builder.setAutoCancel(false);
        builder.setContentIntent(pendingIntent);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if (notificationManager!=null && notificationManager.getNotificationChannel(channelID)==null){
                NotificationChannel notificationChannel = new NotificationChannel(channelID,"Location Service",NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setDescription("This Channel is used  by location Service");
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
        startForeground(Constants.LOCATION_SERVICE_ID,builder.build());


    }

    private void stopLocationService(){
        com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(locationCallback);
        stopForeground(true);
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent!=null){
            String action = intent.getAction();
            Log.i("heet",action);
            if (action!=null){
                if(action.equals(Constants.ACTION_START_LOCATION_SERVICE)){
                    Log.i("heetonstart","start");
                    startLocationService();
                }
                else if(action.equals(Constants.ACTION_STOP_LOCATION_SERVICE)){
                    stopLocationService();
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
