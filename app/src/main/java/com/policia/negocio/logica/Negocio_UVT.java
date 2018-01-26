package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_UVT;
import com.policia.persistencia.tablas.Tabla_UVT;
import com.policia.remote.RemoteClient;
import com.policia.remote.response.UVTCNCPResult;
import com.policia.remote.response.UVTCNCPResult_;

/**
 * Created by 1085253556 on 23/01/2018.
 */

public class Negocio_UVT {

    private Rutinas_UVT rutinasUVT;
    private Seguridad sesion;

    private Context context;
    private RemoteClient remoteClient;

    public Negocio_UVT(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasUVT = new Rutinas_UVT(context);
    }

    public int sincronizar() {

        remoteClient = new RemoteClient(context);
        UVTCNCPResult response = null;
        try {

            response = remoteClient.sincronizarUVT(rutinasUVT.maxFecha());

            for (UVTCNCPResult_ result : response.uVTCNCPResult) {
                Tabla_UVT UVT = new Tabla_UVT();
                UVT.ID = String.valueOf(result.iDUVTCNCP);
                UVT.VALOR = String.valueOf(result.uVTVALOR);
                UVT.ACTIVO = result.aCTIVOUVTCNCP;
                UVT.FECHA = String.valueOf(result.fECHAACTUVTCNCP);
                UVT.ANIO = String.valueOf(result.aOUVTCNCP);
                rutinasUVT.update(UVT);
            }
            return response.uVTCNCPResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}