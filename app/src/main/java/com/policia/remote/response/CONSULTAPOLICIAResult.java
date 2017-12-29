
package com.policia.remote.response;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
