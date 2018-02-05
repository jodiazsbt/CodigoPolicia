package com.policia.negocio.logica;

import android.content.Context;

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

            for (MetadataResult result : response.metadataResult) {
                Tabla_METADATA metadata = new Tabla_METADATA();
                metadata.ID = String.valueOf(result.iDMETADATA);
                metadata.METADATA_ESP = String.valueOf(result.nombreDataBusquedaESP);
                metadata.ACTIVO = result.vigenteMetadata;
                metadata.ARTICULO_ID = String.valueOf(result.idArticuloyparagrafoMetadata);
                metadata.FECHA = result.fechaMetadata;
                metadata.METADATA_ENG = String.valueOf(result.nombreDataBusquedaENG);

                if (rutinasMetadata.exists(metadata.ID))
                    rutinasMetadata.update(metadata);
                else
                    rutinasMetadata.create(metadata);
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
