package com.example.trabajoasierdavid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private MapboxMap mapboxMap;
    private Button insertar;
    EditText nombre;
    EditText latitud;
    EditText longitud;
    Marker marcador;
    boolean marcadorPuesto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_main);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getApplicationContext());

        // set title
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Importante");
        alertDialog.setMessage("Para ver el marcador deseado en el punto inserte los valores: \n " +
                "Nombre , Latitud y Longitud \n" +
                "Despues pulse insertar y el marcador aparecera en el mapa");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setCancelable(false);
        alertDialog.show();


        nombre = findViewById(R.id.nombre);
        latitud = findViewById(R.id.editLatitud);
        longitud = findViewById(R.id.editLongitud);
        insertar = findViewById(R.id.insertar);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                        insertar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(marcadorPuesto){
                                    mapboxMap.removeMarker(marcador);
                                }
                                try{
                                    double Latitud = Double.parseDouble(latitud.getText().toString());
                                    double Longitud = Double.parseDouble(longitud.getText().toString());
                                    String Nombre = nombre.getText().toString();
                                    marcador = mapboxMap.addMarker(new MarkerOptions().
                                            position(new LatLng(Latitud, Longitud))
                                            .title(Nombre));
                                    CameraPosition posicion = new CameraPosition.Builder().target(marcador.getPosition()).build();
                                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(posicion));
                                    marcadorPuesto = true;
                                }catch (Exception e){
                                    Toast.makeText(getApplicationContext(),"Porfavor inserte caracteres validos, En caso de poner la latitud o longitud con una , sustituyala por un .",Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }
                });
                mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.misc021);
                        mediaPlayer.start();
                        Intent intent = new Intent(MainActivity.this,mostrarLugar.class);
                        startActivity(intent);

                        return false;
                    }
                });
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onMapReady(MapboxMap mapa) {
        mapboxMap = mapa;
    }

}
