
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TIPOSARCHIVOSResponse implements Serializable
{

    @SerializedName("TIPOS_ARCHIVOSResult")
    @Expose
    public List<TIPOSARCHIVOSResult> tIPOSARCHIVOSResult = null;
    private final static long serialVersionUID = 5498599873684686107L;

}
