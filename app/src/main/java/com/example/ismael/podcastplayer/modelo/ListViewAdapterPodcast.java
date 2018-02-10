package com.example.ismael.podcastplayer.modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ismael.listapodcast.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Ismael on 14/01/2018.
 * Clase que llena la lista mostrada en pantalla
 */
public class ListViewAdapterPodcast extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ColeccionGenerica coleccion;
    private TextView titulo, duracion, fecha;
    private ImageView imagen;

    /* -------------------- Constructor -------------------- */

    public ListViewAdapterPodcast(Context context, ColeccionGenerica coleccion) {
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

        titulo.setText(elemento.getTitulo());

        if(elemento.getClass().getName().equals("Podcast")) {
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
