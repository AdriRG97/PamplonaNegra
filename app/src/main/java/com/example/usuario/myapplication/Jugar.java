package com.example.usuario.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Jugar extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marcador;
    double lat = 0.0;
    double lng = 0.0;
    int pista;
    Vibrator vibrador;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;


    public void AbrirInformacion(View view) {
        Intent irAINF = new Intent(getApplicationContext(), InfoActivity.class);
        startActivity(irAINF);
    }

    public void AbrirPista(View view) {


            Intent irAPista2 = new Intent(getApplicationContext(), Pista2Activity.class);
            startActivity(irAPista2);


    }


    @Override
    protected void onResume() {
        super.onResume();
        pista = prefs.getInt("pista", 0);
        if (pista == -1) {
            findViewById(R.id.button).setEnabled(false);
            Toast.makeText(this, "Fin del Juego", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        vibrador = ((Vibrator) getSystemService(VIBRATOR_SERVICE));

        prefs = getSharedPreferences("configs", Context.MODE_PRIVATE);
        editor = prefs.edit();

        editor.putInt("avance", 0);
        editor.commit();
    }

    public int ConocerPista() {
        pista = prefs.getInt("pista", 0);
        return pista;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyles));
        


        enableMyLocation();
//        mMap.setMyLocationEnabled(true);
        if (ConocerPista() != -1) {
//            Toast.makeText(this, "Ya has terminado el juego!", Toast.LENGTH_SHORT).show();
            actualizarUbicacion(miUbicacion());
            agregarMarcador(saberSitio().latitude, saberSitio().longitude);
        }
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            actualizarUbicacion(miUbicacion());
        }
    }

    private void agregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null) marcador.remove();
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Mi posición Actual"));
        //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ekis)));

        mMap.animateCamera(miUbicacion);

    }

    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng);
        }
    }


    private LatLng saberSitio() {
        ConocerPista();
        String linea;
        String[] sitio = new String[0];
        LatLng[] lugares = new LatLng[5];

        try {

            InputStream fraw =
                    getResources().openRawResource(R.raw.sitios);

            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));
//hacer un array y meter las pistas en un array para poder saber en k pista vamos
            for (int i = 0; i < 5; i++) {
                linea = brin.readLine();
                sitio = ((linea.split("//")[1]).split(","));
                lugares[i] = new LatLng(Double.parseDouble(sitio[0]), Double.parseDouble(sitio[1]));
            }
            fraw.close();
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero desde recurso raw");
        }
        // LatLng lugar = new LatLng(Double.parseDouble(sitio[0]), Double.parseDouble(sitio[1]));
        return lugares[pista];
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
            Location sitio = new Location("");
            sitio.setLatitude(saberSitio().latitude);
            sitio.setLongitude(saberSitio().longitude);


            if (miUbicacion().distanceTo(sitio) < 10) {
                //TOAST para avisar de que estas cerca y permitir introducir una respuesta
                editor.putInt("avance", 1);
                editor.commit();
                vibrador.vibrate(1000);
                Toast.makeText(Jugar.this, "Ahora puedes intentar resolver el enigma", Toast.LENGTH_SHORT).show();

                //esto da mucho error peta la aplicacion la cierra y toda la poya

              /*  AlertDialog alertita =  new AlertDialog.Builder(getApplicationContext())
                        .setMessage(R.string.alertita)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // After click on Ok, request the permission.

                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .create();
                alertita.show();*/

            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private Location miUbicacion() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
//        mMap.setMyLocationEnabled(true);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            actualizarUbicacion(location);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locationListener);


            //para que la ubicación coincida con el sitio de la proxima pista
            // Location sitioaux = new Location("");
            //sitioaux.setLatitude(saberSitio().latitude);
            //sitioaux.setLongitude(saberSitio().longitude);

            // return sitioaux;
            return location;
        }
        return null;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }


        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }

    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }

    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");

    }


}
