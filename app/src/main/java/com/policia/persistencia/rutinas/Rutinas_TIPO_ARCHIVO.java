package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_TIPO_ARCHIVO;
import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_TIPO_ARCHIVO;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 23/01/2018.
 */

public class Rutinas_TIPO_ARCHIVO {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_TIPO_ARCHIVO(Context context) {

        this.context = context;
    }

    public String maxFecha() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT STRFTIME('%d.%m.%Y',MAX(CASE WHEN LENGTH(FECHA)=8 THEN '20'||SUBSTR(FECHA,7,2) ELSE SUBSTR(FECHA,7,4) END ||'-'||SUBSTR(FECHA,4,2)||'-'||SUBSTR(FECHA,1,2))) FROM 'TIPO_ARCHIVO';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public void update(Tabla_TIPO_ARCHIVO tipo_archivo) {
        String[] parameters = new String[]{
                tipo_archivo.TIPO_ARCHIVO_ESP,
                tipo_archivo.ACTIVO + "",
                tipo_archivo.TIPO_ARCHIVO_ENG + "",
                tipo_archivo.FECHA + "",
                tipo_archivo.ID + ""};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'TIPO_ARCHIVO' SET TIPO_ARCHIVO_ESP=?,ACTIVO=?,TIPO_ARCHIVO_ENG=?,FECHA=? WHERE ID=?", parameters);
        DB.close();
    }

    public ArrayList<Modelo_TIPO_ARCHIVO> TipoArchivos(String Idioma) {
        DB = new SQLiteProvider(context).getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT ID,TIPO_ARCHIVO_" + Idioma + " FROM 'TIPO_ARCHIVO';", null);

        ArrayList<Modelo_TIPO_ARCHIVO> result = new ArrayList<Modelo_TIPO_ARCHIVO>();
        while (cursor.moveToNext()) {
            Modelo_TIPO_ARCHIVO multa = new Modelo_TIPO_ARCHIVO(
                    cursor.getString(0),
                    cursor.getString(1)
            );
            result.add(multa);
        }
        cursor.close();
        DB.close();
        return result;
    }

}
