package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_CAPITULO_MULTA;
import com.policia.persistencia.conexion.SQLiteProvider;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 29/11/2017.
 */

public class Rutinas_CAPITULO_MULTA {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_CAPITULO_MULTA(Context context) {

        this.context = context;
    }

    public ArrayList<Modelo_CAPITULO_MULTA> CapitulosPorTitulo(String Idioma, String Titulo) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                Titulo + ""};

        Cursor cursor = DB.rawQuery("SELECT " +
                "CAPITULO.ID, " +
                "NIVEL.NIVEL_" + Idioma + " NIVEL, " +
                "CAPITULO.CAPITULO_" + Idioma + " CAPITULO " +
                "FROM CAPITULO " +
                "INNER JOIN NIVEL ON CAPITULO.NIVEL_ID=NIVEL.ID " +
                "WHERE CAPITULO.TITULO_ID=?;", parameters);

        ArrayList<Modelo_CAPITULO_MULTA> result = new ArrayList<Modelo_CAPITULO_MULTA>();
        while (cursor.moveToNext()) {
            Modelo_CAPITULO_MULTA capitulo = new Modelo_CAPITULO_MULTA(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            result.add(capitulo);
        }
        cursor.close();
        DB.close();
        return result;
    }
}
