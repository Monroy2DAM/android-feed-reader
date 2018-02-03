package com.example.ismael.podcastplayer;

import java.util.ArrayList;

/**
 * Created by Ismael on 14/01/2018.
 * Clase de catálogo de Podcasts
 */

public class Podcasts {

    private ArrayList<Podcast> listaPodcasts;

    /* -------------------- Constructor -------------------- */

    public Podcasts(ArrayList<Podcast> listaPodcasts) {
        this.listaPodcasts = listaPodcasts;
    }

    /* -------------------- Getter & Setter -------------------- */

    public void add(Podcast podcast){
        listaPodcasts.add(podcast);
    }

    public Podcast get(int index){
        return listaPodcasts.get(index);
    }

    public int size(){
        return listaPodcasts.size();
    }
}
