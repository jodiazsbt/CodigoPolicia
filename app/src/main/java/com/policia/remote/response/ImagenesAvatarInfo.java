
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImagenesAvatarInfo implements Serializable {

    @SerializedName("AvatarNombre")
    @Expose
    public String avatarNombre;
    @SerializedName("ImagenAvatar")
    @Expose
    public byte[] imagenAvatar = null;
    @SerializedName("fechaact")
    @Expose
    public String fechaact;
    @SerializedName("id_avatar")
    @Expose
    public int idAvatar;
    private final static long serialVersionUID = 2528623670889692576L;

}
