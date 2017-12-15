package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_CAPITULO;
import com.policia.negocio.modelo.Modelo_IDIOMA;
import com.policia.persistencia.conexion.SQLiteProvider;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 8/12/2017.
 */

public class Rutinas_IDIOMA {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_IDIOMA(Context context) {

        this.context = context;
    }

    public ArrayList<Modelo_IDIOMA> Idiomas() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT DISTINCT " +
                "IDIOMA.CODIGO," +
                "IDIOMA.IDIOMA," +
                "COALESCE(PREFERENCIA.IDIOMA_CODIGO=IDIOMA.CODIGO,0) SELECCION " +
                "FROM IDIOMA " +
                "LEFT JOIN PREFERENCIA ON IDIOMA.CODIGO=PREFERENCIA.IDIOMA_CODIGO " +
                "LEFT JOIN SESION ON PREFERENCIA.USUARIO_ID=SESION.USUARIO_ID;", null);

        ArrayList<Modelo_IDIOMA> result = new ArrayList<Modelo_IDIOMA>();
        while (cursor.moveToNext()) {
            Modelo_IDIOMA capitulo = new Modelo_IDIOMA(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getInt(2) == 1
            );
            result.add(capitulo);
        }
        cursor.close();
        DB.close();
        return result;
    }
}
