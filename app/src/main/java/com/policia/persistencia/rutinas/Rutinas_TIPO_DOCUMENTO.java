package com.policia.persistencia.rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_TIPO_DOCUMENTO;
import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_TIPO_DOCUMENTO;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 9/02/2018.
 */

public class Rutinas_TIPO_DOCUMENTO {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_TIPO_DOCUMENTO(Context context) {

        this.context = context;
    }

    public ArrayList<Modelo_TIPO_DOCUMENTO> TiposDocumento() {
        DB = new SQLiteProvider(context).getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT ID, TIPO_DOCUMENTO FROM TIPO_DOCUMENTO WHERE ACTIVO=1;", null);

        ArrayList<Modelo_TIPO_DOCUMENTO> result = new ArrayList<Modelo_TIPO_DOCUMENTO>();
        while (cursor.moveToNext()) {
            Modelo_TIPO_DOCUMENTO tipo_documento = new Modelo_TIPO_DOCUMENTO(
                    cursor.getString(0),//ID
                    cursor.getString(1)//TIPO DOCUMENTO
            );
            result.add(tipo_documento);
        }
        cursor.close();
        DB.close();
        return result;
    }

    public void update(Tabla_TIPO_DOCUMENTO tipo_documento) {
        String[] parameters = new String[]{
                tipo_documento.TIPO_DOCUMENTO,
                tipo_documento.ID + ""};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'TIPO_DOCUMENTO' SET TIPO_DOCUMENTO=? WHERE ID=?", parameters);
        DB.close();
    }

    public boolean create(Tabla_TIPO_DOCUMENTO tipo_documento) {
        long id = 0;

        ContentValues parameters = new ContentValues();
        parameters.put("ID", tipo_documento.ID);
        parameters.put("TIPO_DOCUMENTO", tipo_documento.TIPO_DOCUMENTO);
        parameters.put("ACTIVO", "1");

        DB = new SQLiteProvider(context).getWritableDatabase();
        id = DB.insert("'TIPO_DOCUMENTO'", null, parameters);
        DB.close();
        return id > 0;
    }

    public boolean exists(String ID) {
        DB = new SQLiteProvider(context).getReadableDatabase();

        String[] parameters = new String[]{
                ID};

        Cursor cursor = DB.rawQuery("SELECT COUNT(*) FROM TIPO_DOCUMENTO WHERE ID=?;", parameters);

        int cantidad = 0;
        while (cursor.moveToNext()) {
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        DB.close();
        return cantidad == 1;
    }
}
