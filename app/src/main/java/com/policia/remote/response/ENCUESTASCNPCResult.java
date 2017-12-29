
package com.policia.remote.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    private final static long serialVersionUID = -7391686738681291178L;

}
