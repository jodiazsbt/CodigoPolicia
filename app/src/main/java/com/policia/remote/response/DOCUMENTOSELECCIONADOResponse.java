
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DOCUMENTOSELECCIONADOResponse implements Serializable
{

    @SerializedName("DOCUMENTOSELECCIONADOResult")
    @Expose
    public List<DOCUMENTOSELECCIONADOResult> dOCUMENTOSELECCIONADOResult = null;
    private final static long serialVersionUID = -3361080241701855264L;

}
