
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ACCIONESCIUDADANOYPOLICIACNCPResult implements Serializable
{

    @SerializedName("ACCIONESCIUDADANOYPOLICIA_CNCPResult")
    @Expose
    public List<ACCIONESCIUDADANOYPOLICIACNCPResult_> aCCIONESCIUDADANOYPOLICIACNCPResult = null;
    private final static long serialVersionUID = -1884883618883422255L;

}
