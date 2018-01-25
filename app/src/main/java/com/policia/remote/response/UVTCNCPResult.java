
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UVTCNCPResult implements Serializable
{

    @SerializedName("UVT_CNCPResult")
    @Expose
    public List<UVTCNCPResult_> uVTCNCPResult = null;
    private final static long serialVersionUID = 4125690306501151562L;

}
