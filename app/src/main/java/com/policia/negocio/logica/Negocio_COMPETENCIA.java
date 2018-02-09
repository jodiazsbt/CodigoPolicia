package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_COMPENTENCIA;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_COMPETENCIA;
import com.policia.persistencia.tablas.Tabla_COMPETENCIA;
import com.policia.remote.RemoteClient;
import com.policia.remote.response.COMPETENCIASCNPCResponse;
import com.policia.remote.response.COMPETENCIASCNPCResult;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 10/01/2018.
 */

public class Negocio_COMPETENCIA {


    private Rutinas_COMPETENCIA rutinasCompetencia;
    private Seguridad sesion;

    private Context context;

    public Negocio_COMPETENCIA(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasCompetencia = new Rutinas_COMPETENCIA(context);
    }

    public ArrayList<Modelo_COMPENTENCIA> competenciasPorNumeral(String Numeral) {
        return rutinasCompetencia.competenciasPorNumeral(sesion.getIdiomaLargo(), Numeral);
    }

    public int countCompetenciasPorNumeral(String Numeral) {
        return rutinasCompetencia.countCompetenciasPorNumeral(Numeral);
    }

    public int sincronizar() {

        COMPETENCIASCNPCResponse response = null;
        try {

            response = RemoteClient.connect(context).sincronizarCOMPETENCIA(rutinasCompetencia.maxFecha());

            for (COMPETENCIASCNPCResult result : response.cOMPETENCIASCNPCResult) {
                Tabla_COMPETENCIA competencia = new Tabla_COMPETENCIA();
                competencia.ID = String.valueOf(result.iDCOMPETENCIA);
                competencia.COMPETENCIA_ESP = String.valueOf(result.cOMPETENCIAESP);
                competencia.COMPETENCIA_ENG = String.valueOf(result.cOMPETENCIAENG);
                competencia.INSTANCIA_ESP = String.valueOf(result.iNSTANCIAESP);
                competencia.INSTANCIA_ENG = String.valueOf(result.iNSTANCIAENG);
                competencia.VIGENTE = result.aCTIVOCOMPETENCIA;
                competencia.FECHA = String.valueOf(result.fECHAACTCOMPETENCIA);

                if (rutinasCompetencia.exists(competencia.ID))
                    rutinasCompetencia.update(competencia);
                else
                    rutinasCompetencia.create(competencia);
            }
            return response.cOMPETENCIASCNPCResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
