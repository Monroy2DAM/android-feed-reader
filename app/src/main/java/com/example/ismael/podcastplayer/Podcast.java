package com.example.ismael.podcastplayer;

/**
 * Created by Ismael on 14/01/2018.
 * Clase que define los datos de un Podcast
 */

public class Podcast {

    private String imagen, titulo, guid, duracion, fecha;

    /* -------------------- Constructor -------------------- */

    public Podcast() { }

    public Podcast(String imagen, String titulo, String guid, String duracion, String fecha) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.guid = guid;
        this.duracion = duracion;
        this.fecha = fecha;
    }

    /* -------------------- Getter & Setter -------------------- */

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) { this.imagen = imagen; }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGuid() { return guid; }

    public void setGuid(String guid) { this.guid = guid; }

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
}
