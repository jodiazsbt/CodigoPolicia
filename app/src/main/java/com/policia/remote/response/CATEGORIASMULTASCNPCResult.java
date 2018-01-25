
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CATEGORIASMULTASCNPCResult implements Serializable
{

    @SerializedName("ACTIVO_CATEGOTRIAS")
    @Expose
    public boolean aCTIVOCATEGOTRIAS;
    @SerializedName("CATEGORIAENG")
    @Expose
    public String cATEGORIAENG;
    @SerializedName("CATEGORIAESP")
    @Expose
    public String cATEGORIAESP;
    @SerializedName("FECHA_ACT_CATEGORIA")
    @Expose
    public String fECHAACTCATEGORIA;
    @SerializedName("ID_CATEGORIA")
    @Expose
    public int iDCATEGORIA;
    private final static long serialVersionUID = 28521120745616533L;

}
