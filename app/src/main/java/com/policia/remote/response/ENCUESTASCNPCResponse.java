
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ENCUESTASCNPCResponse implements Serializable
{

    @SerializedName("ENCUESTAS_CNPCResult")
    @Expose
    public List<ENCUESTASCNPCResult> eNCUESTASCNPCResult = null;
    private final static long serialVersionUID = -9069792230835259621L;

}
