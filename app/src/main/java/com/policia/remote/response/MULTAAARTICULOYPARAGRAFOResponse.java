
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MULTAAARTICULOYPARAGRAFOResponse implements Serializable
{

    @SerializedName("MULTA_A_ARTICULOYPARAGRAFOResult")
    @Expose
    public List<MULTAAARTICULOYPARAGRAFOResult> mULTAAARTICULOYPARAGRAFOResult = null;
    private final static long serialVersionUID = 299018910001334887L;

}
