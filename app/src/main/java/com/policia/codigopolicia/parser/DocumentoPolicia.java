
package com.policia.codigopolicia.parser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DocumentoPolicia implements Serializable {

    @SerializedName("DocumentElement")
    @Expose
    private DocumentElement documentElement;
    private final static long serialVersionUID = 1867749476777285203L;

    public DocumentElement getDocumentElement() {
        return documentElement;
    }

    public void setDocumentElement(DocumentElement documentElement) {
        this.documentElement = documentElement;
    }

}