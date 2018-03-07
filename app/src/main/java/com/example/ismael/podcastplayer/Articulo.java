package com.example.ismael.podcastplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ismael.listapodcast.R;

public class Articulo extends AppCompatActivity {

    private ImageView imagen;
    private TextView titulo, fecha, contenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viasta_completa);

        imagen = findViewById(R.id.articulo_imagen);
        titulo = findViewById(R.id.articulo_titulo);
        fecha = findViewById(R.id.articulo_fecha);
        contenido = findViewById(R.id.articulo_contenido);

        Bundle contenedor = getIntent().getExtras();

        titulo.setText(contenedor.getString("TITULO"));
        fecha.setText(contenedor.getString("FECHA"));
        contenido.setText(contenedor.getString("CONTENIDO"));
    }
}
