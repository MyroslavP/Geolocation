package com.example.geolocation_lib;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import com.google.android.gms.location.LocationRequest;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Geolocation {

    double latitude, longitude;
    Location location;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;

    public void LocationStatus(double a, double b, TextView Longitude, TextView Latitude, TextView Distance, Context context, Activity activity, LocationManager locationManager) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            Log.d("Provider", "No network provider is enabled");
            return;
        }

        if (isNetworkEnabled) {
            //check the network permission
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 1, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {

                    Longitude.setText(String.valueOf(location.getLongitude()));
                    Latitude.setText(String.valueOf(location.getLatitude()));

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    Distance.setText(String.valueOf(distance(latitude, longitude, 50.465, 30.545)));

                    if (a == longitude && b == latitude) {
                        Toast toast = Toast.makeText(context, "Similar", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });
        }
        if (isGPSEnabled) {
            if (location == null) {
                //check the network permission
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {

                        Longitude.setText(String.valueOf(location.getLongitude()));
                        Latitude.setText(String.valueOf(location.getLatitude()));

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                        Distance.setText(String.valueOf(distance(latitude, longitude, 50.465, 30.545)));

                        if (a == longitude && b == latitude) {
                            Toast toast = Toast.makeText(context, "Similar", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
            }
        }
    }
    public float distance(double a, double b, double c, double d) {
        Location locationA = new Location("point A");

        locationA.setLatitude(a);
        locationA.setLongitude(b);

        Location locationB = new Location("point B");

        locationB.setLatitude(c);
        locationB.setLongitude(d);

        float distance = locationA.distanceTo(locationB) / 1000;
        return distance;
    }

    public void sd()
    {
        LocationRequest locationRequest = LocationRequest.create();
    }
}