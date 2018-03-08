package com.example.ismael.feedreader.modelo;

/**
 * Created by Ismael on 21/01/18.
 */
public class Cancion extends ElementoXML {
    String urlStream;
    String titulo;

    public Cancion(){ super(); }

    public Cancion(String urlStream, String titulo) {
        super();
        this.urlStream = urlStream;
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "urlStream='" + urlStream + '\'' +
                ", titulo='" + titulo + '\'' +
                '}';
    }

    @Override
    public String getRecurso() {
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

    @Override
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public void setRecurso(String recurso) {
        this.urlStream = recurso;
    }

    @Override
    public void setFecha(String fecha) {

    }

    @Override
    public void setDuracion(String duracion) {

    }

    @Override
    public void setImagen(String imagen) {

    }
}
