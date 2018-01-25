
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class COMPETENCIASCNPCResult implements Serializable
{

    @SerializedName("ACTIVO_COMPETENCIA")
    @Expose
    public boolean aCTIVOCOMPETENCIA;
    @SerializedName("COMPETENCIAENG")
    @Expose
    public String cOMPETENCIAENG;
    @SerializedName("COMPETENCIAESP")
    @Expose
    public String cOMPETENCIAESP;
    @SerializedName("FECHA_ACT_COMPETENCIA")
    @Expose
    public String fECHAACTCOMPETENCIA;
    @SerializedName("ID_COMPETENCIA")
    @Expose
    public int iDCOMPETENCIA;
    @SerializedName("INSTANCIAENG")
    @Expose
    public String iNSTANCIAENG;
    @SerializedName("INSTANCIAESP")
    @Expose
    public String iNSTANCIAESP;
    private final static long serialVersionUID = 7414026027744582837L;

}
