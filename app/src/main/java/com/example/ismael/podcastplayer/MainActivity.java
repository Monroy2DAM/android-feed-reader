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
import com.example.ismael.podcastplayer.modelo.AdaptadorLista;
import com.example.ismael.podcastplayer.modelo.ElementoSpinner;
import com.example.ismael.podcastplayer.modelo.Podcasts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

// FIXME Lee el archivo readme para encontrar fallos típicos.
// TODO En la página GitHub -> Issues (-> Milestones) puedes tareas por hacer.

public class MainActivity extends AppCompatActivity {

    // TODO cuidado con que varíe las etiquetas xml del podcast
    public static final String[] NOMBRES_RSS = {
            "Play Rugby",
            "Oh My LOL"
    };

    public static final String[] LISTA_RSS = {
            "http://fapi-top.prisasd.com/podcast/playser/play_rugby.xml",
            "https://recursosweb.prisaradio.com/podcasts/571.xml"
    };

    private ListView lista;
    private Spinner spinner;
    private AdaptadorLista adaptador;
    private Podcasts podcasts;

    private static MediaPlayer reproduccion;
    private ProcesarRss procesadorRss;

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
                // Creamos hilo que procesará los datos. Enlace al xml
                //procesadorRss.cancel(true);
                procesadorRss = new ProcesarRss();
                procesadorRss.execute(LISTA_RSS[ (int)id ]);
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
                String url = podcasts.get(i).getGuid();
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

    /* ================================= Métodos ================================= */

    private void inicializarSpinner(){
        ArrayList<ElementoSpinner> elementosSpinner = new ArrayList<ElementoSpinner>();
        for(int i = 0; i < NOMBRES_RSS.length; i++){ elementosSpinner.add(new ElementoSpinner(i, "Podcast", NOMBRES_RSS[i])); }
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
         * @param params viene a ser la url del RSS (la que viene del .execute)
         * @return
         */
        protected Boolean doInBackground(String... params) {

            RssDownloader saxparser =
                    new RssDownloader(params[0]);

            podcasts = saxparser.parse();

            return true;
        }

        /**
         * Lo que hacemos al finalizar procesamiento
         * @param result
         */
        protected void onPostExecute(Boolean result) {

            // Iniciamos y llenamos la lista tras leer y parsear el RSS
            adaptador = new AdaptadorLista(MainActivity.this, podcasts);
            lista.setAdapter(adaptador);

        }
    }


}
