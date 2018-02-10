package com.example.ismael.podcastplayer.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ismael on 10/02/2018.
 */

public abstract class ColeccionGenerica<T> {

    private ArrayList<T> lista;

    public abstract <T> void add(T elemento);

    public abstract ElementoGenerico get(int index);

    public abstract int size();
}
