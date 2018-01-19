package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Capitulos.CapitulosOutput;
import com.policia.negocio.modelo.Capitulos.CapitulosResultEntry;
import com.policia.negocio.modelo.Modelo_CAPITULO;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_CAPITULO;
import com.policia.persistencia.tablas.Tabla_CAPITULO;
import com.policia.remote.RemoteClient;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 29/11/2017.
 */

public class Negocio_CAPITULO {

    private Rutinas_CAPITULO rutinasCapitulo;
    private Seguridad sesion;

    private Context context;
    private RemoteClient remoteClient;

    public Negocio_CAPITULO(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasCapitulo = new Rutinas_CAPITULO(context);
    }

    public int sincronizar() {

        remoteClient = new RemoteClient(context);
        CapitulosOutput capitulos = null;
        try {

            capitulos = remoteClient.sincronizarCAPITULO(rutinasCapitulo.maxFecha());

            for (CapitulosResultEntry capitulo : capitulos.CapitulosResult) {
                Tabla_CAPITULO tablaCapitulo = new Tabla_CAPITULO();
                tablaCapitulo.ID = String.valueOf(capitulo.Id_Capitulo);
                tablaCapitulo.CAPITULO_ESP = String.valueOf(capitulo.NombreCapituloESP);
                tablaCapitulo.VIGENTE = capitulo.Vigente_Capitulo;
                tablaCapitulo.NIVEL_ID = String.valueOf(capitulo.ID_Nivel_Capitulo);
                tablaCapitulo.TITULO_ID = String.valueOf(capitulo.ID_Titulo_Capitulo);
                tablaCapitulo.FECHA = String.valueOf(capitulo.Fecha_Capitulo);
                tablaCapitulo.CAPITULO_ENG = String.valueOf(capitulo.NombreCapituloENG);
                rutinasCapitulo.update(tablaCapitulo);
            }
            return capitulos.CapitulosResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Modelo_CAPITULO> CapitulosPorTitulo(String Titulo) {

        return rutinasCapitulo.CapitulosPorTitulo(sesion.getIdiomaCodigo(), Titulo);
    }
}
