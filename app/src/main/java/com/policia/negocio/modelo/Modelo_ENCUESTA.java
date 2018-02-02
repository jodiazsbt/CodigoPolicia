package com.policia.negocio.modelo;

/**
 * Created by 1085253556 on 29/12/2017.
 */

public class Modelo_ENCUESTA {

    public Modelo_ENCUESTA(String ID, String Pregunta, String Respuesta_SI, String Respuesta_NO) {

        this.ID = ID;
        this.Pregunta = Pregunta;
        this.Respuesta_SI = Respuesta_SI;
        this.Respuesta_NO = Respuesta_NO;
    }

    public String ID;
    public String Pregunta;
    public String Respuesta_SI;
    public String Respuesta_NO;
}
