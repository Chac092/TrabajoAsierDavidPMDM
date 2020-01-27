package com.example.trabajoasierdavid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
//En esta activity crearemos los 6 tipos de lugares con un objeto que contenga las imagenes y textos y despues las mostraremos de manera totalmente aleatoria.
public class mostrarLugar extends AppCompatActivity {
    TextView texto;
    ImageView imagen;
    Button Atras;
    ArrayList <Lugar> Lugares = new ArrayList<>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_lugar);
        crearLugares();
        double numrandom = Math.random()*6;//El numero se genera automaticamente
        //Los atributos se enlazan a sus correspondientes elementos
        imagen = findViewById(R.id.imageImagen);
        texto = findViewById(R.id.textLugar);
        Atras = findViewById(R.id.atras);
        //Cargamos la imagen y el texto aleatorio en los elementos
        texto.setText(Lugares.get((int)numrandom).Texto);//
        imagen.setImageDrawable(Lugares.get((int)numrandom).Imagen);
        //Este es un listener a un boton que nos devolvera a la pantalla principal
        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mostrarLugar.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    //En esta clase lo que haremos sera crear los 6 lugares y meterlos en un arraylist para usarlos despues
    public void crearLugares(){
        Lugar L1 = new Lugar(getResources().getString(R.string.texto1),getResources().getDrawable(R.drawable.foto1));
        Lugar L2 = new Lugar(getResources().getString(R.string.texto2),getResources().getDrawable(R.drawable.foto2));
        Lugar L3 = new Lugar(getResources().getString(R.string.texto3),getResources().getDrawable(R.drawable.foto3));
        Lugar L4 = new Lugar(getResources().getString(R.string.texto4),getResources().getDrawable(R.drawable.foto4));
        Lugar L5 = new Lugar(getResources().getString(R.string.texto5),getResources().getDrawable(R.drawable.foto5));
        Lugar L6 = new Lugar(getResources().getString(R.string.texto6),getResources().getDrawable(R.drawable.foto6));
        Lugares.add(L1);
        Lugares.add(L2);
        Lugares.add(L3);
        Lugares.add(L4);
        Lugares.add(L5);
        Lugares.add(L6);
    }

    //Este es el objeto Lugar con el que creaemos los 6 lugares
    //en este objeto tendremos la imagen y su texto correspondiente
    public class Lugar{
        String Texto;
        Drawable Imagen;
        public Lugar(String txt, Drawable drw){
            Texto=txt;
            Imagen = drw;
        }
    }
}
