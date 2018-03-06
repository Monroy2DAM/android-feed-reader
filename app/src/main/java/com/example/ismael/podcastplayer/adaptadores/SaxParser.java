package com.example.ismael.podcastplayer.adaptadores;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Xml;

import com.example.ismael.podcastplayer.modelo.ElementoGenerico;
import com.example.ismael.podcastplayer.modelo.ElementosGenerico;
import com.example.ismael.podcastplayer.modelo.News;
import com.example.ismael.podcastplayer.modelo.Podcast;
import com.example.ismael.podcastplayer.modelo.Podcasts;

import org.xml.sax.Attributes;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Clase que lee el XML y crea una lista de Podcasts.
 * @author Francisco Rodríguez García
 */
public class SaxParser {
    // TODO mira el FIXME de más abajo para indicaciones de fallos de la aplicacion

    //==============================================================================================
    // ATRIBUTOS
    //==============================================================================================
    private URL rssUrl;
    private ElementosGenerico elementos;
    private ElementoGenerico elemento;
    private RootElement root;
    private Element channel, image, item;
    private static String urlImagen;

    //==============================================================================================
    // CONSTRUCTOR
    //==============================================================================================
    public SaxParser(String url) {
        try {
            this.rssUrl = new URL(url); // Se guarda la URL con el XML a analizar pasada por parámetro.

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    //==============================================================================================
    // MÉTODOS
    //==============================================================================================
    /**
     * Método que analiza el fichero XML.
     * @return devuelve un objeto Podcasts, que contiene un ArrayList de objetos Podcast.
     */
    public ElementosGenerico parse() {
        if(true)
            elementos = new Podcasts(); // Se crea la lista de Podcasts.
        else
            elementos = new News();

        root = new RootElement("rss"); // Se define el elemento raíz.
        channel = root.getChild("channel"); // Se define el hijo al que bajamos desde raíz.
        image = channel.getChild("image"); // Se define otro hijo al que bajamos desde "channel".
        item = channel.getChild("item"); // Se define otro hijo al que bajamos desde "channel".

        /**
         * FIXME Para los Podcasts de CadenaSer y OndaCero las imágenes vienen como:
         * <itunes:image href="https://recursosweb.prisaradio.com/logos/cadenaser-logo_01.png"/>
         * itunes:image + atributo de la etiqueta
         * ¿Cómo podríamos capturar todos los casos de XML?
         */

        // Se obtiene la url de la imagen.
        image.getChild("url").setEndTextElementListener(
                new EndTextElementListener() {
                    @Override
                    public void end(String url) {
                        urlImagen = url;
                    }
                }
        );

        // Al comenzar un elemento "item", se crea un objeto Podcast.
        item.setStartElementListener(new StartElementListener() {
            public void start(Attributes attrs) {
                elemento = new ElementoGenerico();

                // Se añade la imagen.
                elemento.setImagen(urlImagen);
            }
        });

        // Se añade el título del Podcast.
        item.getChild("title").setEndTextElementListener(
                new EndTextElementListener() {
                    public void end(String titulo) {
                        elemento.setTitulo(titulo);
                    }
                });

        // Se añade la fecha del Podcast.
        item.getChild("pubDate").setEndTextElementListener(
                new EndTextElementListener() {
                    public void end(String fecha) {
                        elemento.setFecha(fecha);
                    }
                });

        // Se añade la duración del Podcast.
        // Nota: Como la etiqueta es "itunes:elemento", en este caso hay que especificar la URI, y luego el nombre de la etiqueta.
        item.getChild("http://www.itunes.com/dtds/elemento-1.0.dtd","duration").setEndTextElementListener(
                new EndTextElementListener() {
                    public void end(String duracion) {
                        elemento.setDuracion(duracion);
                    }
                });

        // Se añade la URL del MP3 del Podcast.
        item.getChild("enclosure").setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                elemento.setUrl(attributes.getValue("url"));
            }
        });




        item.getChild("description").setEndTextElementListener(
                new EndTextElementListener() {
                    public void end(String contenido) {
                        if(elemento.getContenido().equals(ElementoGenerico.CONTENIDO_DEFECTO))
                            elemento.setContenido(contenido);
                    }
        });


        item.getChild("encoded").setEndTextElementListener(
                new EndTextElementListener() {
                    public void end(String contenido) {
                        elemento.setContenido(contenido);
                    }
        });


        item.getChild("content").setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                elemento.setUrl(attributes.getValue("url"));
            }
        });





        // Al finalizar un elemento "item", se añade el objeto Podcast a la lista de Podcasts.
        item.setEndElementListener(new EndElementListener() {
            public void end() {
                elementos.add((Podcast)elemento);
            }
        });

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
}
