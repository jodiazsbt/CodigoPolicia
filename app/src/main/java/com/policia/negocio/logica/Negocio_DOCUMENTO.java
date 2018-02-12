package com.policia.negocio.logica;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.policia.movil.Almacenamiento;
import com.policia.negocio.modelo.Modelo_DOCUMENTO;
import com.policia.negocio.modelo.Modelo_TIPO_ARCHIVO;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_DOCUMENTO;
import com.policia.persistencia.tablas.Tabla_DOCUMENTO;
import com.policia.remote.RemoteClient;
import com.policia.remote.response.DOCUMENTOSINSTRUCTIVOSCNPCNResponse;
import com.policia.remote.response.DOCUMENTOSINSTRUCTIVOSCNPCNResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 1085253556 on 15/01/2018.
 */

public class Negocio_DOCUMENTO {

    private Rutinas_DOCUMENTO rutinasDocumento;
    private Seguridad sesion;

    private Negocio_TIPO_ARCHIVO negocioTipoArchivo;

    private Context context;

    public Negocio_DOCUMENTO(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasDocumento = new Rutinas_DOCUMENTO(context);
    }

    public ArrayList<Modelo_DOCUMENTO> Documentos() {
        return rutinasDocumento.Documentos();
    }

    public int countDocumentos() {
        return rutinasDocumento.countDocumentos();
    }

    public int sincronizar() {

        DOCUMENTOSINSTRUCTIVOSCNPCNResponse response = null;
        try {
            int sincronizados = 0;
            negocioTipoArchivo = new Negocio_TIPO_ARCHIVO(context);
            for (Modelo_TIPO_ARCHIVO tipo_archivo : negocioTipoArchivo.TipoArchivos()) {

                response = RemoteClient.connect(context).sincronizarDOCUMENTO(tipo_archivo.ID);

                for (DOCUMENTOSINSTRUCTIVOSCNPCNResult result : response.dOCUMENTOSINSTRUCTIVOSCNPCNResult) {

                    /*
                    DOCUMENTOSELECCIONADOResponse responseDocumento = RemoteClient.connect(context).DOCUMENTOSELECCIONADO(result.uRL.replace("/", ","));

                    for (DOCUMENTOSELECCIONADOResult itemDocumento : responseDocumento.dOCUMENTOSELECCIONADOResult) {

                    }*/

                    Tabla_DOCUMENTO documento = new Tabla_DOCUMENTO();
                    documento.ID = String.valueOf(result.iDDOCUMENTO);
                    documento.DOCUMENTO_ESP = String.valueOf(result.nOMBREDOCUMENTO);
                    documento.URL = String.valueOf(result.uRL);
                    documento.ACTIVO = result.aCTIVODOCUMENTOS;
                    documento.TIPO_ARCHIVO_ID = String.valueOf(result.iDTIPODOARCHIVO);
                    documento.UBICACION = null;

                    if (tipo_archivo.TipoArchivo.equals("AVATAR")) {
                        documento.ACTIVO = false;
                        documento.UBICACION = Almacenamiento.newInstance(context).guardarAVATAR(documento.URL);
                    } else {
                        documento.UBICACION = null;//Almacenamiento.newInstance(context).guardarDocumento(itemDocumento.documento, itemDocumento.nombreArchivo);
                    }

                    if (rutinasDocumento.exists(documento.ID))
                        rutinasDocumento.update(documento);
                    else
                        rutinasDocumento.create(documento);
                }
                sincronizados += response.dOCUMENTOSINSTRUCTIVOSCNPCNResult.size();
            }
            return sincronizados;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String guardarDOCUMENTO(Bitmap bitmapImage, String Nombre, String Extension) {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("avatar_app", Context.MODE_PRIVATE);
        // Create imageDir
        File avatar = new File(directory, Nombre + "." + Extension);

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

    public void drawAVATAR(Negocio_DOCUMENTO.AVATAR avatar, ImageView image) {
        try {
            String location = rutinasDocumento.locationAVATAR(avatar.toString());
            if (!location.equals("")) {/*
                image.setImageDrawable(
                        context.getResources().getDrawable(
                                context.getResources().getIdentifier(
                                        "@mipmap/background_green",
                                        null,
                                        context.getPackageName())));
            } else {*/
                image.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(location)));
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
