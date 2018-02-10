package com.example.ismael.podcastplayer.modelo;

import java.util.ArrayList;

/**
 * Created by Ismael on 14/01/2018.
 * Clase de catálogo de Podcasts
 */

public class Podcasts extends ColeccionGenerica<Podcast>{

    private ArrayList<Podcast> listaPodcasts;

    /* -------------------- Constructor -------------------- */

    public Podcasts(){ listaPodcasts = new ArrayList<Podcast>(); }
    public Podcasts(ArrayList<Podcast> listaPodcasts) {
        this.listaPodcasts = listaPodcasts;
    }

    /* -------------------- Getter & Setter -------------------- */

    @Override
    public <T> void add(T podcast){
        listaPodcasts.add((Podcast)podcast);
    }

    @Override
    public Podcast get(int index){
        return (Podcast) listaPodcasts.get(index);
    }

    @Override
    public int size(){
        return listaPodcasts.size();
    }
}
