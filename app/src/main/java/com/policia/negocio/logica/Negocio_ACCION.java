package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_ACCION;
import com.policia.persistencia.tablas.Tabla_ACCION;
import com.policia.remote.RemoteClient;
import com.policia.remote.response.ACCIONESCIUDADANOYPOLICIACNCPResult;
import com.policia.remote.response.ACCIONESCIUDADANOYPOLICIACNCPResult_;

/**
 * Created by 1085253556 on 9/01/2018.
 */

public class Negocio_ACCION {

    private Rutinas_ACCION rutinasAccion;
    private Seguridad sesion;

    private Context context;
    public Negocio_ACCION(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasAccion = new Rutinas_ACCION(context);
    }

    public String accionCiudadano(){

        return rutinasAccion.accionCiudadano(sesion.getIdiomaLargo());
    }

    public String accionPolicia(){

        return rutinasAccion.accionPolicia(sesion.getIdiomaLargo());
    }

    public int sincronizar() {

        ACCIONESCIUDADANOYPOLICIACNCPResult response = null;
        try {

            response = RemoteClient.connect(context).sincronizarACCION(rutinasAccion.maxFecha());

            for (ACCIONESCIUDADANOYPOLICIACNCPResult_ result : response.aCCIONESCIUDADANOYPOLICIACNCPResult) {
                Tabla_ACCION accion = new Tabla_ACCION();
                accion.ID = String.valueOf(result.iDCIUDADANOYPOLICIA);
                accion.ACCION_ESP = String.valueOf(result.dESCRIPCIONCIUDADANOYPOLICIAESP);
                accion.ACCION_ENG = String.valueOf(result.dESCRIPCIONCIUDADANOYPOLICIAENG);
                accion.FECHA = String.valueOf(result.fECHAACTCIUDADANOYPOLICIA);
                accion.VIGENTE = result.aCTIVOCIUDADANOYPOLICIA;
                rutinasAccion.update(accion);
            }
            return response.aCCIONESCIUDADANOYPOLICIACNCPResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
