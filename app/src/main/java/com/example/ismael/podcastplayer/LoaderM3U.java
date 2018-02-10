package com.example.ismael.podcastplayer;

import com.example.ismael.podcastplayer.modelo.Cancion;
import com.example.ismael.podcastplayer.modelo.Canciones;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Ismael on 10/02/2018.
 */

public class LoaderM3U {

    private URL url;
    private ArrayList<Cancion> listaCanciones=new ArrayList<Cancion>();

    public LoaderM3U(String url){
        try {

            String direccionCancion="";
            String tituloCancion="";
            this.url = new URL(url);

            BufferedReader filtroEscritura=new BufferedReader(new InputStreamReader(this.url.openStream()));

            String linea=filtroEscritura.readLine();
            linea=filtroEscritura.readLine();

            while(linea!=null){

                if(linea.contains("http")){
                    direccionCancion=linea;
                    //Log.d("Direccion", direccionCancion);

                    //añadimos la cancion al arrayList
                    listaCanciones.add(new Cancion(direccionCancion, tituloCancion));

                }else{
                    if(!linea.equals("")){
                        StringTokenizer st=new StringTokenizer(linea, ",");
                        String nombre=st.nextToken();
                        nombre=st.nextToken();
                        //Log.d("Titulo cancion", nombre);
                        tituloCancion=nombre;
                    }
                }
                linea=filtroEscritura.readLine();
            }
            filtroEscritura.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Canciones getCanciones() {
        return new Canciones(listaCanciones);
    }
}
