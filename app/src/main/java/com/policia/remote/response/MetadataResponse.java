
package com.policia.remote.response;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetadataResponse implements Serializable
{

    @SerializedName("MetadataResult")
    @Expose
    public List<MetadataResult> metadataResult = null;
    private final static long serialVersionUID = 8426818774217339896L;

}
