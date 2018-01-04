
package com.policia.remote.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MULTAAARTICULOYPARAGRAFOResult implements Serializable
{

    @SerializedName("FECHA_ACT_MULTAAARTICULO")
    @Expose
    public String fECHAACTMULTAAARTICULO;
    @SerializedName("ID_ARTICULOYPARAGRAFO_MULTAAARTICULO")
    @Expose
    public int iDARTICULOYPARAGRAFOMULTAAARTICULO;
    @SerializedName("ID_NUMERAL_MULTAAARTICULO")
    @Expose
    public int iDNUMERALMULTAAARTICULO;
    @SerializedName("ID_TIPOMULTA_MULTAAARTIUCLO")
    @Expose
    public int iDTIPOMULTAMULTAAARTIUCLO;
    @SerializedName("VIGENTE_MULTAAARTICULO")
    @Expose
    public boolean vIGENTEMULTAAARTICULO;
    private final static long serialVersionUID = 5215968868769268868L;

}
