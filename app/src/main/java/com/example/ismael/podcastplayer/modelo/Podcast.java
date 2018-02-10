package com.example.ismael.podcastplayer.modelo;

/**
 * Created by Ismael on 14/01/2018.
 * Clase que define los datos de un Podcast
 */

public class Podcast extends ElementoGenerico{

    private String imagen, titulo, urlMp3, duracion, fecha;

    /* -------------------- Constructor -------------------- */

    public Podcast() { }

    public Podcast(String imagen, String titulo, String urlMp3, String duracion, String fecha) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.urlMp3 = urlMp3;
        this.duracion = duracion;
        this.fecha = fecha;
    }

    /* -------------------- Getter & Setter -------------------- */

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) { this.imagen = imagen; }

    @Override
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrlMp3() { return urlMp3; }

    public void setUrlMp3(String urlMp3) { this.urlMp3 = urlMp3; }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String getUrlStream() {
        return getUrlMp3();
    }
}
