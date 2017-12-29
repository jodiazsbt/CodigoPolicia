package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_NIVEL;
import com.policia.remote.RemoteClient;

/**
 * Created by 1085253556 on 28/12/2017.
 */

public class Negocio_NIVEL {

    private Rutinas_NIVEL rutinasNivel;
    private Seguridad sesion;

    private Context context;
    private RemoteClient remoteClient;

    public Negocio_NIVEL(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasNivel = new Rutinas_NIVEL(context);
    }

    public int sincronizar() {
        /*
        remoteClient = new RemoteClient(context);
        NivelesOutput niveles = remoteClient.sincronizarNIVEL(rutinasNivel.maxFecha());

        for (Object sincronizarNIVEL : niveles.NivelesResult) {
        }
        */
        return 0;
    }
}
