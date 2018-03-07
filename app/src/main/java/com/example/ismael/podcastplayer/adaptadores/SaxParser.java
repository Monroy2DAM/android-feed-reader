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
import java.util.function.Function;

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
    private RootElement root;
    private Element channel, image, item;
    private static String urlImagen;
    private Podcast elemento;
    private Podcasts elementos;

    //==============================================================================================
    // CONSTRUCTOR
    //==============================================================================================
    public SaxParser(String url) {
        try {
            this.rssUrl = new URL(url);
            elementos = new Podcasts();
            urlImagen = ElementoGenerico.IMAGEN_DEFECTO;
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
        // Elementos del XML
        root = new RootElement("rss");
        channel = root.getChild("channel");
        image = channel.getChild("image");
        item = channel.getChild("item");

        /**
         * FIXME Para los Podcasts de CadenaSer y OndaCero las imágenes vienen como:
         * <itunes:image href="https://recursosweb.prisaradio.com/logos/cadenaser-logo_01.png"/>
         * itunes:image + atributo de la etiqueta
         */

        // Se obtiene la url de la imagen.
        image.getChild("url").setEndTextElementListener(
                new EndTextElementListener() {
                    @Override
                    public void end(String url) {
                        urlImagen = url.trim();
                    }
                }
        );

        // Al comenzar un elemento "item", se crea un objeto Podcast.
        item.setStartElementListener(new StartElementListener() {
            public void start(Attributes attrs) {
                elemento = new Podcast();

                // Se añade la imagen.
                elemento.setImagen(urlImagen);
            }
        });

        /* ============================ Básicos ============================ */
        item.getChild("title").setEndTextElementListener(
                new EndTextElementListener() {
                    public void end(String titulo) {
                        elemento.setTitulo(titulo.trim());
                    }
                });

        item.getChild("pubDate").setEndTextElementListener(
                new EndTextElementListener() {
                    public void end(String fecha) {
                        elemento.setFecha(fecha.trim());
                    }
                });

        // Nota: Como la etiqueta es "itunes:elemento", en este caso hay que especificar la URI, y luego el nombre de la etiqueta.
        item.getChild("http://www.itunes.com/dtds/elemento-1.0.dtd","duration").setEndTextElementListener(
                new EndTextElementListener() {
                    public void end(String duracion) {
                        elemento.setDuracion(duracion.trim());
                    }
                });

        item.getChild("content").setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                elemento.setImagen(attributes.getValue("url").trim());
            }
        });

        item.getChild("http://www.itunes.com/dtds/elemento-1.0.dtd","image").setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                elemento.setImagen(attributes.getValue("href").trim());
            }
        });

        /* ============================ Urls ============================ */
        item.getChild("enclosure").setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                String url = attributes.getValue("url").trim();
                String formato = getFormato(url);
                if(formato.equals(".jpg") || formato.equals(".png") || formato.equals(".svg") || formato.equals(".jpeg"))
                    elemento.setImagen(url);
                else
                    if(elemento.getUrl() == null)
                        elemento.setUrl(url);
            }
        });

        item.getChild("link").setEndTextElementListener(
            new EndTextElementListener() {
                public void end(String url) {
                    elemento.setUrl(url.trim());
                }
        });

        /* ============================ Contenido ============================ */
        item.getChild("encoded").setEndTextElementListener(
            new EndTextElementListener() {
                public void end(String contenido) {
                    elemento.setContenido(contenido.trim());
                }
        });


        // Contenido alternativo al contenido grande (el de arriba)
        item.getChild("description").setEndTextElementListener(
                new EndTextElementListener() {
                    public void end(String contenido) {
                        if(elemento.getContenido().equals(ElementoGenerico.CONTENIDO_DEFECTO))
                            elemento.setContenido(contenido.trim());
                    }
        });



        // Al finalizar un elemento "item", se añade el objeto Podcast a la lista de Podcasts.
        item.setEndElementListener(new EndElementListener() {
            public void end() {
                elementos.add(elemento);
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


    /**
     * Devuelve el formato de la url (.mp3, .jpg )
     * @param url
     * @return
     */
    public static String getFormato(String url){
        url = url.trim();
        return url.substring(url.lastIndexOf('.'), url.length());
    }
}
