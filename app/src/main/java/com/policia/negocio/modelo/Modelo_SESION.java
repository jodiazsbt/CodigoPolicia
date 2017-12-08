package com.policia.negocio.modelo;

import java.util.Date;

/**
 * Created by 1085253556 on 7/12/2017.
 */

public class Modelo_SESION {

    private String usuario;
    private String idioma;
    private Date fecha;

    public Date getFecha() {

        return fecha;
    }

    public String getUsuario() {

        return usuario;
    }

    public String getIdioma() {

        return idioma;
    }

    public void setUsuario(String usuario) {

        this.usuario = usuario;
    }

    public void setIdioma(String idioma) {

        this.idioma = idioma;
    }

    public void setFecha(Date fecha) {

        this.fecha = fecha;
    }

    public Modelo_SESION(){

    }
}
