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

    public Negocio_LIBRO(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasLibro = new Rutinas_LIBRO(context);
    }

    public int sincronizar() {

        LibrosOutput libros = null;
        try {

            libros = RemoteClient.connect(context).sincronizarLIBRO(rutinasLibro.maxFecha());

            for (LibrosResultEntry result : libros.LibrosResult) {
                Tabla_LIBRO libro = new Tabla_LIBRO();
                libro.ID = String.valueOf(result.ID_Libro);
                libro.LIBRO_ESP = String.valueOf(result.NombreLibroESP);
                libro.VIGENTE = result.Vigente_Libro;
                libro.NIVEL_ID = String.valueOf(result.Id_Nivel_Libro);
                libro.FECHA = String.valueOf(result.Fecha_Libro);
                libro.LIBRO_ENG = String.valueOf(result.NombreLibroENG);

                if (rutinasLibro.exists(libro.ID))
                    rutinasLibro.update(libro);
                else
                    rutinasLibro.create(libro);
            }
            return libros.LibrosResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Modelo_LIBRO> Libros() {

        return rutinasLibro.Libros(sesion.getIdiomaLargo());
    }
}
