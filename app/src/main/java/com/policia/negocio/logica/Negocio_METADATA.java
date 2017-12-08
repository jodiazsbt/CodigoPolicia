package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_Busqueda_Articulo;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_METADATA;

import java.util.ArrayList;

/**
 * Created by JORGE on 3/12/2017.
 */

public class Negocio_METADATA {
    Rutinas_METADATA rutinas_metadata;
    private Seguridad sesion;

    public Negocio_METADATA(Context context) throws Exception {

        sesion = Seguridad.Sesion(context);
        rutinas_metadata = new Rutinas_METADATA(context);
    }

    public ArrayList<Modelo_Busqueda_Articulo> BusquedaMETADATA(String termino_busqueda) {

        return rutinas_metadata.BusquedaMETADATA(sesion.getIdioma(), termino_busqueda);
    }

}
