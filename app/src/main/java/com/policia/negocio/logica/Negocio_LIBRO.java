package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Libros.LibrosOutput;
import com.policia.negocio.modelo.Libros.LibrosResultEntry;
import com.policia.negocio.modelo.Modelo_LIBRO;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_LIBRO;
import com.policia.persistencia.tablas.Tabla_LIBRO;
import com.policia.remote.RemoteClient;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 24/11/2017.
 */

public class Negocio_LIBRO {

    private Rutinas_LIBRO rutinasLibro;
    private Seguridad sesion;

    private Context context;
    private RemoteClient remoteClient;

    public Negocio_LIBRO(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasLibro = new Rutinas_LIBRO(context);
    }

    public int sincronizar() {

        remoteClient = new RemoteClient(context);
        LibrosOutput libros = null;
        try {

            libros = remoteClient.sincronizarLIBRO(rutinasLibro.maxFecha());

            for (LibrosResultEntry libro : libros.LibrosResult) {
                Tabla_LIBRO tablaLibro = new Tabla_LIBRO();
                tablaLibro.ID = String.valueOf(libro.ID_Libro);
                tablaLibro.LIBRO_ESP = String.valueOf(libro.NombreLibroESP);
                tablaLibro.VIGENTE = libro.Vigente_Libro;
                tablaLibro.NIVEL_ID = String.valueOf(libro.Id_Nivel_Libro);
                tablaLibro.FECHA = String.valueOf(libro.Fecha_Libro);
                tablaLibro.LIBRO_ENG = String.valueOf(libro.NombreLibroENG);
                rutinasLibro.update(tablaLibro);
            }
            return libros.LibrosResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Modelo_LIBRO> Libros() {

        return rutinasLibro.Libros(sesion.getIdiomaCodigo());
    }
}
