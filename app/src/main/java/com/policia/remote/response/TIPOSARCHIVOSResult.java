
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TIPOSARCHIVOSResult implements Serializable
{

    @SerializedName("ACTIVO_TIPO_ARCHIVO")
    @Expose
    public boolean aCTIVOTIPOARCHIVO;
    @SerializedName("FECHA_ACT_TIPO_ARCHIVO")
    @Expose
    public String fECHAACTTIPOARCHIVO;
    @SerializedName("ID_TIPO_ARCHIVO")
    @Expose
    public int iDTIPOARCHIVO;
    @SerializedName("TIPO_ARCHIVOENG")
    @Expose
    public String tIPOARCHIVOENG;
    @SerializedName("TIPO_ARCHIVOESP")
    @Expose
    public String tIPOARCHIVOESP;
    private final static long serialVersionUID = -5954842856519132851L;

}
