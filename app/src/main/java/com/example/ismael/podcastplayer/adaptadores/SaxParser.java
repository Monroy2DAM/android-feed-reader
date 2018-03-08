package com.example.ismael.podcastplayer.adaptadores;

import android.sax.Element;
import android.sax.RootElement;
import android.util.Xml;

import com.example.ismael.podcastplayer.modelo.ElementoXML;
import com.example.ismael.podcastplayer.modelo.ColeccionAbstracta;
import com.example.ismael.podcastplayer.modelo.Podcasts;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Clase que lee el XML y crea una lista de Podcasts.
 * @author Francisco Rodríguez García
 */
public class SaxParser {
    // FIXME de más abajo para indicaciones de fallos de la aplicacion

    //==============================================================================================
    // ATRIBUTOS
    //==============================================================================================
    private URL rssUrl;
    private RootElement root;
    private Element channel, image, item;
    private static String urlImagen;
    private ElementoXML elemento;
    private Podcasts elementos;

    //==============================================================================================
    // CONSTRUCTOR
    //==============================================================================================
    public SaxParser(String url) {
        try {
            this.rssUrl = new URL(url);
            elementos = new Podcasts();
            urlImagen = ElementoXML.IMAGEN_DEFECTO;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    //==============================================================================================
    // MÉTODOS
    //==============================================================================================
    /**
     * Método que analiza el fichero XML.
     * @return devuelve un objeto Elementos, que contiene un ArrayList de objetos Elemento.
     */
    public ColeccionAbstracta parse() {
        // Elementos del XML
        root = new RootElement("rss");
        channel = root.getChild("channel");
        image = channel.getChild("image");
        item = channel.getChild("item");

        // Obtiene url de la imagen
        image.getChild("url").setEndTextElementListener(url -> urlImagen = url.trim() );

        // Al comenzar un item
        item.setStartElementListener(atributos -> {
            elemento = new ElementoXML();
            elemento.setImagen(urlImagen);
        });

        /* ============================ Básicos ============================ */
        item.getChild("title").setEndTextElementListener(titulo -> elemento.setTitulo(titulo.trim()));

        item.getChild("pubDate").setEndTextElementListener(fecha -> elemento.setFecha(fecha.trim()));

        item.getChild("description").setEndTextElementListener(descripcion -> elemento.setDescripcion(descripcion.trim()) );

        item.getChild("encoded").setEndTextElementListener(contenido -> elemento.setContenido(contenido.trim()) );

        // Fixme esto no está funcionando

        item.getChild("http://www.itunes.com/dtds/elemento-1.0.dtd","duration")
                .setEndTextElementListener(duracion -> elemento.setDuracion(duracion.trim()));

        item.getChild("http://www.itunes.com/dtds/elemento-1.0.dtd","image")
                .setStartElementListener(attributes -> elemento.setImagen(attributes.getValue("href").trim() ));

        /* ============================ Urls ============================ */
        item.getChild("enclosure").setStartElementListener(attributes -> {
                String url = attributes.getValue("url").trim();
                String formato = getFormato(url);
                if(formato.equals(".jpg") || formato.equals(".png") || formato.equals(".jpeg"))
                    elemento.setImagen(url);
                else
                    if(formato.equals(".mp3") || formato.equals(".ogg") || formato.equals(".wav"))
                        elemento.setRecurso(url);
            });

        item.getChild("link").setEndTextElementListener(url -> {
            String formato = getFormato(url.trim());
            if(formato.equals(".mp3") || formato.equals(".ogg") || formato.equals(".wav"))
                elemento.setRecurso(url.trim());
            else {
                if (formato.equals(".jpg") || formato.equals(".png") || formato.equals(".jpeg"))
                    elemento.setImagen(url);
                else
                    elemento.setLink(url.trim());
            }
        });

        item.getChild("guid").setEndTextElementListener(url -> {
            String formato = getFormato(url.trim());
            if(formato.equals(".mp3") || formato.equals(".ogg") || formato.equals(".wav"))
                elemento.setRecurso(url.trim());
            else
                elemento.setLink(url.trim());
        });

        // De aquí que se coge? en que podcast aparece?
        item.getChild("content").setStartElementListener(attributes -> elemento.setImagen(attributes.getValue("url").trim()) );




        item.setEndElementListener(() -> elementos.add(elemento) );

        try {
            Xml.parse(this.getInputStream(),
                    Xml.Encoding.UTF_8,
                    root.getContentHandler());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return elementos;
    }

    private InputStream getInputStream() {
        try {
            return rssUrl.openConnection().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Método que devuelve el formato de la url (.mp3, .jpg )
     * @param url
     * @return
     */
    public static String getFormato(String url){
        url = url.trim();
        return url.substring(url.lastIndexOf('.'), url.length());
    }
}
