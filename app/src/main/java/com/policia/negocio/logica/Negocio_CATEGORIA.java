package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_CATEGORIA;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_CATEGORIA;
import com.policia.persistencia.tablas.Tabla_CATEGORIA;
import com.policia.remote.RemoteClient;
import com.policia.remote.response.CATEGORIASMULTASCNPCResponse;
import com.policia.remote.response.CATEGORIASMULTASCNPCResult;

import java.util.ArrayList;

/**
 * Created by JORGE on 26/11/2017.
 */

public class Negocio_CATEGORIA {

    private Rutinas_CATEGORIA rutinasCategoria;
    private Seguridad sesion;

    private Context context;

    public Negocio_CATEGORIA(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasCategoria = new Rutinas_CATEGORIA(context);
    }

    public ArrayList<Modelo_CATEGORIA> CategoriaPorTipoMulta(String TipoMulta) {

        return rutinasCategoria.CategoriaPorTipoMulta(sesion.getIdiomaLargo(), TipoMulta);
    }

    public int sincronizar() {

        CATEGORIASMULTASCNPCResponse response = null;
        try {

            response = RemoteClient.connect(context).sincronizarCATEGORIA(rutinasCategoria.maxFecha());

            for (CATEGORIASMULTASCNPCResult result : response.cATEGORIASMULTASCNPCResult) {
                Tabla_CATEGORIA categoria = new Tabla_CATEGORIA();
                categoria.ID = String.valueOf(result.iDCATEGORIA);
                categoria.CATEGORIA_ESP = String.valueOf(result.cATEGORIAESP);
                categoria.CATEGORIA_ENG = String.valueOf(result.cATEGORIAENG);
                categoria.VIGENTE = result.aCTIVOCATEGOTRIAS;
                categoria.FECHA = String.valueOf(result.fECHAACTCATEGORIA);

                if (rutinasCategoria.exists(categoria.ID))
                    rutinasCategoria.update(categoria);
                else
                    rutinasCategoria.create(categoria);
            }
            return response.cATEGORIASMULTASCNPCResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
