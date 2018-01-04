
package com.policia.remote.response;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MULTAAARTICULOYPARAGRAFOResponse implements Serializable
{

    @SerializedName("MULTA_A_ARTICULOYPARAGRAFOResult")
    @Expose
    public List<MULTAAARTICULOYPARAGRAFOResult> mULTAAARTICULOYPARAGRAFOResult = null;
    private final static long serialVersionUID = 299018910001334887L;

}
