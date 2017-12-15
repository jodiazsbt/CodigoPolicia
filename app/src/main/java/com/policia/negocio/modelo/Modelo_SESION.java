package com.policia.negocio.modelo;

import java.util.Date;

/**
 * Created by 1085253556 on 7/12/2017.
 */

public class Modelo_SESION {

    private String usuario;

    private String funcionario;

    private String fisica;
    private String idiomaCodigo;
    private String idiomaNombre;
    private Date fecha;

    public Date getFecha() {

        return fecha;
    }

    public String getUsuario() {

        return usuario;
    }

    public String getFisica() {
        return fisica;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public String getIdiomaCodigo() {

        return idiomaCodigo;
    }

    public String getIdiomaNombre() {

        return idiomaNombre;
    }

    public void setUsuario(String usuario) {

        this.usuario = usuario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public void setIdiomaCodigo(String idioma) {

        this.idiomaCodigo = idioma;
    }

    public void setIdiomaNombre(String idioma) {

        this.idiomaNombre = idioma;
    }

    public void setFisica(String fisica) {
        this.fisica = fisica;
    }

    public void setFecha(Date fecha) {

        this.fecha = fecha;
    }

    public Modelo_SESION(){

    }
}
