package com.policia.negocio.seguridad;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_SESION;
import com.policia.persistencia.rutinas.Rutinas_SESION;

/**
 * Created by 1085253556 on 7/12/2017.
 */

public class Seguridad {

    private Context context;
    private Rutinas_SESION rutinasSesion;

    private Seguridad(Context context) throws Exception {

        rutinasSesion = new Rutinas_SESION(context);
        Modelo_SESION sesion = rutinasSesion.Sesion();

        if (sesion == null)
            throw new Exception("No existe sesion");

        this.nombre = sesion.getUsuario();
        this.idioma = sesion.getIdioma();
    }

    private static Seguridad instancia;

    public static Seguridad Sesion(Context context) throws Exception {

        if (instancia == null)
            instancia = new Seguridad(context);

        return instancia;
    }

    private String nombre;
    private String idioma;

    public String getNombre() {
        return nombre;
    }

    public String getIdioma() {
        return idioma;
    }
}
