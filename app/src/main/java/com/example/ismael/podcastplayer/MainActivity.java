package com.example.ismael.podcastplayer;

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
import com.example.ismael.podcastplayer.modelo.Canciones;
import com.example.ismael.podcastplayer.modelo.ColeccionGenerica;
import com.example.ismael.podcastplayer.modelo.ListViewAdapterPodcast;
import com.example.ismael.podcastplayer.modelo.Cancion;
import com.example.ismael.podcastplayer.modelo.ElementoSpinner;
import com.example.ismael.podcastplayer.modelo.Podcasts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// FIXME Lee el archivo readme para encontrar fallos típicos.
// TODO En la página GitHub -> Issues (-> Milestones) puedes tareas por hacer.

public class MainActivity extends AppCompatActivity {

    /**
     * TODO Clase colección de elementos del que extienda Podcasts y Canciones
     * Métodos comunes: getGuid
     */

    /* TODO Cuidado! El primer campo debe ser el tipo "Podcast" o "Lista" */
    public static final ElementoSpinner[] fuentes = {
            new ElementoSpinner("Podcast", "Play Rugby", "http://fapi-top.prisasd.com/podcast/playser/play_rugby.xml"),
            new ElementoSpinner("Podcast", "Oh My LOL", "https://recursosweb.prisaradio.com/podcasts/571.xml"),
            new ElementoSpinner("Lista", "Canciones Orlando", "http://practicascursodam.esy.es/musica/milista.m3u")
    };

    private ListView lista;
    private Spinner spinner;
    private ListViewAdapterPodcast adaptador;

    private ColeccionGenerica coleccion;

    private static MediaPlayer reproduccion;
    private ProcesarRss procesadorRss;

    private String[] parametrosTask;

    /* -------------------- OnCreate -------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Asociamos elementos con la vista
        lista = findViewById(R.id.lista);
        spinner = findViewById(R.id.spinner);

        inicializarSpinner();

        reproduccion = new MediaPlayer();

        /* -------------------- Eventos -------------------- */

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Creamos hilo que procesará los datos. Le pasamos el tipo de Podcast o lista y su enlace
                procesadorRss = new ProcesarRss();
                parametrosTask[0] = fuentes[(int)id].getTipo();
                parametrosTask[1] = fuentes[(int)id].getUrl();
                procesadorRss = new ProcesarRss();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // OnClick en la lista
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // Mírate: https://developer.android.com/reference/android/media/MediaPlayer.html
                if(reproduccion.isPlaying())
                    reproduccion.reset();


                // Reproducimos audio
                String url = coleccion.get(i).getUrlStream();
                try {
                    reproduccion.setDataSource(url);
                    reproduccion.prepare(); // Aquí carga el audio, puede tardar
                    reproduccion.start();
                } catch (IOException e) {
                    reproduccion.stop();
                    Toast.makeText(MainActivity.this, "Error de reproducción:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        
    }

    /* ============================ Métodos e hilo reproductor ============================ */

    private void inicializarSpinner(){
        ArrayList<ElementoSpinner> elementosSpinner = new ArrayList<>(Arrays.asList(fuentes));

        ArrayAdapter<ElementoSpinner> adaptadorSpinner = new ArrayAdapter<ElementoSpinner>(this, R.layout.support_simple_spinner_dropdown_item, elementosSpinner);
        adaptadorSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adaptadorSpinner);
    }

    /* -------------------- AsyncTask -------------------- */

    /**
     * Hilo que interpretará los datos de internet y llenará la lista
     */
    private class ProcesarRss extends AsyncTask<String,Integer,Boolean> {

        /**
         * Procesamos los datos de internet
         * @param params viene a ser el tipo de fuente y la url
         * @return
         */
        protected Boolean doInBackground(String... params) {

            if (parametrosTask[0].equals("Podcast")) {
                RssDownloader saxparser = new RssDownloader(parametrosTask[1]);
                coleccion = saxparser.parse();
            }else
                if(parametrosTask[0].equals("Lista")){
                    LoaderM3U m3uLoader = new LoaderM3U(parametrosTask[1]);
                    coleccion = m3uLoader.getCanciones();
                }



            return true;
        }

        /**
         * Lo que hacemos al finalizar procesamiento
         * @param result
         */
        protected void onPostExecute(Boolean result) {

            // Iniciamos y llenamos la lista tras leer y parsear el RSS
            adaptador = new ListViewAdapterPodcast(MainActivity.this, coleccion);
            lista.setAdapter(adaptador);

        }
    }


}
