package com.policia.negocio.modelo;

/**
 * Created by 1085253556 on 9/02/2018.
 */

public class Modelo_TIPO_DOCUMENTO {

    public Modelo_TIPO_DOCUMENTO(String ID, String TIPO_DOCUMENTO) {

        this.ID = ID;
        this.TIPO_DOCUMENTO = TIPO_DOCUMENTO;
    }

    public String ID;
    public String TIPO_DOCUMENTO;

    @Override
    public String toString() {
        return TIPO_DOCUMENTO;
    }
}
