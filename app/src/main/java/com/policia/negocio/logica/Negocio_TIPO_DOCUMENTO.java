package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_TIPO_DOCUMENTO;
import com.policia.persistencia.rutinas.Rutinas_TIPO_DOCUMENTO;
import com.policia.persistencia.tablas.Tabla_TIPO_DOCUMENTO;
import com.policia.remote.RemoteClient;
import com.policia.remote.response.RNMCTIPOSDOCResponse;
import com.policia.remote.response.RNMCTIPOSDOCResult;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 9/02/2018.
 */

public class Negocio_TIPO_DOCUMENTO {

    private Context context;
    private Rutinas_TIPO_DOCUMENTO rutinasTipoDocumento;

    public Negocio_TIPO_DOCUMENTO(Context context) throws Exception {

        this.context = context;
        this.rutinasTipoDocumento = new Rutinas_TIPO_DOCUMENTO(context);
    }

    public int sincronizar() {

        RNMCTIPOSDOCResponse response = null;
        try {

            response = RemoteClient.connect(context).RNMC_TIPOS_DOC();

            for (RNMCTIPOSDOCResult result : response.rNMCTIPOSDOCResult) {
                Tabla_TIPO_DOCUMENTO tipo_documento = new Tabla_TIPO_DOCUMENTO();
                tipo_documento.ID = String.valueOf(result.iDTIPOSDOC);
                tipo_documento.TIPO_DOCUMENTO = String.valueOf(result.dESCRIPCIONTIPOSDOC);

                if (rutinasTipoDocumento.exists(tipo_documento.ID))
                    rutinasTipoDocumento.update(tipo_documento);
                else
                    rutinasTipoDocumento.create(tipo_documento);
            }
            return response.rNMCTIPOSDOCResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Modelo_TIPO_DOCUMENTO> TiposDocumento() {
        return rutinasTipoDocumento.TiposDocumento();
    }
}
