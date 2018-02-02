
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RNMCDETALLECOMPORTAMIENTOResponse implements Serializable
{

    @SerializedName("RNMC_DETALLE_COMPORTAMIENTOResult")
    @Expose
    public List<RNMCDETALLECOMPORTAMIENTOResult> rNMCDETALLECOMPORTAMIENTOResult = null;
    private final static long serialVersionUID = -294147875732048376L;

}
