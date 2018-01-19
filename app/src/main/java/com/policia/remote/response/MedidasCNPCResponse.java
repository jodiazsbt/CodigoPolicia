
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MedidasCNPCResponse implements Serializable
{

    @SerializedName("MedidasCNPCResult")
    @Expose
    public List<MedidasCNPCResult> medidasCNPCResult = null;
    private final static long serialVersionUID = -7113478549972782691L;

}
