
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DOCUMENTOSINSTRUCTIVOSCNPCNResponse implements Serializable
{

    @SerializedName("DOCUMENTOS_INSTRUCTIVOS_CNPCNResult")
    @Expose
    public List<DOCUMENTOSINSTRUCTIVOSCNPCNResult> dOCUMENTOSINSTRUCTIVOSCNPCNResult = null;
    private final static long serialVersionUID = -1738871958898829207L;

}
