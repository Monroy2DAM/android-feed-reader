package com.example.ismael.feedreader.modelo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/***************************************************************************************************
 * Clase de elemento genérico hecha para que el adaptador de ListView sea válido para todos los
 * tipos de rss. Pueden ser:
 * Cancion - elemento recogido de m3u
 * Elemento - elemento recogido de xml
 * Created by Ismael on 10/02/2018.
 ***************************************************************************************************/
public class ElementoXML {

    public static String IMAGEN_DEFECTO = "https://www.shareicon.net/data/128x128/2016/08/18/809295_info_512x512.png";

    private String titulo, descripcion, fecha, imagen;
    private String recurso, link, duracion, contenido;

    /* -------------------- Constructor -------------------- */

    public ElementoXML() {
        this.imagen = IMAGEN_DEFECTO;
    }

    public ElementoXML(String titulo, String descripcion, String fecha, String imagen,
                       String recurso, String link, String duracion, String contenido) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.imagen = imagen;
        this.recurso = recurso;
        this.link = link;
        this.duracion = duracion;
        this.contenido = contenido;
    }

    /* -------------------- Getter -------------------- */

    public String getTitulo() { return titulo; }

    public String getDescripcion() { return descripcion; }

    public String getImagen() { return imagen; }

    public String getRecurso() {
        return recurso;
    }

    public String getLink() { return link; }

    public String getDuracion() { return duracion; }

    public String getContenido() { return contenido; }

    public String getFecha() {
        String fechaParseada = null;
        try {
            // Tue, 20 Feb 2018 07:57:55 +0000
            DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss z", Locale.ENGLISH);
            Calendar c = Calendar.getInstance();
            c.setTime(df.parse(fecha));
            fechaParseada = c.get(Calendar.DAY_OF_WEEK_IN_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR);
        } catch (ParseException e) {
            fechaParseada = fecha;
        }catch(NullPointerException e){
            fechaParseada = "(?)";
        }

        return fechaParseada;
    }

    /* -------------------- Setter -------------------- */

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public void setFecha(String fecha) { this.fecha = fecha; }

    public void setImagen(String imagen) { this.imagen = imagen; }

    public void setRecurso(String recurso) { this.recurso = recurso; }

    public void setLink(String link) { this.link = link; }

    public void setDuracion(String duracion) { this.duracion = duracion; }

    public void setContenido(String contenido) { this.contenido = contenido; }
}
