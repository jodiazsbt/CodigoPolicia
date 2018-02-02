
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RNMCGENERALResponse implements Serializable
{

    @SerializedName("RNMC_GENERALResult")
    @Expose
    public List<RNMCGENERALResult> rNMCGENERALResult = null;
    private final static long serialVersionUID = -2402523134263915706L;

}
