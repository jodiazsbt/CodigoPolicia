
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RESPUESTAENCUESTAResponse implements Serializable
{

    @SerializedName("RESPUESTAENCUESTAResult")
    @Expose
    public List<RESPUESTAENCUESTAResult> rESPUESTAENCUESTAResult = null;
    private final static long serialVersionUID = 4526859627722047739L;

}
