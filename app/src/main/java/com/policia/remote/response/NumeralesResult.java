
package com.policia.remote.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NumeralesResult implements Serializable
{

    @SerializedName("Fecha_Numeral")
    @Expose
    public String fechaNumeral;
    @SerializedName("ID_NUMERAL")
    @Expose
    public int iDNUMERAL;
    @SerializedName("Id_Articulosyparagrafos_Numeral")
    @Expose
    public int idArticulosyparagrafosNumeral;
    @SerializedName("Id_Nivel_Numeral")
    @Expose
    public int idNivelNumeral;
    @SerializedName("NombreDescripcionNumeralENG")
    @Expose
    public String nombreDescripcionNumeralENG;
    @SerializedName("NombreDescripcionNumeralESP")
    @Expose
    public String nombreDescripcionNumeralESP;
    @SerializedName("Vigente_Numeral")
    @Expose
    public boolean vigenteNumeral;
    private final static long serialVersionUID = 1343622452982140725L;

}
