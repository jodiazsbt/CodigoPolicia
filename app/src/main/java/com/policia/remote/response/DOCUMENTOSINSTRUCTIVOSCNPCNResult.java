
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DOCUMENTOSINSTRUCTIVOSCNPCNResult implements Serializable
{

    @SerializedName("ACTIVO_DOCUMENTOS")
    @Expose
    public boolean aCTIVODOCUMENTOS;
    @SerializedName("ID_DOCUMENTO")
    @Expose
    public int iDDOCUMENTO;
    @SerializedName("ID_TIPODOARCHIVO")
    @Expose
    public int iDTIPODOARCHIVO;
    @SerializedName("NOMBRE_DOCUMENTO")
    @Expose
    public String nOMBREDOCUMENTO;
    @SerializedName("URL")
    @Expose
    public String uRL;
    private final static long serialVersionUID = 4149744746873363436L;

}
