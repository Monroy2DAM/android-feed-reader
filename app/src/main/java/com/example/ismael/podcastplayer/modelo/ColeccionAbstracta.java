package com.example.ismael.podcastplayer.modelo;

import java.util.ArrayList;

/**************************************************************************************************
 * Clase genérica que tiene los métodos y atributos comunes para los tipos de colecciones.
 * Se ha creado para que el adaptador de pantalla de ListView sea válido para todas las clases que
 * extiendan de esta. Puede castearse a:
 * Canciones - Clase coleccion de Cancion recogidas de m3u
 * Podcasts - Clase coleccion de podcasts leidas de xml
 * News - noticias de blogs
 * Created by Ismael on 10/02/2018.
 **************************************************************************************************/

public abstract class ColeccionAbstracta<T> {

    private ArrayList<T> lista;

    public abstract <T> void add(T elemento);

    public abstract ElementoXML get(int index);

    public abstract int size();
}
