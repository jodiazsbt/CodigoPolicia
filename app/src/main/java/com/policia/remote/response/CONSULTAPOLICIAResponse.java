
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CONSULTAPOLICIAResponse implements Serializable
{

    @SerializedName("CONSULTAPOLICIAResult")
    @Expose
    public List<CONSULTAPOLICIAResult> cONSULTAPOLICIAResult = null;
    private final static long serialVersionUID = -5196995512323996941L;

}