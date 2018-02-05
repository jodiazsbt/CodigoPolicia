package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_TITULO;
import com.policia.negocio.modelo.Titulos.TitulosOutput;
import com.policia.negocio.modelo.Titulos.TitulosResultEntry;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_TITULO;
import com.policia.persistencia.tablas.Tabla_TITULO;
import com.policia.remote.RemoteClient;

import java.util.ArrayList;

/**
 * Created by JORGE on 26/11/2017.
 */

public class Negocio_TITULO {

    private Rutinas_TITULO rutinasTitulo;
    private Seguridad sesion;

    private Context context;
    private RemoteClient remoteClient;

    public Negocio_TITULO(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasTitulo = new Rutinas_TITULO(context);
    }

    public int sincronizar() {

        remoteClient = new RemoteClient(context);
        TitulosOutput titulos = null;
        try {

            titulos = remoteClient.sincronizarTITULO(rutinasTitulo.maxFecha());
            for (TitulosResultEntry resultEntry : titulos.TitulosResult) {
                Tabla_TITULO titulo = new Tabla_TITULO();
                titulo.ID = String.valueOf(resultEntry.Id_Titulo);
                titulo.TITULO_ESP = String.valueOf(resultEntry.NombreTituloESP);
                titulo.VIGENTE = resultEntry.Vigente_Titulo;
                titulo.NIVEL_ID = String.valueOf(resultEntry.ID_Nivel_Titulo);
                titulo.LIBRO_ID = String.valueOf(resultEntry.ID_Libro_Titulo);
                titulo.FECHA = String.valueOf(resultEntry.Fecha_Titulo);
                titulo.TITULO_ENG = String.valueOf(resultEntry.NombreTituloENG);

                if (rutinasTitulo.exists(titulo.ID))
                    rutinasTitulo.update(titulo);
                else
                    rutinasTitulo.create(titulo);
            }
            return titulos.TitulosResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Modelo_TITULO> TitulosPorLibro(String Libro) {

        return rutinasTitulo.TitulosPorLibro(sesion.getIdiomaCodigo(), Libro);
    }
}
