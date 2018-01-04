package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_MEDIDA;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_MEDIDA;
import com.policia.persistencia.tablas.Tabla_MEDIDA;
import com.policia.remote.RemoteClient;
import com.policia.remote.response.MedidasCNPCResponse;
import com.policia.remote.response.MedidasCNPCResult;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 17/12/2017.
 */

public class Negocio_MEDIDA {

    private Rutinas_MEDIDA rutinasMedida;
    private Seguridad sesion;

    private Context context;
    private RemoteClient remoteClient;

    public Negocio_MEDIDA(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasMedida = new Rutinas_MEDIDA(context);
    }

    public int sincronizar() {

        remoteClient = new RemoteClient(context);
        MedidasCNPCResponse response = null;
        try {

            response = remoteClient.sincronizarMEDIDA(rutinasMedida.maxFecha());

            for (MedidasCNPCResult medida : response.medidasCNPCResult) {
                Tabla_MEDIDA tablaMedida = new Tabla_MEDIDA();
                tablaMedida.ID = String.valueOf(medida.iDMEDIDA);
                tablaMedida.COMPORTAMIENTO_ESP = String.valueOf(medida.nombreComportamientoMedidasCNPCESP);
                tablaMedida.MEDIDA_ESP = String.valueOf(medida.nombreMedidaCorrectivaMedidasCNPCESP);
                tablaMedida.VIGENTE = medida.vigenteMedidasCNPC;
                tablaMedida.NIVEL_ID = String.valueOf(medida.iDNivelMedidasCNPC);
                tablaMedida.ARTICULO_ID = String.valueOf(medida.idARTICULOYPARAGRAFOMedidasCNPC);
                tablaMedida.COMPORTAMIENTO_ENG = String.valueOf(medida.nombreComportamientoMedidasCNPCENG);
                tablaMedida.MEDIDA_ENG = String.valueOf(medida.nombreMedidaCorrectivaMedidasCNPCENG);
                tablaMedida.FECHA = String.valueOf(medida.fechaMediadasCNPC);
                rutinasMedida.update(tablaMedida);
            }
            return response.medidasCNPCResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Modelo_MEDIDA> MedidasPorParagrafo(String Paragrafo) {

        return rutinasMedida.MedidasPorParagrafo(sesion.getIdiomaCodigo(), Paragrafo);
    }
}
