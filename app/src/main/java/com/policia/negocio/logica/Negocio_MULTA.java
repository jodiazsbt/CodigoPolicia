package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_MULTA;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_MULTA;
import com.policia.persistencia.tablas.Tabla_MULTA;
import com.policia.remote.RemoteClient;
import com.policia.remote.response.MULTAAARTICULOYPARAGRAFOResponse;
import com.policia.remote.response.MULTAAARTICULOYPARAGRAFOResult;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 24/11/2017.
 */

public class Negocio_MULTA {

    private Rutinas_MULTA rutinasMulta;
    private Seguridad sesion;

    private Context context;
    private RemoteClient remoteClient;

    public Negocio_MULTA(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasMulta = new Rutinas_MULTA(context);
    }

    public ArrayList<Modelo_MULTA> Multas() {
        return rutinasMulta.Multas(sesion.getIdiomaCodigo());
    }

    public int sincronizar() {

        remoteClient = new RemoteClient(context);
        MULTAAARTICULOYPARAGRAFOResponse response = null;
        try {

            response = remoteClient.sincronizarMULTA(rutinasMulta.maxFecha());

            for (MULTAAARTICULOYPARAGRAFOResult result : response.mULTAAARTICULOYPARAGRAFOResult) {
                Tabla_MULTA multa = new Tabla_MULTA();
                multa.NUMERAL_ID = String.valueOf(result.iDNUMERALMULTAAARTICULO);
                multa.MEDIDA_ID = String.valueOf(result.iDARTICULOYPARAGRAFOMULTAAARTICULO);
                multa.VIGENTE = result.vIGENTEMULTAAARTICULO;
                multa.FECHA = String.valueOf(result.fECHAACTMULTAAARTICULO);
                multa.TIPOMULTA_ID = String.valueOf(result.iDTIPOMULTAMULTAAARTIUCLO);
                multa.CATEGORIA_ID = String.valueOf(result.iDCATEGORIAMULTAAARTICULO);

                if (rutinasMulta.exists(multa.NUMERAL_ID, multa.MEDIDA_ID, multa.TIPOMULTA_ID))
                    rutinasMulta.update(multa);
                else
                    rutinasMulta.create(multa);
            }
            return response.mULTAAARTICULOYPARAGRAFOResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
