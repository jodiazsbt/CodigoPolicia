
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RELACIONCOMPETENCIANUMERALMEDIDACNPCResponse implements Serializable
{

    @SerializedName("RELACION_COMPETENCIA_NUMERAL_MEDIDA_CNPCResult")
    @Expose
    public List<RELACIONCOMPETENCIANUMERALMEDIDACNPCResult> rELACIONCOMPETENCIANUMERALMEDIDACNPCResult = null;
    private final static long serialVersionUID = -1967450743845797248L;

}
