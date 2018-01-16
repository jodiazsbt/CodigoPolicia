package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_DOCUMENTO;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_DOCUMENTO;
import com.policia.remote.RemoteClient;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 15/01/2018.
 */

public class Negocio_DOCUMENTO {

    private Rutinas_DOCUMENTO rutinasDocumento;
    private Seguridad sesion;

    private Context context;
    private RemoteClient remoteClient;

    public Negocio_DOCUMENTO(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasDocumento = new Rutinas_DOCUMENTO(context);
    }

    public ArrayList<Modelo_DOCUMENTO> Documentos() {
        return rutinasDocumento.Documentos(sesion.getIdiomaCodigo());
    }
}
