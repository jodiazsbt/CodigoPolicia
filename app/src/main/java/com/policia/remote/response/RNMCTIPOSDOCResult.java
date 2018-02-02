
package com.policia.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RNMCTIPOSDOCResult implements Serializable {

    @SerializedName("DESCRIPCION_TIPOS_DOC")
    @Expose
    public String dESCRIPCIONTIPOSDOC;
    @SerializedName("ID_TIPOS_DOC")
    @Expose
    public int iDTIPOSDOC;
    private final static long serialVersionUID = 5886679842094058580L;

    public RNMCTIPOSDOCResult() {
    }

    @Override
    public String toString() {
        return dESCRIPCIONTIPOSDOC;
    }
}
