
package com.policia.remote.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RNMCGENERAL2Response {

    @SerializedName("RNMC_GENERAL2Result")
    @Expose
    public List<RNMCGENERAL2Result> rNMCGENERAL2Result = null;

}
