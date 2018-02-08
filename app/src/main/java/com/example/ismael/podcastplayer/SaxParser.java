package com.example.ismael.podcastplayer;

import com.example.ismael.podcastplayer.modelo.Podcast;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Clase que lee xml y crea una lista de Podcasts
 */
public class SaxParser extends DefaultHandler {

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
                podcastActual.setTitulo(sbTexto.toString().substring(0, sbTexto.length()-13).trim());
           } else if (localName.equals("guid")) {
                podcastActual.setGuid(sbTexto.toString().trim());
            } else if (localName.equals("duration")) {
                podcastActual.setDuracion(sbTexto.toString().trim());
            } else if (localName.equals("pubDate")) {
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