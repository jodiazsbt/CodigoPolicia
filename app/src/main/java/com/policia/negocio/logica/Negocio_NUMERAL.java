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

    public Negocio_NUMERAL(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasNumeral = new Rutinas_NUMERAL(context);
    }

    public int sincronizar() {

        NumeralesResponse response = null;
        try {

            response = RemoteClient.connect(context).sincronizarNUMERAL(rutinasNumeral.maxFecha());

            for (NumeralesResult result : response.numeralesResult) {
                Tabla_NUMERAL numeral = new Tabla_NUMERAL();
                numeral.ID = String.valueOf(result.iDNUMERAL);
                numeral.NUMERAL_ESP = String.valueOf(result.nombreDescripcionNumeralESP);
                numeral.NUMERAL_ENG = String.valueOf(result.nombreDescripcionNumeralENG);
                numeral.VIGENTE = result.vigenteNumeral;
                numeral.NIVEL_ID = String.valueOf(result.idNivelNumeral);
                numeral.ARTICULO_ID = String.valueOf(result.idArticulosyparagrafosNumeral);
                numeral.FECHA = String.valueOf(result.fechaNumeral);

                if (rutinasNumeral.exists(numeral.ID))
                    rutinasNumeral.update(numeral);
                else
                    rutinasNumeral.create(numeral);
            }
            return response.numeralesResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Modelo_NUMERAL> NumeralesPorArticulo(String Articulo){

        return rutinasNumeral.NumeralesPorArticulo(sesion.getIdiomaLargo(), Articulo);
    }
}
