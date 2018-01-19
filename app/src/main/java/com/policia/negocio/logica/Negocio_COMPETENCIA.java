package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_COMPENTENCIA;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_COMPETENCIA;
import com.policia.remote.RemoteClient;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 10/01/2018.
 */

public class Negocio_COMPETENCIA {


    private Rutinas_COMPETENCIA rutinasCompetencia;
    private Seguridad sesion;

    private Context context;
    private RemoteClient remoteClient;

    public Negocio_COMPETENCIA(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasCompetencia = new Rutinas_COMPETENCIA(context);
    }

    public ArrayList<Modelo_COMPENTENCIA> competenciasPorNumeral(String Numeral) {
        return rutinasCompetencia.competenciasPorNumeral(sesion.getIdiomaCodigo(), Numeral);
    }

    public int countCompetenciasPorNumeral(String Numeral) {
        return rutinasCompetencia.countCompetenciasPorNumeral(Numeral);
    }
}
