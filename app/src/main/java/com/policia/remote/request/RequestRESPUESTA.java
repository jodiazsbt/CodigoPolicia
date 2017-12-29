package com.policia.remote.request;

/**
 * Created by 1085253556 on 29/12/2017.
 */

public class RequestRESPUESTA {

    public RequestRESPUESTA(String id, String depto, String respuesta) {

        this.ID = id;
        this.DEPARTAMENTO = depto;
        this.RESPUESTA = respuesta;
    }

    public String ID;
    public String DEPARTAMENTO;
    public String RESPUESTA;
}
