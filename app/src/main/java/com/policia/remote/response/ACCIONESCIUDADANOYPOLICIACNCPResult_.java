
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ACCIONESCIUDADANOYPOLICIACNCPResult_ implements Serializable
{

    @SerializedName("ACTIVO_CIUDADANOYPOLICIA")
    @Expose
    public boolean aCTIVOCIUDADANOYPOLICIA;
    @SerializedName("DESCRIPCION_CIUDADANOYPOLICIAENG")
    @Expose
    public String dESCRIPCIONCIUDADANOYPOLICIAENG;
    @SerializedName("DESCRIPCION_CIUDADANOYPOLICIAESP")
    @Expose
    public String dESCRIPCIONCIUDADANOYPOLICIAESP;
    @SerializedName("FECHA_ACT_CIUDADANOYPOLICIA")
    @Expose
    public String fECHAACTCIUDADANOYPOLICIA;
    @SerializedName("ID_CIUDADANOYPOLICIA")
    @Expose
    public int iDCIUDADANOYPOLICIA;
    private final static long serialVersionUID = -8894224965348259646L;

}
