package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_MEDIDA;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_MEDIDA;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 17/12/2017.
 */

public class Negocio_MEDIDA {

    private Rutinas_MEDIDA rutinasMedida;
    private Seguridad sesion;

    public Negocio_MEDIDA(Context context) throws Exception {

        sesion = Seguridad.Sesion(context);
        rutinasMedida = new Rutinas_MEDIDA(context);
    }

    public ArrayList<Modelo_MEDIDA> MedidasPorParagrafo(String Paragrafo) {

        return rutinasMedida.MedidasPorParagrafo(sesion.getIdiomaCodigo(), Paragrafo);
    }
}
