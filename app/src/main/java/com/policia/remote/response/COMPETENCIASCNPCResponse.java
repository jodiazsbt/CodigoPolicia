
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class COMPETENCIASCNPCResponse implements Serializable
{

    @SerializedName("COMPETENCIAS_CNPCResult")
    @Expose
    public List<COMPETENCIASCNPCResult> cOMPETENCIASCNPCResult = null;
    private final static long serialVersionUID = 8512897062595530675L;

}
