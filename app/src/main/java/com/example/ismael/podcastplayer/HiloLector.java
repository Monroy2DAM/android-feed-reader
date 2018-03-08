package com.example.ismael.podcastplayer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.ismael.podcastplayer.adaptadores.ListViewAdapter;
import com.example.ismael.podcastplayer.adaptadores.LoaderM3U;
import com.example.ismael.podcastplayer.adaptadores.SaxParser;
import com.example.ismael.podcastplayer.modelo.ColeccionAbstracta;

/**
 * Hilo que interpretará los datos del RSS y llenará la lista
 */
class HiloLector extends AsyncTask<String, Integer, Boolean> {

    private Context contexto;
    private ColeccionAbstracta coleccionGenerica;
    private ListView lista;
    private ProgressDialog progresoCircular;

    /* Permite pasar la variable a otra clase */
    public interface AsyncResponse {
        void enviar(ColeccionAbstracta respuesta);
    }

    public AsyncResponse ifazRespuesta = null;

    /* -------------------------------------- Constructor -------------------------------------- */

    public HiloLector(Context contexto, ListView lista, AsyncResponse ifazRespuesta){
        this.contexto = contexto;
        this.lista = lista;
        this.ifazRespuesta = ifazRespuesta;
        configurarProgresoCircular();
    }

    /* -------------------------------------- Métodos -------------------------------------- */

    /* Inicializa la animación de progreso de carga */
    private void configurarProgresoCircular(){
        progresoCircular = new ProgressDialog(contexto);
        progresoCircular.setMessage("Procesando...");
        progresoCircular.setCancelable(true);
    }

    /* -------------------------------------- AsyncTask -------------------------------------- */

    /* Ejecutado antes de lanzar el hilo */
    @Override
    protected void onPreExecute() {
        progresoCircular.show();
    }

    /**
     * Procesamos los datos de internet según el tipo de fuente.
     * @param params params[0] = Tipo de fuente / params[1] = url del RSS
     */
    protected Boolean doInBackground(String... params) {
        String tipoDeFuente = params[0];

        if(tipoDeFuente.equals("Lista")){
            LoaderM3U m3uLoader = new LoaderM3U(params[1]);
            coleccionGenerica = m3uLoader.getCanciones();
        }else {
            coleccionGenerica = null;
            SaxParser xmlParser = new SaxParser(params[1]);
            coleccionGenerica = xmlParser.parse();
        }
        return true;
    }

    /* Lo que hacemos al finalizar procesamiento */
    protected void onPostExecute(Boolean result) {
        // Iniciamos y llenamos la lista tras leer y parsear el RSS
        ListViewAdapter adaptador = new ListViewAdapter(contexto, coleccionGenerica);
        lista.setAdapter(adaptador);
        ifazRespuesta.enviar(coleccionGenerica);

        // Cerramos animación de carga
        if(progresoCircular.isShowing())
            progresoCircular.dismiss();
    }
}