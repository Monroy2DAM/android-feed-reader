package com.example.ismael.podcastplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ismael.listapodcast.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Intent encargado de mostrar información detallada del elemento seleccionado
 * @author Ismael
 */
public class Articulo extends AppCompatActivity {

    private MediaPlayer reproduccion;
    private String audio, link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_detalle);

        /* --------------------- Asociación con vista --------------------- */

        ImageView imagen = findViewById(R.id.detalle_imagen);
        TextView titulo = findViewById(R.id.detalle_titulo);
        TextView fecha = findViewById(R.id.detalle_fecha);
        TextView descripcion = findViewById(R.id.detalle_descripcion);
        TextView contenido = findViewById(R.id.detalle_contenido);
        Button botonSonido = findViewById(R.id.detalle_boton_recurso);
        Button botonFuente = findViewById(R.id.detalle_boton_link);

        /* --------------------- Llenado de vista --------------------- */

        Bundle contenedor = getIntent().getExtras();

        Picasso.with(this)
                .load(contenedor.getString("IMAGEN"))
                .resize(360,360)
                .centerInside()
                .into(imagen);
        titulo.setText(contenedor.getString("TITULO"));
        fecha.setText(contenedor.getString("FECHA"));
        descripcion.setText(contenedor.getString("DESCRIPCION"));
        contenido.setText(contenedor.getString("CONTENIDO"));

        if(contenedor.getString("LINK") != null)
            link = contenedor.getString("LINK");
        else
            botonFuente.setVisibility(View.INVISIBLE);

        if(contenedor.getString("RECURSO") != null) {
            reproduccion = new MediaPlayer();
            audio = contenedor.getString("RECURSO");
        }else
            botonSonido.setVisibility(View.INVISIBLE);

        /* --------------------- Botones --------------------- */

        botonSonido.setOnClickListener(v -> reproducirNuevoStream(audio));

        botonFuente.setOnClickListener(v -> {
            Uri webpage = Uri.parse(link);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        });
    }

    /* Reproduce el stream si es posible */
    private void reproducirNuevoStream(String url){
        // Mírate: https://developer.android.com/reference/android/media/MediaPlayer.html

        // Para impedir que se reproduzcan múltiples streams
        if(reproduccion.isPlaying())
            reproduccion.reset();

        // Reproducimos audio si es posible
        try {
            reproduccion.setDataSource(url);

            reproduccion.prepare(); // Aquí carga el audio, puede tardar
            reproduccion.start();

        } catch (IOException e) {
            reproduccion.reset();
            Toast.makeText(this, "Error de reproducción:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
            System.err.println(url + " <=========================================");
        }
    }
}
