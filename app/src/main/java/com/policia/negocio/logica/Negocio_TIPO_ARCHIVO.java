package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_TIPO_ARCHIVO;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_TIPO_ARCHIVO;
import com.policia.persistencia.tablas.Tabla_TIPO_ARCHIVO;
import com.policia.remote.RemoteClient;
import com.policia.remote.response.TIPOSARCHIVOSResponse;
import com.policia.remote.response.TIPOSARCHIVOSResult;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 23/01/2018.
 */

public class Negocio_TIPO_ARCHIVO {

    private Rutinas_TIPO_ARCHIVO rutinasTipoArchivo;
    private Seguridad sesion;

    private Context context;

    public Negocio_TIPO_ARCHIVO(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasTipoArchivo = new Rutinas_TIPO_ARCHIVO(context);
    }

    public int sincronizar() {

        TIPOSARCHIVOSResponse response = null;
        try {

            response = RemoteClient.connect(context).sincronizarTIPO_ARCHIVO(rutinasTipoArchivo.maxFecha());

            for (TIPOSARCHIVOSResult result : response.tIPOSARCHIVOSResult) {
                Tabla_TIPO_ARCHIVO tipo_archivo = new Tabla_TIPO_ARCHIVO();
                tipo_archivo.ID = String.valueOf(result.iDTIPOARCHIVO);
                tipo_archivo.TIPO_ARCHIVO_ESP = String.valueOf(result.tIPOARCHIVOESP);
                tipo_archivo.ACTIVO = result.aCTIVOTIPOARCHIVO;
                tipo_archivo.TIPO_ARCHIVO_ENG = String.valueOf(result.tIPOARCHIVOENG);
                tipo_archivo.FECHA = String.valueOf(result.fECHAACTTIPOARCHIVO);

                if (rutinasTipoArchivo.exists(tipo_archivo.ID))
                    rutinasTipoArchivo.update(tipo_archivo);
                else
                    rutinasTipoArchivo.create(tipo_archivo);
            }
            return response.tIPOSARCHIVOSResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Modelo_TIPO_ARCHIVO> TipoArchivos() {
        return rutinasTipoArchivo.TipoArchivos(sesion.getIdiomaLargo());
    }
}
