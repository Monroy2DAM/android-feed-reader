package com.example.ismael.podcastplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ismael.listapodcast.R;
import com.example.ismael.podcastplayer.modelo.ColeccionGenerica;
import com.example.ismael.podcastplayer.modelo.ElementoGenerico;
import com.example.ismael.podcastplayer.modelo.Podcast;
import com.squareup.picasso.Picasso;

/**
 * Clase que llena la lista mostrada en pantalla
 * Created by Ismael on 14/01/2018.
 */
public class ListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;

    private ColeccionGenerica coleccion;

    private TextView titulo, duracion, fecha;
    private ImageView imagen;

    /* -------------------- Constructor -------------------- */

    public ListViewAdapter(Context context, ColeccionGenerica coleccion) {
        this.inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.coleccion = coleccion;
    }

    /* -------------------- Métodos Adapter -------------------- */

    @Override
    public int getCount() {
        return coleccion.size();
    }

    @Override
    public Object getItem(int position) {
        return coleccion.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Cogemos el podcast actual
        ElementoGenerico elemento = coleccion.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.elemento_lista, null);
        }

        // Asociamos elementos con la vista
        imagen = convertView.findViewById(R.id.imagen);
        titulo = convertView.findViewById(R.id.titulo);
        duracion = convertView.findViewById(R.id.duracion);
        fecha = convertView.findViewById(R.id.fecha);

        // Aquí mostramos los parámetros generales
        titulo.setText(elemento.getTitulo());

        // Aquí mostramos la información adicional si es un Podcast
        if(elemento.getClass().getSimpleName().equals("Podcast")) {
            // Cargamos datos en la vista
            Picasso.with(context)
                    .load(((Podcast)elemento).getImagen())
                    .resize(80, 80)
                    .into(imagen);
            duracion.setText(((Podcast)elemento).getDuracion());
            fecha.setText(((Podcast)elemento).getFecha());
        }

        return convertView;
    }
}
