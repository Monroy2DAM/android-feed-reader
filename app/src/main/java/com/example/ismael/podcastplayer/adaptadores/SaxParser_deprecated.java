package com.example.ismael.podcastplayer.adaptadores;

import com.example.ismael.podcastplayer.modelo.Podcast;
import com.example.ismael.podcastplayer.modelo.Podcasts;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import javax.xml.parsers.SAXParser;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.xml.parsers.SAXParserFactory;

/**
 * Clase que recoge los datos de Internet y obtiene el catálogo de Podcasts basándose en un handler
 * La otra forma de interpretar el xml es más eficiente
 * Created by Ismael on 14/01/2018.
 */
@Deprecated
public class SaxParser_deprecated {

    private URL rssUrl;

	/* -------------------- Constructor -------------------- */

    public SaxParser_deprecated(String url) {
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
            SaxHandler handler = new SaxHandler();

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


    /* ============================ Clase interna SaxHandler ============================ */

    /**
     * Clase que lee xml y crea una lista de Podcasts
     */
    @Deprecated
    public class SaxHandler extends DefaultHandler {

        private ArrayList<Podcast> listaPodcasts;
        private Podcast podcastActual;
        private StringBuilder sbTexto;

        public ArrayList<Podcast> getListaPodcasts(){
            return listaPodcasts;
        }

    /* -------------------- Contenido etiqueta -------------------- */

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {

            super.characters(ch, start, length);

            if (this.podcastActual != null)
                sbTexto.append(ch, start, length);
        }

    /* -------------------- Final de elemento -------------------- */

        /**
         * Al finalizar cada elemento llenaremos las propiedades del podcast
         * @param uri
         * @param localName Etiqueta en la que nos encontramos
         * @param name Es el nombre completo de la etiqueta. Ej: itunes:image
         * @throws SAXException
         */
        @Override
        public void endElement(String uri, String localName, String name)
                throws SAXException {

            super.endElement(uri, localName, name);

            // Comprobamos en qué etiqueta estamos y guardamos su dato en PodcastActual
            if (this.podcastActual != null) {

                if (localName.equals("title")) {
                    podcastActual.setTitulo(sbTexto.toString().substring(0));
                } else if (localName.equals("guid")) {
                    podcastActual.setUrl(sbTexto.toString().trim());
                } else if (localName.equals("duration")) {
                    podcastActual.setDuracion(sbTexto.toString().trim());
                } else if (localName.equals("pubDate")) {
                    // FIXME Cuidado con el formato de fecha que traiga
                    podcastActual.setFecha(sbTexto.toString().substring(0, sbTexto.length()-6).trim());
                } else if (localName.equals("item")) {
                    listaPodcasts.add(podcastActual);
                }
                // La imagen se hace en el startElement porque nos viene como atributo

                sbTexto.setLength(0);
            }
        }

    /* -------------------- Comienzo documento -------------------- */

        @Override
        public void startDocument() throws SAXException {

            super.startDocument();

            // Iniciamos variables
            listaPodcasts = new ArrayList<Podcast>();
            sbTexto = new StringBuilder();
        }

    /* -------------------- Comienzo elemento -------------------- */

        @Override
        public void startElement(String uri, String localName,
                                 String name, Attributes attributes) throws SAXException {

            super.startElement(uri, localName, name, attributes);

            // Creamos nuevo podcast vacío al inicio de un <item>
            if (localName.equals("item"))
                podcastActual = new Podcast();

            // Ponemos la imagen. Viene como un atributo :
            // <itunes:image href="https://recursosweb.prisaradio.com/logos/cadenaser-logo_01.png"/>
            if(localName.equals("image") && podcastActual != null)
                podcastActual.setImagen(attributes.getValue(0));
        }
    }

}
