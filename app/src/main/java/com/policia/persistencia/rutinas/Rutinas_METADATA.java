package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_ARTICULO;
import com.policia.negocio.modelo.Modelo_Busqueda_Articulo;
import com.policia.persistencia.conexion.SQLiteProvider;

import java.util.ArrayList;

/**
 * Created by JORGE on 3/12/2017.
 */

public class Rutinas_METADATA {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_METADATA(Context context) {

        this.context = context;
    }

    public ArrayList<Modelo_Busqueda_Articulo> BusquedaMETADATA(String Idioma, String termino_busqueda) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        //String[] parametros = new String[]{termino_busqueda};

        Cursor cursor = DB.rawQuery("" +
                "SELECT " +
                "CAPITULO.ID, " +
                "(SELECT COUNT(A.ID) FROM ARTICULO A WHERE A.CAPITULO_ID=CAPITULO.ID AND A.ID<ARTICULO.ID) POS," +
                "NIVEL.NIVEL_" + Idioma + " LIBRO, " +
                "NIVEL_TITULO.NIVEL_" + Idioma + " TITULO, " +
                "NIVEL_CAPITULO.NIVEL_" + Idioma + " CAPITULO, " +
                "NIVEL_ARTICULO.NIVEL_" + Idioma + " ARTICULO " +
                "FROM NIVEL " +
                "INNER JOIN LIBRO ON NIVEL.ID=LIBRO.NIVEL_ID " +
                "INNER JOIN TITULO ON LIBRO.ID=TITULO.LIBRO_ID " +
                "INNER JOIN NIVEL NIVEL_TITULO ON TITULO.NIVEL_ID=NIVEL_TITULO.ID " +
                "INNER JOIN CAPITULO ON TITULO.ID=CAPITULO.TITULO_ID " +
                "INNER JOIN NIVEL NIVEL_CAPITULO ON CAPITULO.NIVEL_ID=NIVEL_CAPITULO.ID " +
                "INNER JOIN ARTICULO ON CAPITULO.ID=ARTICULO.CAPITULO_ID " +
                "INNER JOIN NIVEL NIVEL_ARTICULO ON ARTICULO.NIVEL_ID=NIVEL_ARTICULO.ID " +
                "INNER JOIN METADATA ON METADATA.ARTICULO_ID=ARTICULO.ID " +
                "WHERE METADATA.METADATA_" + Idioma + " LIKE '%" + termino_busqueda + "%';", null);

        ArrayList<Modelo_Busqueda_Articulo> result = new ArrayList<Modelo_Busqueda_Articulo>();
        while (cursor.moveToNext()) {
            result.add(new Modelo_Busqueda_Articulo(
                    cursor.getString(0),    //ID CAPITULO
                    cursor.getInt(1),       //POSICION ARTICULO
                    cursor.getString(2),    //LIBRO
                    cursor.getString(3),    //TITULO
                    cursor.getString(4),    //CAPITULO
                    cursor.getString(5))    //ARTICULO
            );
        }
        cursor.close();
        DB.close();
        return result;
    }

}
