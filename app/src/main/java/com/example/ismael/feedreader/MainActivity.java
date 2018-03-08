package com.example.ismael.feedreader;

import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ismael.listapodcast.R;
import com.example.ismael.feedreader.modelo.ColeccionAbstracta;
import com.example.ismael.feedreader.modelo.ElementoSpinner;

import java.util.ArrayList;
import java.util.Arrays;

/*
 +========================= ANDROID RSS PLAYER =========================+
 |                                                                      |
 | Reproductor de RSS's remotos colaborativo. Podrás:                   |
 | Participar, sugerir, documentarte, solucionar errores y más en...    |
 |                                                                      |
 | -------> https://github.com/ismenc/android-podcasts-player <-------  |
 |                                                                      |
 +======================================================================+
 */
public class MainActivity extends AppCompatActivity {

    /* TODO Añadir aquí los podcasts con nombre y enlace */
    public static final ElementoSpinner[] fuentes = {
            new ElementoSpinner("GeekyTheory", "https://geekytheory.com/feed"),
            new ElementoSpinner("Palabra de hacker", "http://www.ivoox.com/palabra-hacker_fg_f1266057_filtro_1.xml"),
            new ElementoSpinner("Play Rugby", "http://cadenaser.com/programa/rss/play_rugby/"),
            new ElementoSpinner("Oh My LOL", "https://recursosweb.prisaradio.com/podcasts/571.xml"),
            new ElementoSpinner("OC: El transistor", "http://www.ondacero.es/rss/podcast/644375/podcast.xml"),
            new ElementoSpinner("Canciones Orlando", "http://practicascursodam.esy.es/musica/milista.m3u"),
            new ElementoSpinner("EthereumWorldNews", "https://ethereumworldnews.com/feed/"),
            new ElementoSpinner("CryptoVest", "https://cryptovest.com/feed/"),
            new ElementoSpinner("Cointelegraph", "https://cointelegraph.com/rss"),
            new ElementoSpinner("InvestInBlockchain", "https://www.investinblockchain.com/category/news/feed/"),
            new ElementoSpinner("Bitfalls", "https://bitfalls.com/feed/"),
            new ElementoSpinner("Cryptoninjas", "https://www.cryptoninjas.net/feed/"),
            //new ElementoSpinner("CrypotPanic", "https://cryptopanic.com/about/api/"), NO furulA
            new ElementoSpinner("InsideBitcoins", "https://insidebitcoins.com/feed")
    };

    private ListView lista;

    /* Colección de tipo genérico (podría convertirse en Canciones o Elementos) */
    private ColeccionAbstracta elementos;

    /* ============================ OnCreate ============================ */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Asociamos elementos con la vista
        lista = findViewById(R.id.lista);
        Spinner spinner = findViewById(R.id.spinner);

        inicializarSpinner(spinner);

        /* -------------------- Eventos -------------------- */

        /* Cuando Seleccionamos otro RSS */
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(isNetworkAvailable()) {
                    HiloLector procesadorRss = new HiloLector(MainActivity.this, lista, coleccion -> elementos = coleccion);
                    procesadorRss.execute(fuentes[(int) id].getTipo(), fuentes[(int) id].getUrl());
                }else
                    Toast.makeText(MainActivity.this, "No tienes conexión a internet.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /* Click en el ListView */
        lista.setOnItemClickListener((parent, view, i, id) -> {

                Intent intent = new Intent(getApplication(), Detalle.class);
                intent.putExtra("TITULO", elementos.get(i).getTitulo());
                intent.putExtra("FECHA", elementos.get(i).getFecha());
                intent.putExtra("CONTENIDO", elementos.get(i).getContenido());
                intent.putExtra("DESCRIPCION", elementos.get(i).getDescripcion());
                intent.putExtra("LINK", elementos.get(i).getLink());
                intent.putExtra("IMAGEN", elementos.get(i).getImagen());
                if(elementos.get(i).getRecurso() != null)
                    intent.putExtra("RECURSO", elementos.get(i).getRecurso());
                startActivity(intent);
            });

    }

    /* ============================ Métodos ============================ */

    // TODO Las fuentes deben estar guardadas en un fichero
    private void inicializarSpinner(Spinner spinner){
        ArrayList<ElementoSpinner> elementosSpinner = new ArrayList<>(Arrays.asList(fuentes));

        ArrayAdapter<ElementoSpinner> adaptadorSpinner = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, elementosSpinner);
        adaptadorSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adaptadorSpinner);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
