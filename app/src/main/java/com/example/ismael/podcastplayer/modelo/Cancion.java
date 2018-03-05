package com.example.ismael.podcastplayer.modelo;

/**
 * Created by Ismael on 21/01/18.
 */
public class Cancion extends ElementoGenerico{
    String urlStream;
    String titulo;

    public Cancion(){}

    public Cancion(String urlStream, String titulo) {
        this.urlStream = urlStream;
        this.titulo = titulo;
    }

    public
    void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public
    String toString() {
        return "Cancion{" +
                "urlStream='" + urlStream + '\'' +
                ", titulo='" + titulo + '\'' +
                '}';
    }

    @Override
    public String getUrl() {
        return urlStream;
    }

    @Override
    public String getTitulo() {
        return titulo;
    }

    @Override
    public String getDuracion() {
        return "";
    }

    @Override
    public String getFecha() {
        return "";
    }

    @Override
    public String getImagen() {
        return "https://dl2.macupdate.com/images/icons256/52415.png?d=1513200110";
    }
}
