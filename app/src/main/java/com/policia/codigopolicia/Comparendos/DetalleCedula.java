package com.policia.codigopolicia.Comparendos;

import com.policia.negocio.modelo.ValuePar;
import com.policia.remote.response.RNMCGENERAL2Result;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 21/03/2018.
 */

public class DetalleCedula {

    private ArrayList<ValuePar> valores;
    private RNMCGENERAL2Result comportamiento;

    public DetalleCedula(RNMCGENERAL2Result comportamiento) {

        this.comportamiento = comportamiento;

        valores = new ArrayList<>();
        valores.add(new ValuePar("TIPO DOCUMENTO", comportamiento.dESCRIPCIONRNMCGENERAL));
        valores.add(new ValuePar("IDENTIFICACION", comportamiento.iDENTIFICACIONRNMCGENERAL));
        valores.add(new ValuePar("INFRACTOR", comportamiento.fORMATORNMCGENERAL));
        valores.add(new ValuePar("FECHA", comportamiento.fECHAHECHOSRNMCGENERAL));
        valores.add(new ValuePar("DEPARTAMENTO", comportamiento.dTORNMCGENERAL));
        valores.add(new ValuePar("MUNICIPIO", comportamiento.mUNICIPIORNMCGENERAL));
    }

    public int count() {

        return valores.size();
    }

    public int getID() {

        return comportamiento.iDCOMPORTAMIENTORNMCGENERAL;
    }

    public String getExpediente() {

        return comportamiento.eXPEDIENTERNMCGENERAL;
    }

    public String getComportamiento() {

        return String.valueOf(comportamiento.iDCOMPORTAMIENTORNMCGENERAL);
    }

    public String getLabel(int posicion) {

        return valores.get(posicion).getLabel();
    }

    public String getValor(int posicion) {

        return valores.get(posicion).getValue();
    }
}
