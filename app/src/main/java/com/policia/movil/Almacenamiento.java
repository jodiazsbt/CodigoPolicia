package com.policia.movil;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 1085253556 on 5/02/2018.
 */

public class Almacenamiento {

    private Context context;
    private static Almacenamiento almacenamiento;

    private Almacenamiento(Context context) {

        this.context = context;
    }

    public static Almacenamiento newInstance(Context context) {

        if (almacenamiento == null)
            almacenamiento = new Almacenamiento(context);

        return almacenamiento;
    }

    public String guardarBitmap(byte[] image, String name) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("avatar_app", Context.MODE_PRIVATE);
        File avatar = new File(directory, name);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(avatar);

            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);// Use the compress method on the BitMap object to write image to the OutputStream
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        almacenamiento = null;
        return avatar.getAbsolutePath();
    }

    public String guardarDocumento(byte[] file, String name) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("documento_app", Context.MODE_PRIVATE);
        File documento = new File(directory, name);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(documento);
            fileOutputStream.write(file);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        almacenamiento = null;
        return documento.getAbsolutePath();
    }
}
