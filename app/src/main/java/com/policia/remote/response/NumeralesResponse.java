
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NumeralesResponse implements Serializable
{

    @SerializedName("NumeralesResult")
    @Expose
    public List<NumeralesResult> numeralesResult = null;
    private final static long serialVersionUID = 8836869960089392161L;

}
