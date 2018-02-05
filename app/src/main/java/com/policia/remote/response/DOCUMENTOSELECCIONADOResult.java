
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DOCUMENTOSELECCIONADOResult implements Serializable
{

    @SerializedName("Documento")
    @Expose
    public byte[] documento = null;
    @SerializedName("NombreArchivo")
    @Expose
    public String nombreArchivo;
    @SerializedName("extencion")
    @Expose
    public String extencion;
    private final static long serialVersionUID = 79027519345946992L;

}
