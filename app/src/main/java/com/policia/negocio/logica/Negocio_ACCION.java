package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_ACCION;
import com.policia.remote.RemoteClient;

/**
 * Created by 1085253556 on 9/01/2018.
 */

public class Negocio_ACCION {

    private Rutinas_ACCION rutinasAccion;
    private Seguridad sesion;

    private Context context;
    private RemoteClient remoteClient;

    public Negocio_ACCION(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasAccion = new Rutinas_ACCION(context);
    }

    public String accionCiudadano(){
        return rutinasAccion.accionCiudadano(sesion.getIdiomaCodigo());
    }

    public String accionPolicia(){
        return rutinasAccion.accionPolicia(sesion.getIdiomaCodigo());
    }
}
