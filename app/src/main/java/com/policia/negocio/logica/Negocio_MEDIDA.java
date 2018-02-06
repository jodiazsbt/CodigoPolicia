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

            for (MedidasCNPCResult result : response.medidasCNPCResult) {
                Tabla_MEDIDA medida = new Tabla_MEDIDA();
                medida.ID = String.valueOf(result.iDMEDIDA);
                medida.COMPORTAMIENTO_ESP = String.valueOf(result.nombreComportamientoMedidasCNPCESP);
                medida.MEDIDA_ESP = String.valueOf(result.nombreMedidaCorrectivaMedidasCNPCESP);
                medida.VIGENTE = result.vigenteMedidasCNPC;
                medida.NIVEL_ID = String.valueOf(result.iDNivelMedidasCNPC);
                medida.ARTICULO_ID = String.valueOf(result.idARTICULOYPARAGRAFOMedidasCNPC);
                medida.COMPORTAMIENTO_ENG = String.valueOf(result.nombreComportamientoMedidasCNPCENG);
                medida.MEDIDA_ENG = String.valueOf(result.nombreMedidaCorrectivaMedidasCNPCENG);
                medida.FECHA = String.valueOf(result.fechaMediadasCNPC);

                if (rutinasMedida.exists(medida.ID))
                    rutinasMedida.update(medida);
                else
                    rutinasMedida.create(medida);
            }
            return response.medidasCNPCResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Modelo_MEDIDA> MedidasPorParagrafo(String Paragrafo) {

        return rutinasMedida.MedidasPorParagrafo(sesion.getIdiomaLargo(), Paragrafo);
    }

    public ArrayList<Modelo_MEDIDA> ComparendosNumeral(String Numeral) {

        return rutinasMedida.ComparendosNumeral(sesion.getIdiomaLargo(), Numeral);
    }

    public int countComparendosNumeral(String Numeral) {

        return rutinasMedida.countComparendosNumeral(Numeral);
    }
}
