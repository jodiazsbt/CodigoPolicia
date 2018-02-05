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

            for (NivelesResult result : niveles.nivelesResult) {
                Tabla_NIVEL nivel = new Tabla_NIVEL();
                nivel.ID = String.valueOf(result.idNivel);
                nivel.NIVEL_ESP = String.valueOf(result.nombreNivelESP);
                nivel.VIGENTE = result.vigenteNivel;
                nivel.FECHA = String.valueOf(result.fechaNivel);
                nivel.NIVEL_ENG = String.valueOf(result.nombreNivelENG);
                
                if (rutinasNivel.exists(nivel.ID))
                    rutinasNivel.update(nivel);
                else
                    rutinasNivel.create(nivel);
            }
            return niveles.nivelesResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
