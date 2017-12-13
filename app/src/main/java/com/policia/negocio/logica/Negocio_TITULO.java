package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_TITULO;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_TITULO;

import java.util.ArrayList;

/**
 * Created by JORGE on 26/11/2017.
 */

public class Negocio_TITULO {

    private Rutinas_TITULO rutinasTitulo;
    private Seguridad sesion;

    public Negocio_TITULO(Context context) throws Exception {

        sesion = Seguridad.Sesion(context);
        rutinasTitulo = new Rutinas_TITULO(context);
    }

    public ArrayList<Modelo_TITULO> TitulosPorLibro(String Libro) {

        return rutinasTitulo.TitulosPorLibro(sesion.getIdiomaCodigo(), Libro);
    }
}
