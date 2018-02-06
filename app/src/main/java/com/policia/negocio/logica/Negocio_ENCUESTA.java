package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_ENCUESTA;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_ENCUESTA;
import com.policia.persistencia.tablas.Tabla_ENCUESTA;
import com.policia.remote.response.ENCUESTASCNPCResult;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 29/12/2017.
 */

public class Negocio_ENCUESTA {

    private Context context;
    private Seguridad sesion;
    private Rutinas_ENCUESTA rutinasEncuesta;

    public Negocio_ENCUESTA(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasEncuesta = new Rutinas_ENCUESTA(context);
    }

    public boolean create(ENCUESTASCNPCResult resultEncuesta) {

        Tabla_ENCUESTA encuesta = new Tabla_ENCUESTA();
        encuesta.ID = String.valueOf(resultEncuesta.iDENCUESTA);
        encuesta.ENCUESTA_ESP = String.valueOf(resultEncuesta.pREGUNTAENCUESTAESP);
        encuesta.ENCUESTA_ENG = String.valueOf(resultEncuesta.pREGUNTAENCUESTAENG);
        encuesta.RESPUESTA_SI_ESP = String.valueOf(resultEncuesta.rESPUESTA1ENCUESTA);
        encuesta.RESPUESTA_NO_ESP = String.valueOf(resultEncuesta.rESPUESTA2ENCUESTA);
        encuesta.RESPUESTA_SI_ENG = String.valueOf(resultEncuesta.rESPUESTA1ENGENCUESTA);
        encuesta.RESPUESTA_NO_ENG = String.valueOf(resultEncuesta.rESPUESTA2ENGENCUESTA);
        encuesta.ACTIVA = resultEncuesta.aCTIVAENCUESTA;
        return (rutinasEncuesta.create(encuesta) == 1);
    }

    public void inactivar(String ID) {

        Tabla_ENCUESTA encuesta = new Tabla_ENCUESTA();
        encuesta.ID = ID;
        encuesta.ACTIVA = false;
        rutinasEncuesta.inactivar(encuesta);
    }

    public Modelo_ENCUESTA ultimaEncuesta() {
        return rutinasEncuesta.ultimaEncuesta(sesion.getIdiomaLargo());
    }

    public ArrayList<Long> existeEncuesta(String[] IDs) {
        return rutinasEncuesta.existeEncuesta(IDs);
    }
}