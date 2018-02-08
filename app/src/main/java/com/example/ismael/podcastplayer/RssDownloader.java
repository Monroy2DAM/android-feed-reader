package com.example.ismael.podcastplayer;

import com.example.ismael.podcastplayer.modelo.Podcasts;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import javax.xml.parsers.SAXParser;
import java.net.MalformedURLException;
import javax.xml.parsers.SAXParserFactory;

/**
 * Clase que recoge los datos de Internet y obtiene el catálogo de Podcasts basándose en un handler
 */
public class RssDownloader {

    private URL rssUrl;

	/* -------------------- Constructor -------------------- */

    public RssDownloader(String url) {
        try {
            this.rssUrl = new URL(url);
        }
        catch (MalformedURLException e) { throw new RuntimeException(e); }
    }

	/* -------------------- Métodos -------------------- */

    /**
     * Parsea los datos del xml según un handler programado por nosotros
     * Viene a ser adaptar los datos de xml a Podcasts
     * @return
     */
    public Podcasts parse() {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {

            // Creamos parser (viene en SAX) y manejador (interpretador del xml hecho por nosotros)
            SAXParser parser = factory.newSAXParser();
            SaxParser handler = new SaxParser();

            // Parseamos el stream de datos según nuestro manejador (el sax)
            parser.parse(this.getInputStream(), handler);

            // Devolvemos nuevo catálogo de Podcasts a partir de la lista de podcasts
            return new Podcasts(handler.getListaPodcasts());
        }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    /* -------------------- Obtiene stream -------------------- */

    /**
     * Método que devuelve stream de datos
     * @return
     */
    private InputStream getInputStream() {
        try {
            return rssUrl.openConnection().getInputStream();
        }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}
