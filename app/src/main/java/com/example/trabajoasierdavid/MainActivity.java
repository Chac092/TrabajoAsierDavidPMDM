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
//En esta activity lo que haremos sera recojer los datos de el marcador y ponerlo sobre el mapa
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
        dialog();
        //Aqui uniremos los atributos con los correspondientes elementos
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
                        // Aqui lo que hacemos es poner el listener nada mas cargar el mapa para poner el marcador solicitado
                        insertar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //comprobamos que no hay marcadores puestos en caso de que los haya los eliminamos
                                if(marcadorPuesto){
                                    mapboxMap.removeMarker(marcador);
                                }
                                //ponemos un try a la hora de poner el marcador porsiacaso los datos indicados no son correctos
                                try{
                                    //recojemos los datos
                                    double Latitud = Double.parseDouble(latitud.getText().toString());
                                    double Longitud = Double.parseDouble(longitud.getText().toString());
                                    String Nombre = nombre.getText().toString();
                                    //Añadimos el marcador
                                    marcador = mapboxMap.addMarker(new MarkerOptions().
                                            position(new LatLng(Latitud, Longitud))
                                            .title(Nombre));
                                    //Centramos la camara hacia donde se acaba de colocar el marcador
                                    CameraPosition posicion = new CameraPosition.Builder().target(marcador.getPosition()).build();
                                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(posicion));
                                    marcadorPuesto = true; //indicamos que el marcador esta puesto para que si le da de nuevo a insertar se borre el anterior y se ponga el nuevo
                                }catch (Exception e){
                                    //Este toast sive para informar al usuario de que los parametros estan mal indicados
                                    Toast.makeText(getApplicationContext(),"Porfavor inserte caracteres validos, En caso de poner la latitud o longitud con una , sustituyala por un .",Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }
                });
                //Aqui pondremos un listener para cuando el usuario clique en el marcador
                //Este listener lo que hara sera reproducir un sonido y llevarnos a la siguiente pantalla en la que se nos mostrara una imagen y texto aleatorio
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
    //Aqui lo que hacemos es mostrar un dialogo que nos explique el funcionamiento de la aplicacion nada mas abrirla
    public void dialog(){
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
    }
    //Metodos relacionados con distintas funciones del mapa
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
