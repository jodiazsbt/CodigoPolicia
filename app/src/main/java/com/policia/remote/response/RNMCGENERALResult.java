
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RNMCGENERALResult implements Serializable
{

    @SerializedName("DESCRIPCION_RNMC_GENERAL")
    @Expose
    public String dESCRIPCIONRNMCGENERAL;
    @SerializedName("DTO_RNMC_GENERAL")
    @Expose
    public String dTORNMCGENERAL;
    @SerializedName("EXPEDIENTE_RNMC_GENERAL")
    @Expose
    public String eXPEDIENTERNMCGENERAL;
    @SerializedName("FECHA_HECHOS_RNMC_GENERAL")
    @Expose
    public String fECHAHECHOSRNMCGENERAL;
    @SerializedName("FORMATO_RNMC_GENERAL")
    @Expose
    public String fORMATORNMCGENERAL;
    @SerializedName("IDENTIFICACION_RNMC_GENERAL")
    @Expose
    public String iDENTIFICACIONRNMCGENERAL;
    @SerializedName("ID_COMPORTAMIENTO_RNMC_GENERAL")
    @Expose
    public int iDCOMPORTAMIENTORNMCGENERAL;
    @SerializedName("ID_HECHOS_RNMC_GENERAL")
    @Expose
    public String iDHECHOSRNMCGENERAL;
    @SerializedName("MUNICIPIO_RNMC_GENERAL")
    @Expose
    public String mUNICIPIORNMCGENERAL;
    @SerializedName("SIGLA_RNMC_GENERAL")
    @Expose
    public String sIGLARNMCGENERAL;
    private final static long serialVersionUID = -3805825461553112955L;

}
