package com.policia.persistencia.rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_TIPO_ARCHIVO;
import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_NUMERAL;
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

    public boolean exists(String ID) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                ID + ""};

        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM TIPO_ARCHIVO WHERE ID=?;", parameters);

        int cantidad = 0;
        while (cursor.moveToNext()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return cantidad == 1;
    }

    public boolean create(Tabla_TIPO_ARCHIVO tipo_archivo) {
        long id = 0;

        ContentValues parameters = new ContentValues();
        parameters.put("ID", tipo_archivo.ID);
        parameters.put("TIPO_ARCHIVO_ESP", tipo_archivo.TIPO_ARCHIVO_ESP);
        parameters.put("ACTIVO", tipo_archivo.ACTIVO);
        parameters.put("TIPO_ARCHIVO_ENG", tipo_archivo.TIPO_ARCHIVO_ENG);
        parameters.put("FECHA", tipo_archivo.FECHA);

        DB = new SQLiteProvider(context).getWritableDatabase();
        id = DB.insert("'TIPO_ARCHIVO'", null, parameters);
        DB.close();
        return id > 0;
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
