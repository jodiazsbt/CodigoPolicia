package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_DOCUMENTO;
import com.policia.negocio.modelo.Modelo_TIPO_ARCHIVO;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_DOCUMENTO;
import com.policia.persistencia.rutinas.Rutinas_TIPO_ARCHIVO;
import com.policia.persistencia.tablas.Tabla_DOCUMENTO;
import com.policia.remote.RemoteClient;
import com.policia.remote.response.DOCUMENTOSINSTRUCTIVOSCNPCNResponse;
import com.policia.remote.response.DOCUMENTOSINSTRUCTIVOSCNPCNResult;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 15/01/2018.
 */

public class Negocio_DOCUMENTO {

    private Rutinas_TIPO_ARCHIVO rutinasTipoArchivo;
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
        return rutinasDocumento.Documentos();
    }

    public int sincronizar() {

        remoteClient = new RemoteClient(context);
        DOCUMENTOSINSTRUCTIVOSCNPCNResponse response = null;
        try {
            int sincronizados = 0;
            rutinasTipoArchivo = new Rutinas_TIPO_ARCHIVO(context);
            for (Modelo_TIPO_ARCHIVO tipo_archivo : rutinasTipoArchivo.TipoArchivos(sesion.getIdiomaCodigo())) {

                response = remoteClient.sincronizarDOCUMENTO(tipo_archivo.ID);

                for (DOCUMENTOSINSTRUCTIVOSCNPCNResult result : response.dOCUMENTOSINSTRUCTIVOSCNPCNResult) {
                    Tabla_DOCUMENTO documento = new Tabla_DOCUMENTO();
                    documento.ID = String.valueOf(result.iDDOCUMENTO);
                    documento.DOCUMENTO_ESP = String.valueOf(result.nOMBREDOCUMENTO);
                    //documento.DOCUMENTO_ENG = result.nOMBREDOCUMENTO;//TODO:llega en español
                    documento.URL = String.valueOf(result.uRL);
                    documento.ACTIVO = result.aCTIVODOCUMENTOS;
                    documento.TIPO_ARCHIVO_ID = String.valueOf(result.iDTIPODOARCHIVO);
                    rutinasDocumento.update(documento);
                }

                sincronizados += response.dOCUMENTOSINSTRUCTIVOSCNPCNResult.size();
            }
            return sincronizados;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
