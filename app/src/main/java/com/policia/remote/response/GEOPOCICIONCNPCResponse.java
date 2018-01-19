
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GEOPOCICIONCNPCResponse implements Serializable
{

    @SerializedName("GEOPOCICION_CNPCResult")
    @Expose
    public List<GEOPOCICIONCNPCResult> gEOPOCICIONCNPCResult = null;
    private final static long serialVersionUID = 5837076099485811335L;

}
