
package com.policia.remote.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticulosyParagrafosResult implements Serializable
{

    @SerializedName("DescripcionArticuloyparagrafoENG")
    @Expose
    public String descripcionArticuloyparagrafoENG;
    @SerializedName("DescripcionArticuloyparagrafoESP")
    @Expose
    public String descripcionArticuloyparagrafoESP;
    @SerializedName("Fecha_Articuloyparagrafo")
    @Expose
    public String fechaArticuloyparagrafo;
    @SerializedName("ID_Articuloyparagrafo")
    @Expose
    public int iDArticuloyparagrafo;
    @SerializedName("ID_Capitulo_Articuloyparagrafo")
    @Expose
    public int iDCapituloArticuloyparagrafo;
    @SerializedName("ID_Nivel_Articuloyparagrafo")
    @Expose
    public int iDNivelArticuloyparagrafo;
    @SerializedName("NombreArticuloyparagrafoENG")
    @Expose
    public String nombreArticuloyparagrafoENG;
    @SerializedName("NombreArticuloyparagrafoESP")
    @Expose
    public String nombreArticuloyparagrafoESP;
    @SerializedName("Vigente_Articulo")
    @Expose
    public boolean vigenteArticulo;
    private final static long serialVersionUID = -7797287544202267500L;

}
