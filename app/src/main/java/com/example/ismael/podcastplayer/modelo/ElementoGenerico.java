package com.example.ismael.podcastplayer.modelo;

/***************************************************************************************************
 * Clase de elemento genérico hecha para que el adaptador de ListView sea válido para todas las
 * clases que extiendan de esta. Pueden exnteder:
 * Cancion - elemento recogido de m3u
 * Podcast - elemento recogido de xml
 * New - noticia de blog
 * Created by Ismael on 10/02/2018.
 ***************************************************************************************************/
public abstract class ElementoGenerico {

    public abstract String getUrl();

    public abstract String getTitulo();

    public abstract String getDuracion();

    public abstract String getFecha();

    public abstract String getImagen();

    public abstract void setTitulo(String titulo);

    public abstract void setUrl(String url);

    public abstract void setFecha(String fecha);

    public abstract void setDuracion(String duracion);

    public abstract void setImagen(String imagen);

}
