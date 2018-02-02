
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RNMCMEDIDACORRECTIVAResult implements Serializable
{

    @SerializedName("RNMC_ATRIBUCION_ENG")
    @Expose
    public String rNMCATRIBUCIONENG;
    @SerializedName("RNMC_ATRIBUCION_ESP")
    @Expose
    public String rNMCATRIBUCIONESP;
    @SerializedName("RNMC_MEDIDA_ENG")
    @Expose
    public String rNMCMEDIDAENG;
    @SerializedName("RNMC_MEDIDA_ESP")
    @Expose
    public String rNMCMEDIDAESP;
    @SerializedName("RNMC_VALOR_ENG")
    @Expose
    public String rNMCVALORENG;
    @SerializedName("RNMC_VALOR_ESP")
    @Expose
    public String rNMCVALORESP;
    private final static long serialVersionUID = -516121017884704821L;

}
