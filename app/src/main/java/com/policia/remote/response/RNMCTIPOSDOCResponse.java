
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RNMCTIPOSDOCResponse implements Serializable {

    @SerializedName("RNMC_TIPOS_DOCResult")
    @Expose
    public List<RNMCTIPOSDOCResult> rNMCTIPOSDOCResult = null;
    private final static long serialVersionUID = 3927552131439167836L;

}
