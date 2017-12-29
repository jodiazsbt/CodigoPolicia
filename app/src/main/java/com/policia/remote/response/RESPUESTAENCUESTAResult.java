
package com.policia.remote.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RESPUESTAENCUESTAResult implements Serializable
{

    @SerializedName("respuestaenc")
    @Expose
    public String respuestaenc;
    private final static long serialVersionUID = 1312607472815569267L;

}
