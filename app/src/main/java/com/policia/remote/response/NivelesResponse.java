
package com.policia.remote.response;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NivelesResponse implements Serializable
{

    @SerializedName("NivelesResult")
    @Expose
    public List<NivelesResult> nivelesResult = null;
    private final static long serialVersionUID = 6610829798117219952L;

}
