
package com.policia.remote.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedidasCNPCResult implements Serializable
{

    @SerializedName("Fecha_Mediadas_CNPC")
    @Expose
    public String fechaMediadasCNPC;
    @SerializedName("ID_MEDIDA")
    @Expose
    public int iDMEDIDA;
    @SerializedName("ID_Nivel_Medidas_CNPC")
    @Expose
    public int iDNivelMedidasCNPC;
    @SerializedName("Id_ARTICULOYPARAGRAFOMedidas_CNPC")
    @Expose
    public int idARTICULOYPARAGRAFOMedidasCNPC;
    @SerializedName("NombreComportamientoMedidasCNPCENG")
    @Expose
    public String nombreComportamientoMedidasCNPCENG;
    @SerializedName("NombreComportamientoMedidasCNPCESP")
    @Expose
    public String nombreComportamientoMedidasCNPCESP;
    @SerializedName("NombreMedidaCorrectivaMedidasCNPCENG")
    @Expose
    public String nombreMedidaCorrectivaMedidasCNPCENG;
    @SerializedName("NombreMedidaCorrectivaMedidasCNPCESP")
    @Expose
    public String nombreMedidaCorrectivaMedidasCNPCESP;
    @SerializedName("Vigente_Medidas_CNPC")
    @Expose
    public boolean vigenteMedidasCNPC;
    private final static long serialVersionUID = 852505810728494317L;

}
