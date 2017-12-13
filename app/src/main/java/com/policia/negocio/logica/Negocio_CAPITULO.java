package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_CAPITULO;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_CAPITULO;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 29/11/2017.
 */

public class Negocio_CAPITULO {

    private Rutinas_CAPITULO rutinasCapitulo;
    private Seguridad sesion;

    public Negocio_CAPITULO(Context context) throws Exception {

        sesion = Seguridad.Sesion(context);
        rutinasCapitulo = new Rutinas_CAPITULO(context);
    }

    public ArrayList<Modelo_CAPITULO> CapitulosPorTitulo(String Titulo) {

        return rutinasCapitulo.CapitulosPorTitulo(sesion.getIdiomaCodigo(), Titulo);
    }
}
