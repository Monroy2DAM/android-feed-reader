package com.example.ismael.podcastplayer.modelo;

/**
 * Created by srk on 21/01/18.
 */

public class Cancion extends ElementoGenerico{
    String direccionCancion;
    String tituloCancion;

    public Cancion(){}

    public Cancion(String direccionCancion, String tituloCancion) {
        this.direccionCancion = direccionCancion;
        this.tituloCancion = tituloCancion;
    }

    public
    String getDireccionCancion() {
        return direccionCancion;
    }

    public
    void setDireccionCancion(String direccionCancion) {
        this.direccionCancion = direccionCancion;
    }

    public
    String getTituloCancion() {
        return tituloCancion;
    }

    public
    void setTituloCancion(String tituloCancion) {
        this.tituloCancion = tituloCancion;
    }

    @Override
    public
    String toString() {
        return "Cancion{" +
                "direccionCancion='" + direccionCancion + '\'' +
                ", tituloCancion='" + tituloCancion + '\'' +
                '}';
    }

    @Override
    public String getUrlStream() {
        return direccionCancion;
    }

    @Override
    public String getTitulo() {
        return tituloCancion;
    }
}
