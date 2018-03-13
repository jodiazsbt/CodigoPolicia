
package com.policia.remote.response;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PoliciaPerfilResponse implements Serializable, Parcelable
{

    @SerializedName("identificacion")
    @Expose
    public String identificacion;
    @SerializedName("siglaPapa")
    @Expose
    public String siglaPapa;
    @SerializedName("siglaFisica")
    @Expose
    public String siglaFisica;
    @SerializedName("gradoAlfabetico")
    @Expose
    public String gradoAlfabetico;
    @SerializedName("apellidos")
    @Expose
    public String apellidos;
    @SerializedName("nombres")
    @Expose
    public String nombres;
    @SerializedName("situacionLaboral")
    @Expose
    public String situacionLaboral;
    @SerializedName("nombreGrado")
    @Expose
    public String nombreGrado;
    @SerializedName("cargoActual")
    @Expose
    public String cargoActual;
    @SerializedName("consecutivo")
    @Expose
    public String consecutivo;
    @SerializedName("undeConsecutivo")
    @Expose
    public String undeConsecutivo;
    @SerializedName("undeFuerza")
    @Expose
    public String undeFuerza;
    @SerializedName("funcionario")
    @Expose
    public String funcionario;
    @SerializedName("unidad")
    @Expose
    public String unidad;
    @SerializedName("placa")
    @Expose
    public String placa;
    @SerializedName("undeConsecutivoLaborando")
    @Expose
    public String undeConsecutivoLaborando;
    public final static Creator<PoliciaPerfilResponse> CREATOR = new Creator<PoliciaPerfilResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PoliciaPerfilResponse createFromParcel(Parcel in) {
            return new PoliciaPerfilResponse(in);
        }

        public PoliciaPerfilResponse[] newArray(int size) {
            return (new PoliciaPerfilResponse[size]);
        }

    }
    ;
    private final static long serialVersionUID = -1483129843080704L;

    protected PoliciaPerfilResponse(Parcel in) {
        this.identificacion = ((String) in.readValue((int.class.getClassLoader())));
        this.siglaPapa = ((String) in.readValue((String.class.getClassLoader())));
        this.siglaFisica = ((String) in.readValue((String.class.getClassLoader())));
        this.gradoAlfabetico = ((String) in.readValue((String.class.getClassLoader())));
        this.apellidos = ((String) in.readValue((String.class.getClassLoader())));
        this.nombres = ((String) in.readValue((String.class.getClassLoader())));
        this.situacionLaboral = ((String) in.readValue((String.class.getClassLoader())));
        this.nombreGrado = ((String) in.readValue((String.class.getClassLoader())));
        this.cargoActual = ((String) in.readValue((String.class.getClassLoader())));
        this.consecutivo = ((String) in.readValue((int.class.getClassLoader())));
        this.undeConsecutivo = ((String) in.readValue((int.class.getClassLoader())));
        this.undeFuerza = ((String) in.readValue((int.class.getClassLoader())));
        this.funcionario = ((String) in.readValue((String.class.getClassLoader())));
        this.unidad = ((String) in.readValue((String.class.getClassLoader())));
        this.placa = ((String) in.readValue((int.class.getClassLoader())));
        this.undeConsecutivoLaborando = ((String) in.readValue((int.class.getClassLoader())));
    }

    public PoliciaPerfilResponse() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(identificacion);
        dest.writeValue(siglaPapa);
        dest.writeValue(siglaFisica);
        dest.writeValue(gradoAlfabetico);
        dest.writeValue(apellidos);
        dest.writeValue(nombres);
        dest.writeValue(situacionLaboral);
        dest.writeValue(nombreGrado);
        dest.writeValue(cargoActual);
        dest.writeValue(consecutivo);
        dest.writeValue(undeConsecutivo);
        dest.writeValue(undeFuerza);
        dest.writeValue(funcionario);
        dest.writeValue(unidad);
        dest.writeValue(placa);
        dest.writeValue(undeConsecutivoLaborando);
    }

    public int describeContents() {
        return  0;
    }

}
