package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_TITULO_MULTA;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_TITULO_MULTA;

import java.util.ArrayList;

/**
 * Created by JORGE on 26/11/2017.
 */

public class Negocio_TITULO_MULTA {

    private Rutinas_TITULO_MULTA rutinasTitulo;
    private Seguridad sesion;

    public Negocio_TITULO_MULTA(Context context) throws Exception {

        sesion = Seguridad.Sesion(context);
        rutinasTitulo = new Rutinas_TITULO_MULTA(context);
    }

    public ArrayList<Modelo_TITULO_MULTA> TitulosPorLibro(String Libro) {

        return rutinasTitulo.TitulosPorLibro(sesion.getIdiomaCodigo(), Libro);
    }
}
