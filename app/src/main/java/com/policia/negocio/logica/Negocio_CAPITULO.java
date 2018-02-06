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

            for (CapitulosResultEntry result : capitulos.CapitulosResult) {
                Tabla_CAPITULO capitulo = new Tabla_CAPITULO();
                capitulo.ID = String.valueOf(result.Id_Capitulo);
                capitulo.CAPITULO_ESP = String.valueOf(result.NombreCapituloESP);
                capitulo.VIGENTE = result.Vigente_Capitulo;
                capitulo.NIVEL_ID = String.valueOf(result.ID_Nivel_Capitulo);
                capitulo.TITULO_ID = String.valueOf(result.ID_Titulo_Capitulo);
                capitulo.FECHA = String.valueOf(result.Fecha_Capitulo);
                capitulo.CAPITULO_ENG = String.valueOf(result.NombreCapituloENG);

                if (rutinasCapitulo.exists(capitulo.ID))
                    rutinasCapitulo.update(capitulo);
                else
                    rutinasCapitulo.create(capitulo);
            }
            return capitulos.CapitulosResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Modelo_CAPITULO> CapitulosPorTitulo(String Titulo) {

        return rutinasCapitulo.CapitulosPorTitulo(sesion.getIdiomaLargo(), Titulo);
    }
}
