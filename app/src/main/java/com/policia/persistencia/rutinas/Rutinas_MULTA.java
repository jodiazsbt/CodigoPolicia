package com.policia.persistencia.rutinas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.policia.negocio.modelo.Modelo_MULTA;
import com.policia.persistencia.conexion.SQLiteProvider;
import com.policia.persistencia.tablas.Tabla_MULTA;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 24/11/2017.
 */

public class Rutinas_MULTA {

    private final Context context;
    private SQLiteDatabase DB;

    public Rutinas_MULTA(Context context) {

        this.context = context;
    }

    public ArrayList<Modelo_MULTA> Multas(String Idioma) {
        DB = new SQLiteProvider(context).getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT DISTINCT " +
                "MULTA.TIPOMULTA_ID, " +
                "CASE  " +
                "WHEN MULTA.TIPOMULTA_ID IN (1004) THEN 'MULTAS ESPECIALES'  " +
                "WHEN MULTA.TIPOMULTA_ID IN (1820) THEN 'COMPARENDO'  ELSE REPLACE(UPPER(NIVEL.NIVEL_" + Idioma + "),':','') END NIVEL_" + Idioma + ", " +
                "UPPER(NUMERAL.NUMERAL_ESP) NUMERAL_" + Idioma + ", " +
                "TIPO_MULTA.RESOURCE_ID, " +
                "CASE  " +
                "WHEN MULTA.TIPOMULTA_ID IN (1004,1820) THEN 0 " +
                "WHEN MULTA.TIPOMULTA_ID IN (1000) THEN UVT.VALOR*4 " +
                "WHEN MULTA.TIPOMULTA_ID IN (1001) THEN UVT.VALOR*8 " +
                "WHEN MULTA.TIPOMULTA_ID IN (1002) THEN UVT.VALOR*16 " +
                "WHEN MULTA.TIPOMULTA_ID IN (1003) THEN UVT.VALOR*32 END VALOR " +
                "FROM MULTA " +
                "INNER JOIN NUMERAL ON MULTA.TIPOMULTA_ID=NUMERAL.ID  " +
                "INNER JOIN NIVEL ON NUMERAL.NIVEL_ID=NIVEL.ID " +
                "INNER JOIN TIPO_MULTA ON MULTA.TIPOMULTA_ID=TIPO_MULTA.ID " +
                "INNER JOIN (SELECT UVT.VALOR FROM (SELECT MAX(ANIO) ANIO FROM UVT) UVT_MAX INNER JOIN UVT ON UVT_MAX.ANIO=UVT.ANIO) UVT;", null);

        ArrayList<Modelo_MULTA> result = new ArrayList<Modelo_MULTA>();
        while (cursor.moveToNext()) {
            Modelo_MULTA multa = new Modelo_MULTA(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getDouble(4)
            );
            result.add(multa);
        }
        cursor.close();
        DB.close();
        return result;
    }

    public String maxFecha() {
        DB = new SQLiteProvider(context).getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT STRFTIME('%d.%m.%Y',MAX(CASE WHEN LENGTH(FECHA)=8 THEN '20'||SUBSTR(FECHA,7,2) ELSE SUBSTR(FECHA,7,4) END ||'-'||SUBSTR(FECHA,4,2)||'-'||SUBSTR(FECHA,1,2))) FROM 'MULTA';", null);

        String maxFecha = null;
        while (cursor.moveToNext()) {
            maxFecha = cursor.getString(0);
        }

        cursor.close();
        DB.close();
        return maxFecha;
    }

    public void update(Tabla_MULTA multa) {
        String[] parameters = new String[]{
                multa.CATEGORIA_ID,
                multa.VIGENTE + "",
                multa.FECHA + "",
                multa.NUMERAL_ID + "",
                multa.MEDIDA_ID + "",
                multa.TIPOMULTA_ID + ""};

        DB = new SQLiteProvider(context).getWritableDatabase();
        DB.execSQL("UPDATE 'MULTA' SET CATEGORIA_ID=?,VIGENTE=?,FECHA=? WHERE NUMERAL_ID=? AND MEDIDA_ID=? AND TIPOMULTA_ID=?", parameters);
        DB.close();
    }
}
