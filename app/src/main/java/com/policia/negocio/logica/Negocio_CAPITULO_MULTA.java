package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_CAPITULO_MULTA;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_CAPITULO_MULTA;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 29/11/2017.
 */

public class Negocio_CAPITULO_MULTA {

    private Rutinas_CAPITULO_MULTA rutinasCapitulo;
    private Seguridad sesion;

    public Negocio_CAPITULO_MULTA(Context context) throws Exception {

        sesion = Seguridad.Sesion(context);
        rutinasCapitulo = new Rutinas_CAPITULO_MULTA(context);
    }

    public ArrayList<Modelo_CAPITULO_MULTA> CapitulosPorTitulo(String Titulo) {

        return rutinasCapitulo.CapitulosPorTitulo(sesion.getIdiomaCodigo(), Titulo);
    }
}
