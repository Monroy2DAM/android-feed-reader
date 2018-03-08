package com.example.ismael.feedreader.modelo;

import java.util.ArrayList;

/**
 * Clase de catálogo de Podcasts
 * Created by Ismael on 14/01/2018.
 */

public class Podcasts extends ColeccionAbstracta<ElementoXML> {

    private ArrayList<ElementoXML> listaPodcasts;

    /* -------------------- Constructor -------------------- */

    public Podcasts(){ listaPodcasts = new ArrayList<ElementoXML>(); }

    public Podcasts(ArrayList<ElementoXML> listaPodcasts) {
        this.listaPodcasts = listaPodcasts;
    }

    /* -------------------- Getter & Setter -------------------- */

    @Override
    public <T> void add(T podcast){
        listaPodcasts.add((ElementoXML)podcast);
    }

    @Override
    public ElementoXML get(int index){
        return (ElementoXML) listaPodcasts.get(index);
    }

    @Override
    public int size(){
        return listaPodcasts.size();
    }
}
