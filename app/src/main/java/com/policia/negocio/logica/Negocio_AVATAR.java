package com.policia.negocio.logica;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_AVATAR;
import com.policia.persistencia.tablas.Tabla_AVATAR;
import com.policia.remote.RemoteClient;
import com.policia.remote.response.ImagenesAvatarInfo;
import com.policia.remote.response.ImagenesAvatarInfoResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 1085253556 on 31/01/2018.
 */

public class Negocio_AVATAR {

    private Rutinas_AVATAR rutinasAvatar;
    private Seguridad sesion;

    private Context context;
    private RemoteClient remoteClient;

    public Negocio_AVATAR(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasAvatar = new Rutinas_AVATAR(context);
    }

    public int sincronizar() {

        remoteClient = new RemoteClient(context);
        ImagenesAvatarInfoResponse response = null;
        try {

            response = remoteClient.sincronizarAVATAR(rutinasAvatar.maxFecha());

            for (ImagenesAvatarInfo result : response.imagenesAvatarInfo) {
                Tabla_AVATAR avatar = new Tabla_AVATAR();
                byte[] imagenAvatar = result.imagenAvatar;

                avatar.ID = String.valueOf(result.idAvatar);
                avatar.AVATAR = String.valueOf(result.avatarNombre);
                avatar.FECHA = String.valueOf(result.fechaact);
                avatar.UBICACION = guardarAVATAR(BitmapFactory.decodeByteArray(imagenAvatar, 0, imagenAvatar.length), avatar.AVATAR);
                rutinasAvatar.update(avatar);
            }
            return response.imagenesAvatarInfo.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String guardarAVATAR(Bitmap bitmapImage, String Nombre) {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("avatar_app", Context.MODE_PRIVATE);
        // Create imageDir
        File avatar = new File(directory, Nombre + ".png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(avatar);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return avatar.getAbsolutePath();
    }

    public void drawAVATAR(AVATAR avatar, ImageView image) {
        try {
            Tabla_AVATAR dato = rutinasAvatar.read(avatar.toString());
            image.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(dato.UBICACION)));
        } catch (Exception e) {
            /*
            image.setImageDrawable(
                    context.getResources().getDrawable(
                            context.getResources().getIdentifier(
                                    "@mipmap/background_green",
                                    null,
                                    context.getPackageName())));*/
            e.printStackTrace();
        }
    }

    public enum AVATAR {
        SCREEN_MENU,
        SCREEN_SPLASH,
        SCREEN_CNPC,
        SCREEN_IDIOMA,
        SCREEN_MULTA,
        SCREEN_COMPARENDO,
        SCREEN_PDF417,
        SCREEN_PSC,
        SCREEN_LOGIN,
        SCREEN_CAPACITACION,
        SCREEN_ARTICULO;

    }
}
