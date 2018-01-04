package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Capitulos.CapitulosOutput;
import com.policia.negocio.modelo.Capitulos.CapitulosResultEntry;
import com.policia.negocio.modelo.Modelo_Busqueda_Articulo;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_METADATA;
import com.policia.persistencia.tablas.Tabla_METADATA;
import com.policia.remote.RemoteClient;
import com.policia.remote.response.MetadataResponse;
import com.policia.remote.response.MetadataResult;

import java.util.ArrayList;

/**
 * Created by JORGE on 3/12/2017.
 */

public class Negocio_METADATA {
    private Rutinas_METADATA rutinasMetadata;
    private Seguridad sesion;

    private Context context;
    private RemoteClient remoteClient;

    public Negocio_METADATA(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasMetadata = new Rutinas_METADATA(context);
    }

    public int sincronizar() {

        remoteClient = new RemoteClient(context);
        MetadataResponse response = null;
        try {

            response = remoteClient.sincronizarMETADATA(rutinasMetadata.maxFecha());

            for (MetadataResult metadata : response.metadataResult) {
                Tabla_METADATA tablaMetadata = new Tabla_METADATA();
                tablaMetadata.ID = String.valueOf(metadata.iDMETADATA);
                tablaMetadata.METADATA_ESP = String.valueOf(metadata.nombreDataBusquedaESP);
                tablaMetadata.ACTIVO = metadata.vigenteMetadata;
                tablaMetadata.ARTICULO_ID = String.valueOf(metadata.idArticuloyparagrafoMetadata);
                tablaMetadata.FECHA = metadata.fechaMetadata;
                tablaMetadata.METADATA_ENG = String.valueOf(metadata.nombreDataBusquedaENG);
                rutinasMetadata.update(tablaMetadata);
            }
            return response.metadataResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Modelo_Busqueda_Articulo> BusquedaMETADATA(String termino_busqueda) {

        return rutinasMetadata.BusquedaMETADATA(sesion.getIdiomaCodigo(), termino_busqueda);
    }

}
