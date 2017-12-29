
package com.policia.remote.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GEOPOCICIONCNPCResult implements Serializable
{

    @SerializedName("DireccionCoordanada")
    @Expose
    public String direccionCoordanada;
    @SerializedName("Distancia")
    @Expose
    public int distancia;
    @SerializedName("Latitud")
    @Expose
    public String latitud;
    @SerializedName("Longitud")
    @Expose
    public String longitud;
    @SerializedName("MunicipioCoordenada")
    @Expose
    public String municipioCoordenada;
    @SerializedName("NombreCoordenada")
    @Expose
    public String nombreCoordenada;
    private final static long serialVersionUID = -3603956048077365147L;

}
