package com.policia.movil;

import android.content.Context;
import android.content.ContextWrapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

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

    public String guardarAVATAR(String uri) {

        File image = null;
        try {
            URL url = new URL(uri);
            InputStream input = url.openStream();

            ContextWrapper contextWrapper = new ContextWrapper(context);
            File directory = contextWrapper.getDir("avatar", Context.MODE_PRIVATE);
            image = new File(directory, uri.substring(uri.lastIndexOf("/")+1));
            OutputStream output = new FileOutputStream(image);
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                output.write(buffer, 0, bytesRead);
            }
            input.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image.getAbsolutePath();
    }
}
