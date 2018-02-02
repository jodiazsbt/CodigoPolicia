
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ENCUESTASCNPCResult implements Serializable
{

    @SerializedName("ACTIVA_ENCUESTA")
    @Expose
    public boolean aCTIVAENCUESTA;
    @SerializedName("ID_ENCUESTA")
    @Expose
    public int iDENCUESTA;
    @SerializedName("PREGUNTA_ENCUESTAENG")
    @Expose
    public String pREGUNTAENCUESTAENG;
    @SerializedName("PREGUNTA_ENCUESTAESP")
    @Expose
    public String pREGUNTAENCUESTAESP;
    @SerializedName("RESPUESTA1ENG_ENCUESTA")
    @Expose
    public String rESPUESTA1ENGENCUESTA;
    @SerializedName("RESPUESTA1_ENCUESTA")
    @Expose
    public String rESPUESTA1ENCUESTA;
    @SerializedName("RESPUESTA2ENG_ENCUESTA")
    @Expose
    public String rESPUESTA2ENGENCUESTA;
    @SerializedName("RESPUESTA2_ENCUESTA")
    @Expose
    public String rESPUESTA2ENCUESTA;
    private final static long serialVersionUID = -7895107138898467892L;

}
