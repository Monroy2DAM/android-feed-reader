package com.example.ismael.podcastplayer.modelo;

/***************************************************************************************************
 * Clase de elemento genérico hecha para que el adaptador de ListView sea válido para todas las
 * clases que extiendan de esta. Pueden exnteder:
 * Cancion - elemento recogido de m3u
 * Podcast - elemento recogido de xml
 * New - noticia de blog
 * Created by Ismael on 10/02/2018.
 ***************************************************************************************************/
public class ElementoGenerico {

    public static String CONTENIDO_DEFECTO = "Ningún contenido para mostrar.";
    public static String IMAGEN_DEFECTO = "https://image.flaticon.com/icons/svg/16/16268.svg";

    private String url, titulo, duracion, fecha, imagen, contenido;

    public ElementoGenerico() {
        this.imagen = IMAGEN_DEFECTO;
        this.contenido = CONTENIDO_DEFECTO;
        this.duracion = "";
    }

    public ElementoGenerico(String titulo, String url, String fecha, String duracion, String contenido, String imagen) {
        this.titulo = titulo;
        this.url = url;
        this.fecha = fecha;
        this.imagen = imagen;
        this.duracion = duracion;
        this.contenido = contenido;
    }

    public String getUrl(){ return this.url; }

    public String getTitulo(){ return this.titulo; }

    public String getDuracion(){ return this.duracion; }

    public String getFecha(){ return this.fecha; }

    public String getImagen(){ return this.imagen; }

    public String getContenido(){ return contenido; }

    public void setTitulo(String titulo){ this.titulo = titulo; }

    public void setUrl(String url){ this.titulo = url; }

    public void setFecha(String fecha){ this.titulo = fecha; }

    public void setDuracion(String duracion){ this.titulo = duracion; }

    public void setImagen(String imagen){ this.titulo = imagen; }

    public void setContenido(String contenido){ this.contenido = contenido; }

}
