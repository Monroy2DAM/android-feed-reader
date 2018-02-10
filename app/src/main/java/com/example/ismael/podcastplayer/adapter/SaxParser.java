package com.example.ismael.podcastplayer.adapter;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Xml;

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
    // TODO mira

    //==============================================================================================
    // ATRIBUTOS
    //==============================================================================================
    private URL rssUrl;
    private Podcasts podcasts;
    private Podcast podcast;
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
    public Podcasts parse() {
        podcasts = new Podcasts(); // Se crea la lista de Podcasts.

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
                podcast = new Podcast();

                // Se añade la imagen.
                podcast.setImagen(urlImagen);
            }
        });

        // Se añade el título del Podcast.
        item.getChild("title").setEndTextElementListener(
                new EndTextElementListener() {
                    public void end(String titulo) {
                        podcast.setTitulo(titulo);
                    }
                });

        // Se añade la fecha del Podcast.
        item.getChild("pubDate").setEndTextElementListener(
                new EndTextElementListener() {
                    public void end(String fecha) {
                        podcast.setFecha(fecha);
                    }
                });

        // Se añade la duración del Podcast.
        // Nota: Como la etiqueta es "itunes:podcast", en este caso hay que especificar la URI, y luego el nombre de la etiqueta.
        item.getChild("http://www.itunes.com/dtds/podcast-1.0.dtd","duration").setEndTextElementListener(
                new EndTextElementListener() {
                    public void end(String duracion) {
                        podcast.setDuracion(duracion);
                    }
                });

        // Se añade la URL del MP3 del Podcast.
        item.getChild("enclosure").setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                podcast.setUrlMp3(attributes.getValue("url"));
            }
        });

        // Al finalizar un elemento "item", se añade el objeto Podcast a la lista de Podcasts.
        item.setEndElementListener(new EndElementListener() {
            public void end() {
                podcasts.add(podcast);
            }
        });

        try {
            Xml.parse(this.getInputStream(),
                    Xml.Encoding.UTF_8,
                    root.getContentHandler());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return podcasts;
    }

    private InputStream getInputStream() {
        try {
            return rssUrl.openConnection().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
