package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_NUMERAL;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_NUMERAL;
import com.policia.persistencia.tablas.Tabla_NUMERAL;
import com.policia.remote.RemoteClient;
import com.policia.remote.response.NumeralesResponse;
import com.policia.remote.response.NumeralesResult;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 17/12/2017.
 */

public class Negocio_NUMERAL {

    private Rutinas_NUMERAL rutinasNumeral;
    private Seguridad sesion;

    private Context context;
    private RemoteClient remoteClient;

    public Negocio_NUMERAL(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasNumeral = new Rutinas_NUMERAL(context);
    }

    public int sincronizar() {

        remoteClient = new RemoteClient(context);
        NumeralesResponse response = null;
        try {

            response = remoteClient.sincronizarNUMERAL(rutinasNumeral.maxFecha());

            for (NumeralesResult metadata : response.numeralesResult) {
                Tabla_NUMERAL tablaNumeral = new Tabla_NUMERAL();
                tablaNumeral.ID = String.valueOf(metadata.iDNUMERAL);
                tablaNumeral.NUMERAL_ESP = String.valueOf(metadata.nombreDescripcionNumeralESP);
                tablaNumeral.NUMERAL_ENG = String.valueOf(metadata.nombreDescripcionNumeralENG);
                tablaNumeral.VIGENTE = metadata.vigenteNumeral;
                tablaNumeral.NIVEL_ID = String.valueOf(metadata.idNivelNumeral);
                tablaNumeral.ARTICULO_ID = String.valueOf(metadata.idArticulosyparagrafosNumeral);
                tablaNumeral.FECHA = String.valueOf(metadata.fechaNumeral);
                rutinasNumeral.update(tablaNumeral);
            }
            return response.numeralesResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Modelo_NUMERAL> NumeralesPorArticulo(String Articulo){

        return rutinasNumeral.NumeralesPorArticulo(sesion.getIdiomaCodigo(), Articulo);
    }
}
