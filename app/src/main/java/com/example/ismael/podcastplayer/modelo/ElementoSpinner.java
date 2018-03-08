package com.example.ismael.podcastplayer.modelo;

import com.example.ismael.podcastplayer.adaptadores.SaxParser;

/**
 * Elementos del spinner del principal. Estaría bien fusionarlo con ElementoXML
 * Created by Ismael on 08/02/2018.
 */
public class ElementoSpinner {

    private int id;
    /* Usado por el AsyncTask para saber si es elemento o Cancion */
    private String tipo;
    private String nombre;
    private String url;

    public ElementoSpinner(String nombre, String url) {
        this.nombre = nombre;
        this.url = url;

        String extension = SaxParser.getFormato(url);
        if( extension.equals(".m3u") )
            tipo = "Lista";
        else
            tipo = "Otro";
    }

    public ElementoSpinner(int id, String tipo, String nombre, String url) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return tipo+ ": " + nombre;
    }
}
