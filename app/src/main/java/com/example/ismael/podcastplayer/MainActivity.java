package com.example.ismael.podcastplayer;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ismael.listapodcast.R;
import com.example.ismael.podcastplayer.adapter.LoaderM3U;
import com.example.ismael.podcastplayer.adapter.SaxParser;
import com.example.ismael.podcastplayer.modelo.ColeccionGenerica;
import com.example.ismael.podcastplayer.adapter.ListViewAdapter;
import com.example.ismael.podcastplayer.modelo.ElementoSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

// TODO Lee el archivo readme para encontrar solución a errores típicos.
// TODO En la página GitHub -> Issues (-> Milestones) puedes tareas por hacer.

public class MainActivity extends AppCompatActivity {

    /* TODO Añadir aquí los podcasts con nombre y enlace */
    public static final ElementoSpinner[] fuentes = {
            new ElementoSpinner("Palabra de hacker", "http://www.ivoox.com/palabra-hacker_fg_f1266057_filtro_1.xml"),
            new ElementoSpinner("Play Rugby", "http://fapi-top.prisasd.com/podcast/playser/play_rugby.xml"),
            new ElementoSpinner("Oh My LOL", "https://recursosweb.prisaradio.com/podcasts/571.xml"),
            new ElementoSpinner("OC: El transistor", "http://www.ondacero.es/rss/podcast/644375/podcast.xml"),
            new ElementoSpinner("Canciones Orlando", "http://practicascursodam.esy.es/musica/milista.m3u")
    };

    private ListView lista;
    private ListViewAdapter adaptador;
    private Spinner spinner;

    /* Colección de tipo genérico (podría convertirse en Canciones o Podcasts) */
    private ColeccionGenerica coleccionGenerica;

    private MediaPlayer reproduccion;
    private ProgressDialog progresoCircular;

    /* -------------------- OnCreate -------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Asociamos elementos con la vista
        lista = findViewById(R.id.lista);
        spinner = findViewById(R.id.spinner);

        inicializarSpinner();

        configurarProgresoCircular();
        reproduccion = new MediaPlayer();

        /* -------------------- Eventos -------------------- */

        reproduccion.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if(progresoCircular.isShowing())
                    progresoCircular.dismiss();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Creamos hilo que procesará los datos. Le pasamos el tipo de Podcast o lista y su enlace
                HiloProcesador procesadorRss = new HiloProcesador();
                procesadorRss.execute(fuentes[(int)id].getTipo(), fuentes[(int)id].getUrl());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // OnClick en la lista
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                reproducirNuevoStream( coleccionGenerica.get(i).getUrlStream() );

            }
        });
        
    }

    /* ============================ Métodos e hilo reproductor ============================ */

    // TODO podríamos hacer que el spinner obtuviera los podcasts de un fichero de texto
    private void inicializarSpinner(){
        ArrayList<ElementoSpinner> elementosSpinner = new ArrayList<>(Arrays.asList(fuentes));

        ArrayAdapter<ElementoSpinner> adaptadorSpinner = new ArrayAdapter<ElementoSpinner>(this, R.layout.support_simple_spinner_dropdown_item, elementosSpinner);
        adaptadorSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adaptadorSpinner);
    }

    /** Inicializa la animación de progreso de carga */
    private void configurarProgresoCircular(){
        progresoCircular = new ProgressDialog(this);
        progresoCircular.setMessage("Procesando...");
        progresoCircular.setCancelable(true);
    }

    /** Reproduce un nuevo Stream si es posible */
    private void reproducirNuevoStream(String url){
        // Mírate: https://developer.android.com/reference/android/media/MediaPlayer.html
        progresoCircular.setMessage("Cargando Stream");
        progresoCircular.show();

        // Para impedir que se reproduzcan múltiples streams
        if(reproduccion.isPlaying())
            reproduccion.reset();

        // Reproducimos audio si es posible
        try {
            reproduccion.setDataSource(url);

            reproduccion.prepare(); // Aquí carga el audio, puede tardar
            reproduccion.start();

        } catch (IOException e) {
            if(progresoCircular.isShowing())
                progresoCircular.dismiss();
            reproduccion.reset();
            Toast.makeText(MainActivity.this, "Error de reproducción:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /* -------------------- AsyncTask -------------------- */

    /**
     * Hilo que interpretará los datos de internet y llenará la lista
     */
    private class HiloProcesador extends AsyncTask<String,Integer,Boolean> {

        /** Ejecutado antes de lanzar el hilo */
        @Override
        protected void onPreExecute() {
            progresoCircular.show();
        }

        /**
         * Procesamos los datos de internet
         * @param params viene a ser el tipo de fuente y la url
         * @return
         */
        protected Boolean doInBackground(String... params) {

            if (params[0].trim().equals("Podcast")) {
                // Con el viejo sax: coleccionGenerica = new SaxParser1(params[1]).parse();
                SaxParser xmlParser = new SaxParser(params[1]);
                coleccionGenerica = xmlParser.parse();
            }else
                if(params[0].trim().equals("Lista")){
                    LoaderM3U m3uLoader = new LoaderM3U(params[1]);
                    coleccionGenerica = m3uLoader.getCanciones();
                }
            return true;
        }

        /**
         * Lo que hacemos al finalizar procesamiento
         * @param result
         */
        protected void onPostExecute(Boolean result) {

            // Iniciamos y llenamos la lista tras leer y parsear el RSS
            adaptador = new ListViewAdapter(MainActivity.this, coleccionGenerica);
            lista.setAdapter(adaptador);
            if(progresoCircular.isShowing())
                progresoCircular.dismiss();
        }
    }


}
