
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NivelesResult implements Serializable
{

    @SerializedName("Fecha_Nivel")
    @Expose
    public String fechaNivel;
    @SerializedName("Id_Nivel")
    @Expose
    public int idNivel;
    @SerializedName("NombreNivelENG")
    @Expose
    public String nombreNivelENG;
    @SerializedName("NombreNivelESP")
    @Expose
    public String nombreNivelESP;
    @SerializedName("Vigente_Nivel")
    @Expose
    public boolean vigenteNivel;
    private final static long serialVersionUID = -6655614439356092489L;

}
