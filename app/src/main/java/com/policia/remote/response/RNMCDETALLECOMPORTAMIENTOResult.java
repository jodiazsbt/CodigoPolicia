
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RNMCDETALLECOMPORTAMIENTOResult implements Serializable
{

    @SerializedName("ARTICULO_COMPORTAMIENTO_RNMC_ENG")
    @Expose
    public String aRTICULOCOMPORTAMIENTORNMCENG;
    @SerializedName("ARTICULO_COMPORTAMIENTO_RNMC_ESP")
    @Expose
    public String aRTICULOCOMPORTAMIENTORNMCESP;
    @SerializedName("EXPEDIENTE_COMPORTAMIENTO_RNMC")
    @Expose
    public String eXPEDIENTECOMPORTAMIENTORNMC;
    @SerializedName("NUMERAL_COMPORTAMIENTO_RNMC_ENG")
    @Expose
    public String nUMERALCOMPORTAMIENTORNMCENG;
    @SerializedName("NUMERAL_COMPORTAMIENTO_RNMC_ESP")
    @Expose
    public String nUMERALCOMPORTAMIENTORNMCESP;
    private final static long serialVersionUID = 2327693455455511350L;

}
