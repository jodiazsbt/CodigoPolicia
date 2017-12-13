package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_ARTICULO;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_ARTICULO;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 30/11/2017.
 */

public class Negocio_ARTICULO {

    private Rutinas_ARTICULO rutinasArticulo;
    private Seguridad sesion;

    public Negocio_ARTICULO(Context context) throws Exception {

        sesion = Seguridad.Sesion(context);
        rutinasArticulo = new Rutinas_ARTICULO(context);
    }

    public int CantidadArticulosPorCapitulo(String Capitulo) {

        return rutinasArticulo.CantidadArticulosPorCapitulo(Capitulo);
    }

    public ArrayList<Modelo_ARTICULO> ArticulosPorCapitulo(String Capitulo, int position) {

        return rutinasArticulo.ArticulosPorCapitulo(sesion.getIdiomaCodigo(), Capitulo, position);
    }
}
