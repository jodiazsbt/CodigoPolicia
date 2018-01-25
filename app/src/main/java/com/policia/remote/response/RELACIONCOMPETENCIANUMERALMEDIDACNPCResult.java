
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RELACIONCOMPETENCIANUMERALMEDIDACNPCResult implements Serializable
{

    @SerializedName("ACTIVO_RELCOMNUMMED")
    @Expose
    public boolean aCTIVORELCOMNUMMED;
    @SerializedName("FECHA_ACT_RELCOMPNUMMED")
    @Expose
    public String fECHAACTRELCOMPNUMMED;
    @SerializedName("ID_COMPETENCIA_RELNUMMED")
    @Expose
    public int iDCOMPETENCIARELNUMMED;
    @SerializedName("ID_NUMERAL_RELCOMNUMMED")
    @Expose
    public int iDNUMERALRELCOMNUMMED;
    @SerializedName("ID_RELCOMPNUMMED")
    @Expose
    public int iDRELCOMPNUMMED;
    private final static long serialVersionUID = -6570371071263139452L;

}
