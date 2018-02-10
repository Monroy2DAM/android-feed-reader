package com.example.ismael.podcastplayer.modelo;

/**
 * Created by Ismael on 08/02/2018.
 */
public class ElementoSpinner {

    private int id;
    private String tipo;
    private String nombre;
    private String url;

    public ElementoSpinner(String tipo, String nombre, String url) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.url = url;
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
