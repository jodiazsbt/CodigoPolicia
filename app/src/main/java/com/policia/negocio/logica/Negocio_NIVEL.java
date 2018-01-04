package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_NIVEL;
import com.policia.persistencia.tablas.Tabla_NIVEL;
import com.policia.remote.RemoteClient;
import com.policia.remote.response.NivelesResponse;
import com.policia.remote.response.NivelesResult;

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

        remoteClient = new RemoteClient(context);
        NivelesResponse niveles = null;
        try {

            niveles = remoteClient.sincronizarNIVEL(rutinasNivel.maxFecha());

            for (NivelesResult nivel : niveles.nivelesResult) {
                Tabla_NIVEL tablaNivel = new Tabla_NIVEL();
                tablaNivel.ID = String.valueOf(nivel.idNivel);
                tablaNivel.NIVEL_ESP = String.valueOf(nivel.nombreNivelESP);
                tablaNivel.VIGENTE = nivel.vigenteNivel;
                tablaNivel.FECHA = String.valueOf(nivel.fechaNivel);
                tablaNivel.NIVEL_ENG = String.valueOf(nivel.nombreNivelENG);
                rutinasNivel.update(tablaNivel);
            }
            return niveles.nivelesResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
