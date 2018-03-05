package com.example.ismael.podcastplayer.modelo;

import java.util.ArrayList;

/**
 * Clase que modela la colección de noticias. Ver New.java para info de la fuente.
 * Created by Ismael on 11/02/2018.
 */
public class News extends ElementosGenerico<New> {
    private ArrayList<New> listaNews;

    /* -------------------- Constructor -------------------- */

    public News(){ listaNews = new ArrayList<New>(); }

    public News(ArrayList<New> listaNews) {
        this.listaNews = listaNews;
    }

    /* -------------------- Getter & Setter -------------------- */

    @Override
    public <T> void add(T New){
        listaNews.add((New)New);
    }

    @Override
    public New get(int index){
        return (New) listaNews.get(index);
    }

    @Override
    public int size(){
        return listaNews.size();
    }
}
