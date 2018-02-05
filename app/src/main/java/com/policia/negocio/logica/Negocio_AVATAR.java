package com.policia.negocio.logica;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.policia.movil.Almacenamiento;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_AVATAR;
import com.policia.persistencia.tablas.Tabla_AVATAR;
import com.policia.remote.RemoteClient;
import com.policia.remote.response.ImagenesAvatarInfo;
import com.policia.remote.response.ImagenesAvatarInfoResponse;

import java.io.FileInputStream;

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

                avatar.ID = String.valueOf(result.idAvatar);
                avatar.AVATAR = String.valueOf(result.avatarNombre);
                avatar.FECHA = String.valueOf(result.fechaact);
                avatar.UBICACION = Almacenamiento.newInstance(context).guardarBitmap(result.imagenAvatar, avatar.AVATAR + ".PNG");
                rutinasAvatar.update(avatar);
            }
            return response.imagenesAvatarInfo.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void drawAVATAR(AVATAR avatar, ImageView image) {
        try {
            Tabla_AVATAR dato = rutinasAvatar.read(avatar.toString());
            if (!(dato.UBICACION == null)) {/*
                image.setImageDrawable(
                        context.getResources().getDrawable(
                                context.getResources().getIdentifier(
                                        "@mipmap/background_green",
                                        null,
                                        context.getPackageName())));
            } else {*/
                image.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(dato.UBICACION)));
            }
        } catch (Exception e) {
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
