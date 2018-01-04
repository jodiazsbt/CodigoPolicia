
package com.policia.remote.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetadataResult implements Serializable
{

    @SerializedName("Fecha_Metadata")
    @Expose
    public String fechaMetadata;
    @SerializedName("ID_METADATA")
    @Expose
    public int iDMETADATA;
    @SerializedName("Id_Articuloyparagrafo_Metadata")
    @Expose
    public int idArticuloyparagrafoMetadata;
    @SerializedName("NombreDataBusquedaENG")
    @Expose
    public String nombreDataBusquedaENG;
    @SerializedName("NombreDataBusquedaESP")
    @Expose
    public String nombreDataBusquedaESP;
    @SerializedName("Vigente_Metadata")
    @Expose
    public boolean vigenteMetadata;
    private final static long serialVersionUID = 7494741826315631350L;

}
