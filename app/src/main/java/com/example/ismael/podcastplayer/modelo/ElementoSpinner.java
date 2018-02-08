package com.example.ismael.podcastplayer.modelo;

/**
 * Created by Ismael on 08/02/2018.
 */
public class ElementoSpinner {

    private int id;
    private String tipo;
    private String nombre;

    public ElementoSpinner(int id, String tipo, String nombre) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return tipo+ ": " + nombre;
    }
}
