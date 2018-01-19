
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NivelesResponse implements Serializable
{

    @SerializedName("NivelesResult")
    @Expose
    public List<NivelesResult> nivelesResult = null;
    private final static long serialVersionUID = 6610829798117219952L;

}
