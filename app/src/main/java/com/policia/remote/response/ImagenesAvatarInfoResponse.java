
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ImagenesAvatarInfoResponse implements Serializable {

    @SerializedName("ImagenesAvatarInfo")
    @Expose
    public List<ImagenesAvatarInfo> imagenesAvatarInfo = null;
    private final static long serialVersionUID = -5018626745783964311L;

}
