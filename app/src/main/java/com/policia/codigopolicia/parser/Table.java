package com.policia.codigopolicia.parser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Table implements Serializable {

    @SerializedName("E2")
    @Expose
    private Integer e2;
    @SerializedName("E4")
    @Expose
    private String e4;
    @SerializedName("E5")
    @Expose
    private String e5;
    @SerializedName("E8")
    @Expose
    private String e8;
    @SerializedName("E11")
    @Expose
    private String e11;
    @SerializedName("E13")
    @Expose
    private String e13;
    @SerializedName("E15")
    @Expose
    private Integer e15;
    @SerializedName("E16")
    @Expose
    private String e16;
    @SerializedName("E19")
    @Expose
    private String e19;
    @SerializedName("E21")
    @Expose
    private String e21;
    @SerializedName("E22")
    @Expose
    private String e22;
    @SerializedName("E25")
    @Expose
    private String e25;
    private final static long serialVersionUID = -6885315983501125375L;

    public Integer getE2() {
        return e2;
    }

    public void setE2(Integer e2) {
        this.e2 = e2;
    }

    public String getE4() {
        return e4;
    }

    public void setE4(String e4) {
        this.e4 = e4;
    }

    public String getE5() {
        return e5;
    }

    public void setE5(String e5) {
        this.e5 = e5;
    }

    public String getE8() {
        return e8;
    }

    public void setE8(String e8) {
        this.e8 = e8;
    }

    public String getE11() {
        return e11;
    }

    public void setE11(String e11) {
        this.e11 = e11;
    }

    public String getE13() {
        return e13;
    }

    public void setE13(String e13) {
        this.e13 = e13;
    }

    public Integer getE15() {
        return e15;
    }

    public void setE15(Integer e15) {
        this.e15 = e15;
    }

    public String getE16() {
        return e16;
    }

    public void setE16(String e16) {
        this.e16 = e16;
    }

    public String getE19() {
        return e19;
    }

    public void setE19(String e19) {
        this.e19 = e19;
    }

    public String getE21() {
        return e21;
    }

    public void setE21(String e21) {
        this.e21 = e21;
    }

    public String getE22() {
        return e22;
    }

    public void setE22(String e22) {
        this.e22 = e22;
    }

    public String getE25() {
        return e25;
    }

    public void setE25(String e25) {
        this.e25 = e25;
    }

}