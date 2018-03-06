package com.example.ismael.podcastplayer.modelo;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * Clase que define los datos de un Podcast
 * Created by Ismael on 14/01/2018.
 */
public class Podcast extends ElementoGenerico{

    private String imagen, titulo, urlMp3, duracion, fecha;

    /* -------------------- Constructor -------------------- */

    public Podcast() { super(); }

    public Podcast(String imagen, String titulo, String urlMp3, String duracion, String fecha) {
        super();
        this.imagen = imagen;
        this.titulo = titulo;
        this.urlMp3 = urlMp3;
        this.duracion = duracion;
        this.fecha = fecha;
    }

    /* -------------------- Getter & Setter -------------------- */

    @Override
    public String getImagen() {
        return imagen;
    }

    @Override
    public void setImagen(String imagen) { this.imagen = imagen; }

    @Override
    public String getTitulo() {
        return titulo;
    }

    @Override
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String getUrl() { return urlMp3; }

    @Override
    public void setUrl(String urlMp3) { this.urlMp3 = urlMp3; }

    @Override
    public String getDuracion() {
        return duracion;
    }

    @Override
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    @Override
    public String getFecha() {
        String fechaParseada = null;
        try {
            // Tue, 20 Feb 2018 07:57:55 +0000
            DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss z", Locale.ENGLISH);
            Date result =  df.parse(fecha);
            fechaParseada = result.getDay() + " /" + result.getMonth() + "/" + result.getYear();
        } catch (ParseException e) {
            fechaParseada = fecha;
        }
        return fechaParseada;
    }

    @Override
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
