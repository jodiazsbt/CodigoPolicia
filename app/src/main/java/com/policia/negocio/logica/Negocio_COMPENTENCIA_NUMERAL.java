package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_COMPENTENCIA_NUMERAL;
import com.policia.persistencia.tablas.Tabla_COMPETENCIA_NUMERAL;
import com.policia.remote.RemoteClient;
import com.policia.remote.response.RELACIONCOMPETENCIANUMERALMEDIDACNPCResponse;
import com.policia.remote.response.RELACIONCOMPETENCIANUMERALMEDIDACNPCResult;

/**
 * Created by 1085253556 on 23/01/2018.
 */

public class Negocio_COMPENTENCIA_NUMERAL {

    private Rutinas_COMPENTENCIA_NUMERAL rutinasCompentenciaNumeral;
    private Seguridad sesion;

    private Context context;
    private RemoteClient remoteClient;

    public Negocio_COMPENTENCIA_NUMERAL(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasCompentenciaNumeral = new Rutinas_COMPENTENCIA_NUMERAL(context);
    }

    public int sincronizar() {

        remoteClient = new RemoteClient(context);
        RELACIONCOMPETENCIANUMERALMEDIDACNPCResponse response = null;
        try {

            response = remoteClient.sincronizarCOMPETENCIA_NUMERAL(rutinasCompentenciaNumeral.maxFecha());

            for (RELACIONCOMPETENCIANUMERALMEDIDACNPCResult result : response.rELACIONCOMPETENCIANUMERALMEDIDACNPCResult) {
                Tabla_COMPETENCIA_NUMERAL competencia_numeral = new Tabla_COMPETENCIA_NUMERAL();
                competencia_numeral.ID = String.valueOf(result.iDRELCOMPNUMMED);
                competencia_numeral.NUMERAL_ID = String.valueOf(result.iDNUMERALRELCOMNUMMED);
                competencia_numeral.COMPETENCIA_ID = String.valueOf(result.iDCOMPETENCIARELNUMMED);
                competencia_numeral.VIGENTE = result.aCTIVORELCOMNUMMED;
                competencia_numeral.FECHA = String.valueOf(result.fECHAACTRELCOMPNUMMED);

                if (rutinasCompentenciaNumeral.exists(competencia_numeral.ID))
                    rutinasCompentenciaNumeral.update(competencia_numeral);
                else
                    rutinasCompentenciaNumeral.create(competencia_numeral);
            }
            return response.rELACIONCOMPETENCIANUMERALMEDIDACNPCResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
