package com.policia.negocio.seguridad;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.policia.codigopolicia.R;
import com.policia.negocio.modelo.Modelo_SESION;
import com.policia.persistencia.rutinas.Rutinas_PREFERENCIA;
import com.policia.persistencia.rutinas.Rutinas_SESION;
import com.policia.persistencia.rutinas.Rutinas_USUARIO;
import com.policia.persistencia.tablas.Tabla_PREFERENCIA;
import com.policia.persistencia.tablas.Tabla_SESION;
import com.policia.persistencia.tablas.Tabla_USUARIO;
import com.policia.remote.response.LoginPoliciaNalResult;

import java.util.Date;
import java.util.Locale;

/**
 * Created by 1085253556 on 7/12/2017.
 */

public class Seguridad {

    private Context context;
    private Rutinas_SESION rutinasSesion;
    private Rutinas_PREFERENCIA rutinasPreferencia;
    private Rutinas_USUARIO rutinasUsuario;

    private Seguridad(Context context) throws Exception {

        this.context = context;

        rutinasSesion = new Rutinas_SESION(context);
        rutinasUsuario = new Rutinas_USUARIO(context);
        rutinasPreferencia = new Rutinas_PREFERENCIA(context);

        actualizarSesion();
    }

    private void actualizarSesion() throws Exception {

        Modelo_SESION sesion = rutinasSesion.Sesion();

        if (sesion == null)
            throw new Exception("No existe sesion");

        this.usuario = sesion.getUsuario();
        this.funcionario = sesion.getFuncionario();
        this.fisica = sesion.getFisica();
        this.idiomaCodigo = sesion.getIdiomaCodigo();
        this.idiomaNombre = sesion.getIdiomaNombre();
    }

    private static Seguridad instancia;

    public static Seguridad Sesion(Context context) throws Exception {

        if (instancia == null)
            instancia = new Seguridad(context);

        return instancia;
    }

    private String usuario;
    private String funcionario;
    private String fisica;

    private String idiomaCodigo;
    private String idiomaNombre;

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

    public boolean actualizarIdiomaSesion(String idioma) throws Exception {

        Locale locale = new Locale(idioma);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);

        Tabla_PREFERENCIA preferencia = new Tabla_PREFERENCIA();
        preferencia.USUARIO_ID = this.usuario;
        preferencia.IDIOMA_CODIGO = idioma;
        rutinasPreferencia.update(preferencia);

        actualizarSesion();
        return true;
    }

    public boolean cerrarSesionPolicia() throws Exception {

        Tabla_SESION tablaSesion = new Tabla_SESION();
        tablaSesion.USUARIO_ID = "1";
        tablaSesion.FECHA = new Date();

        rutinasSesion.borrarSesiones();
        rutinasSesion.crearSesion(tablaSesion);

        actualizarSesion();

        return true;
    }

    public boolean abrirSesionPolicia(LoginPoliciaNalResult usuario) throws Exception {

        String usuarioID = "0";

        Tabla_SESION tablaSesion = null;
        Tabla_USUARIO tablaUsuario = null;
        Tabla_PREFERENCIA tablaPreferencia = null;

        if (!rutinasUsuario.existeUsuario(usuario.Placa)) {

            tablaUsuario = new Tabla_USUARIO();
            tablaUsuario.CONSECUTIVO = usuario.Consecutivo;
            tablaUsuario.FISICA = usuario.Fisica;
            tablaUsuario.FUNCIONARIO = usuario.Funcionario;
            tablaUsuario.GRADO = usuario.Grado;
            tablaUsuario.IDENTIFICACION = usuario.Identificacion;
            tablaUsuario.PLACA = usuario.Placa;
            tablaUsuario.UNDECONSECUTIVO = usuario.UndeConsecutivo;
            tablaUsuario.UNDEFUERZA = usuario.UndeFuerza;
            tablaUsuario.UNDELABORA = usuario.UnderLabora;
            tablaUsuario.UNIDAD = usuario.Unidad;
            tablaUsuario.VERIFICA = usuario.Verifica == 1;

            rutinasUsuario.crearUsuario(tablaUsuario);
        }

        usuarioID = rutinasUsuario.usuarioID(usuario.Placa);

        if (!rutinasPreferencia.existePreferenciaUsuario(usuarioID)) {

            tablaPreferencia = new Tabla_PREFERENCIA();
            tablaPreferencia.USUARIO_ID = usuarioID;
            tablaPreferencia.IDIOMA_CODIGO = context.getString(R.string.preferencia_idioma);

            rutinasPreferencia.crearPreferencia(tablaPreferencia);
        }

        if (!rutinasSesion.exists(usuarioID)) {

            tablaSesion = new Tabla_SESION();
            tablaSesion.USUARIO_ID = usuarioID;
            tablaSesion.FECHA = new Date();

            rutinasSesion.borrarSesiones();
            rutinasSesion.crearSesion(tablaSesion);
        }

        actualizarSesion();
        return true;
    }
}
