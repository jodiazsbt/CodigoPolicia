package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_MULTA;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_MULTA;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 1085253556 on 24/11/2017.
 */

public class Negocio_MULTA {

    private Rutinas_MULTA rutinasMulta;
    private Seguridad sesion;

    public Negocio_MULTA(Context context) throws Exception {

        sesion = Seguridad.Sesion(context);
        rutinasMulta = new Rutinas_MULTA(context);
    }

    public ArrayList<Modelo_MULTA> Multas() {
        return rutinasMulta.Multas(sesion.getIdiomaCodigo());
    }
}
