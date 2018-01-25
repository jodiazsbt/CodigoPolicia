
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UVTCNCPResult_ implements Serializable
{

    @SerializedName("ACTIVO_UVT_CNCP")
    @Expose
    public boolean aCTIVOUVTCNCP;
    @SerializedName("A\u00d1O_UVT_CNCP")
    @Expose
    public int aOUVTCNCP;
    @SerializedName("FECHA_ACT_UVT_CNCP")
    @Expose
    public String fECHAACTUVTCNCP;
    @SerializedName("ID_UVT_CNCP")
    @Expose
    public int iDUVTCNCP;
    @SerializedName("UVT_VALOR")
    @Expose
    public int uVTVALOR;
    private final static long serialVersionUID = -51916978793685569L;

}
