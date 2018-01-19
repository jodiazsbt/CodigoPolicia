
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CONSULTAPOLICIAResult implements Serializable
{

    @SerializedName("Imagen")
    @Expose
    public byte[] imagen = null;
    @SerializedName("estado")
    @Expose
    public String estado;
    @SerializedName("mensaje")
    @Expose
    public String mensaje;
    @SerializedName("unidad")
    @Expose
    public String unidad;
    private final static long serialVersionUID = 870965369008975582L;

}
