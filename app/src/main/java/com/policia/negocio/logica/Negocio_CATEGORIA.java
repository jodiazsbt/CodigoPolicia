package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_CATEGORIA;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_CATEGORIA;

import java.util.ArrayList;

/**
 * Created by JORGE on 26/11/2017.
 */

public class Negocio_CATEGORIA {

    private Rutinas_CATEGORIA rutinasTitulo;
    private Seguridad sesion;

    public Negocio_CATEGORIA(Context context) throws Exception {

        sesion = Seguridad.Sesion(context);
        rutinasTitulo = new Rutinas_CATEGORIA(context);
    }

    public ArrayList<Modelo_CATEGORIA> CategoriaPorTipoMulta(String TipoMulta) {

        return rutinasTitulo.CategoriaPorTipoMulta(sesion.getIdiomaCodigo(), TipoMulta);
    }
}
