package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_LIBRO;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_LIBRO;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 24/11/2017.
 */

public class Negocio_LIBRO {

    private Rutinas_LIBRO rutinasLibro;
    private Seguridad sesion;

    public Negocio_LIBRO(Context context) throws Exception {

        sesion = Seguridad.Sesion(context);
        rutinasLibro = new Rutinas_LIBRO(context);
    }

    public ArrayList<Modelo_LIBRO> Libros() {

        return rutinasLibro.Libros(sesion.getIdioma());
    }
}
