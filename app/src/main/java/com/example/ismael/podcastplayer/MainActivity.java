package com.example.ismael.podcastplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ismael.listapodcast.R;

import java.io.IOException;

// FIXME Lee el archivo readme para encontrar fallos típicos.
// TODO En la página GitHub -> Issues (-> Milestones) puedes tareas por hacer.

public class MainActivity extends AppCompatActivity {

    // Podría haber que adaptar la clase Podcast para distintos rss
    public static final String[] LISTA_RSS = {
            "http://fapi-top.prisasd.com/podcast/playser/play_rugby.xml",
            "https://recursosweb.prisaradio.com/podcasts/571.xml"
    };

    private ListView lista;
    private AdaptadorLista adaptador;
    private Podcasts podcasts;

    /* -------------------- OnCreate -------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Asociamos elementos con la vista
        lista = findViewById(R.id.lista);

        // Creamos hilo que procesará los datos. Enlace al xml
        ProcesarRss procesadorRss = new ProcesarRss();
        procesadorRss.execute(LISTA_RSS[1]);

        // OnClick en la lista
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = podcasts.get(i).getGuid();
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                // Reproducimos audio
                try {
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare(); // might take long! (for buffering, etc)
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        
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
