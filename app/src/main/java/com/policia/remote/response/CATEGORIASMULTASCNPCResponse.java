
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CATEGORIASMULTASCNPCResponse implements Serializable
{

    @SerializedName("CATEGORIAS_MULTAS_CNPCResult")
    @Expose
    public List<CATEGORIASMULTASCNPCResult> cATEGORIASMULTASCNPCResult = null;
    private final static long serialVersionUID = 3287312943038574730L;

}
