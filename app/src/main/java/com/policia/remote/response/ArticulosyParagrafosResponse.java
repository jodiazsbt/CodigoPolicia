
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ArticulosyParagrafosResponse implements Serializable
{

    @SerializedName("ArticulosyParagrafosResult")
    @Expose
    public List<ArticulosyParagrafosResult> articulosyParagrafosResult = null;
    private final static long serialVersionUID = -8007217899280669075L;

}
