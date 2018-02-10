package com.example.ismael.podcastplayer.modelo;

/**
 * Clase de elemento genérico hecha para que el adaptador de ListView sea válido para todas las clases que extiendan de esta.
 * Pueden exnteder:
 * Cancion - elemento recogido de m3u
 * Podcast - elemento recogido de xml
 * Created by Ismael on 10/02/2018.
 */
public abstract class ElementoGenerico {
    public abstract String getUrlStream();

    public abstract String getTitulo();
}
