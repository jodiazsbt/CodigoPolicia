package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_NUMERAL;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_NUMERAL;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 17/12/2017.
 */

public class Negocio_NUMERAL {

    private Rutinas_NUMERAL rutinasNumeral;
    private Seguridad sesion;

    public Negocio_NUMERAL(Context context) throws Exception {

        sesion = Seguridad.Sesion(context);
        rutinasNumeral = new Rutinas_NUMERAL(context);
    }

    public ArrayList<Modelo_NUMERAL> NumeralesPorArticulo(String Articulo){

        return rutinasNumeral.NumeralesPorArticulo(sesion.getIdiomaCodigo(), Articulo);
    }
}
