package com.policia.persistencia.conexion;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.policia.codigopolicia.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 1085253556 on 24/11/2017.
 */

public class SQLiteProvider extends SQLiteOpenHelper {

    private final Context context;

    public SQLiteProvider(Context context) {

        super(context, context.getString(R.string.dbname), null, Integer
                .parseInt(context.getString(R.string.dbversion)));
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        executeSQLScript(database, context.getString(R.string.SCRIPT));
    }

    private void executeSQLScript(SQLiteDatabase database, String script) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open(script);
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();

            String[] createScript = outputStream.toString().replace(");", ")=").replace("\r\n", "").split("=");
            for (int i = 0; i < createScript.length; i++) {
                String sqlStatement = createScript[i].trim();
                // TODO You may want to parse out comments here
                if (sqlStatement.length() > 0) {
                    database.execSQL(sqlStatement + ";");
                }
            }
            database.execSQL("UPDATE MULTA SET CATEGORIA_ID=10001 WHERE NUMERAL_ID BETWEEN 1271 AND 1280;");
            database.execSQL("UPDATE MULTA SET CATEGORIA_ID=10002 WHERE NUMERAL_ID BETWEEN 1281 AND 1290;");
            database.execSQL("UPDATE MULTA SET CATEGORIA_ID=10003 WHERE NUMERAL_ID BETWEEN 1291 AND 1300;");
            database.execSQL("UPDATE MULTA SET CATEGORIA_ID=10004 WHERE NUMERAL_ID BETWEEN 1301 AND 1310;");
            database.execSQL("UPDATE MULTA SET CATEGORIA_ID=10005 WHERE NUMERAL_ID BETWEEN 1311 AND 1320;");
            database.execSQL("UPDATE MULTA SET CATEGORIA_ID=10006 WHERE NUMERAL_ID BETWEEN 1321 AND 1330;");
            database.execSQL("UPDATE MULTA SET CATEGORIA_ID=10007 WHERE NUMERAL_ID BETWEEN 1331 AND 1340;");
            database.execSQL("UPDATE MULTA SET CATEGORIA_ID=10008 WHERE NUMERAL_ID BETWEEN 1341 AND 1350;");
            database.execSQL("UPDATE MULTA SET CATEGORIA_ID=10009 WHERE NUMERAL_ID BETWEEN 1351 AND 1360;");
            database.execSQL("UPDATE MULTA SET CATEGORIA_ID=10010 WHERE NUMERAL_ID BETWEEN 1361 AND 1370;");
            database.execSQL("UPDATE MULTA SET CATEGORIA_ID=10011 WHERE NUMERAL_ID BETWEEN 1371 AND 1380;");
            database.execSQL("UPDATE MULTA SET CATEGORIA_ID=10012 WHERE NUMERAL_ID BETWEEN 1381 AND 1390;");
            database.execSQL("UPDATE MULTA SET CATEGORIA_ID=10013 WHERE NUMERAL_ID BETWEEN 1391 AND 1400;");
            database.execSQL("UPDATE MULTA SET CATEGORIA_ID=10014 WHERE NUMERAL_ID BETWEEN 1401 AND 1410;");
            database.execSQL("UPDATE MULTA SET CATEGORIA_ID=10015 WHERE NUMERAL_ID BETWEEN 1411 AND 1420;");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
