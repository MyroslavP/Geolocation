package com.example.geolocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView Latitude, Longitude, Distance;
    private LocationManager locationManager;
    double latitude, longitude;
    Location location;

    // flag for GPS status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Latitude = findViewById(R.id.lat);
        Longitude = findViewById(R.id.lon);
        Distance = findViewById(R.id.dist);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationStatus(20.956, 52.226);


    }

    private void LocationStatus(double a, double b) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            Log.d("Provider", "No network provider is enabled");
            return;
        }

        if (isNetworkEnabled) {
            //check the network permission
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 1, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    Longitude.setText(new DecimalFormat("##.####").format(location.getLongitude()));
                    Latitude.setText(new DecimalFormat("##.####").format(location.getLatitude()));

                    latitude = Double.parseDouble((new DecimalFormat("##.###").format(location.getLatitude())));
                    longitude = Double.parseDouble((new DecimalFormat("##.###").format(location.getLongitude())));
                    distance(latitude, longitude, 50.465, 30.545);

                    if (a == longitude && b == latitude) {
                        Toast toast = Toast.makeText(MainActivity.this, "Similar", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });
        }
        if (isGPSEnabled) {
            if (location == null) {
                //check the network permission
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        Longitude.setText(new DecimalFormat("##.####").format(location.getLongitude()));
                        Latitude.setText(new DecimalFormat("##.####").format(location.getLatitude()));

                        latitude = Double.parseDouble((new DecimalFormat("##.###").format(location.getLatitude())));
                        longitude = Double.parseDouble((new DecimalFormat("##.###").format(location.getLongitude())));
                        distance(latitude, longitude, 50.465, 30.545);

                        if (a == longitude && b == latitude) {
                            Toast toast = Toast.makeText(MainActivity.this, "Similar", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
            }
        }
    }

    private void distance(double a, double b, double c, double d) {
        Location locationA = new Location("point A");

        locationA.setLatitude(a);
        locationA.setLongitude(b);

        Location locationB = new Location("point B");

        locationB.setLatitude(c);
        locationB.setLongitude(d);

        float distance = locationA.distanceTo(locationB);
        distance = distance / 1000;
        Distance.setText(String.valueOf(distance));
    }

    public void as() {
        LocationRequest locationRequest = LocationRequest.create();
    }
}