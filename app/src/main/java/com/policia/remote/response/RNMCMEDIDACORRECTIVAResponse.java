
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RNMCMEDIDACORRECTIVAResponse implements Serializable
{

    @SerializedName("RNMC_MEDIDA_CORRECTIVAResult")
    @Expose
    public List<RNMCMEDIDACORRECTIVAResult> rNMCMEDIDACORRECTIVAResult = null;
    private final static long serialVersionUID = -1132935626819934772L;

}
