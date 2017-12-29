package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Libros.LibrosOutput;
import com.policia.negocio.modelo.Libros.LibrosResultEntry;
import com.policia.negocio.modelo.Modelo_TITULO;
import com.policia.negocio.modelo.Titulos.TitulosOutput;
import com.policia.negocio.modelo.Titulos.TitulosResultEntry;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_TITULO;
import com.policia.persistencia.tablas.Tabla_LIBRO;
import com.policia.persistencia.tablas.Tabla_TITULO;
import com.policia.remote.RemoteClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
            for (TitulosResultEntry titulo : titulos.TitulosResult) {
                Tabla_TITULO tablaTitulo = new Tabla_TITULO();
                tablaTitulo.ID = String.valueOf(titulo.Id_Titulo);
                tablaTitulo.TITULO_ESP = String.valueOf(titulo.NombreTituloESP);
                tablaTitulo.VIGENTE = titulo.Vigente_Titulo;
                tablaTitulo.NIVEL_ID = String.valueOf(titulo.ID_Nivel_Titulo);
                tablaTitulo.LIBRO_ID = String.valueOf(titulo.ID_Libro_Titulo);
                tablaTitulo.FECHA = String.valueOf(titulo.Fecha_Titulo);
                tablaTitulo.TITULO_ENG = String.valueOf(titulo.NombreTituloENG);
                rutinasTitulo.update(tablaTitulo);
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
