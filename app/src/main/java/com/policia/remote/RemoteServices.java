package com.policia.remote;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.policia.codigopolicia.PrincipalActivity;
import com.policia.negocio.logica.Negocio_ACCION;
import com.policia.negocio.logica.Negocio_ARTICULO;
import com.policia.negocio.logica.Negocio_AVATAR;
import com.policia.negocio.logica.Negocio_CAPITULO;
import com.policia.negocio.logica.Negocio_CATEGORIA;
import com.policia.negocio.logica.Negocio_COMPENTENCIA_NUMERAL;
import com.policia.negocio.logica.Negocio_COMPETENCIA;
import com.policia.negocio.logica.Negocio_DOCUMENTO;
import com.policia.negocio.logica.Negocio_LIBRO;
import com.policia.negocio.logica.Negocio_MEDIDA;
import com.policia.negocio.logica.Negocio_METADATA;
import com.policia.negocio.logica.Negocio_MULTA;
import com.policia.negocio.logica.Negocio_NIVEL;
import com.policia.negocio.logica.Negocio_NUMERAL;
import com.policia.negocio.logica.Negocio_TIPO_ARCHIVO;
import com.policia.negocio.logica.Negocio_TITULO;
import com.policia.negocio.logica.Negocio_UVT;

/**
 * Created by 1085253556 on 28/12/2017.
 */

public class RemoteServices extends AsyncTask<Void, Void, Long> {

    private RemoteClient remoteClient;

    private Activity activity;
    private static RemoteServices remoteServices;

    private Negocio_NIVEL negocioNivel;
    private Negocio_LIBRO negocioLibro;
    private Negocio_TITULO negocioTitulo;
    private Negocio_CAPITULO negocioCapitulo;
    private Negocio_ARTICULO negocioArticulo;
    private Negocio_METADATA negocioMetadata;
    private Negocio_NUMERAL negocioNumeral;
    private Negocio_MEDIDA negocioMedida;
    private Negocio_MULTA negocioMulta;
    private Negocio_TIPO_ARCHIVO negocioTipoArchivo;
    private Negocio_DOCUMENTO negocioDocumento;
    private Negocio_CATEGORIA negocioCategoria;
    private Negocio_COMPETENCIA negocioCompetencia;
    private Negocio_COMPENTENCIA_NUMERAL negocioCompentenciaNumeral;
    private Negocio_ACCION negocioAccion;
    private Negocio_UVT negocioUVT;
    private Negocio_AVATAR negocioAvatar;

    public static RemoteServices newInstance(Activity activity) {

        if (remoteServices == null) {
            remoteServices = new RemoteServices(activity);
        }
        return remoteServices;
    }

    private RemoteServices(Activity activity) {

        this.activity = activity;
        this.remoteClient = new RemoteClient(activity);

        try {
            this.negocioNivel = new Negocio_NIVEL(activity);
            this.negocioLibro = new Negocio_LIBRO(activity);
            this.negocioTitulo = new Negocio_TITULO(activity);
            this.negocioCapitulo = new Negocio_CAPITULO(activity);
            this.negocioArticulo = new Negocio_ARTICULO(activity);
            this.negocioMetadata = new Negocio_METADATA(activity);
            this.negocioNumeral = new Negocio_NUMERAL(activity);
            this.negocioMedida = new Negocio_MEDIDA(activity);
            this.negocioMulta = new Negocio_MULTA(activity);
            this.negocioTipoArchivo = new Negocio_TIPO_ARCHIVO(activity);
            this.negocioDocumento = new Negocio_DOCUMENTO(activity);
            this.negocioCategoria = new Negocio_CATEGORIA(activity);
            this.negocioCompetencia = new Negocio_COMPETENCIA(activity);
            this.negocioCompentenciaNumeral = new Negocio_COMPENTENCIA_NUMERAL(activity);
            this.negocioAccion = new Negocio_ACCION(activity);
            this.negocioUVT = new Negocio_UVT(activity);
            this.negocioAvatar = new Negocio_AVATAR(activity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Long doInBackground(Void... voids) {

        long sincronizar = 0;

        try {

            if (!remoteClient.isServiceOnline())
                return Long.valueOf(-1);

            sincronizar += negocioAvatar.sincronizar();

            if (sincronizar >= 0) {

                sincronizar += negocioNivel.sincronizar();
                sincronizar += negocioLibro.sincronizar();
                sincronizar += negocioTitulo.sincronizar();
                sincronizar += negocioCapitulo.sincronizar();
                sincronizar += negocioArticulo.sincronizar();
                sincronizar += negocioNumeral.sincronizar();
                sincronizar += negocioMetadata.sincronizar();
                sincronizar += negocioMedida.sincronizar();
                sincronizar += negocioMulta.sincronizar();
                sincronizar += negocioTipoArchivo.sincronizar();
                sincronizar += negocioDocumento.sincronizar();
                sincronizar += negocioCategoria.sincronizar();
                sincronizar += negocioCompetencia.sincronizar();
                sincronizar += negocioCompentenciaNumeral.sincronizar();
                sincronizar += negocioAccion.sincronizar();
                sincronizar += negocioUVT.sincronizar();
            }


        } catch (Exception e) {
            e.printStackTrace();
            return Long.valueOf(-2);
        }
        return sincronizar;
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);

        if (aLong == -1) {
            Log.e("REMOTESERVICE", "Error verificando la conexión con los servicios");
        } else if (aLong == -2) {
            Log.e("REMOTESERVICE", "Error sincronizando con los servicios");
        } else if (aLong == 0) {
            Toast.makeText(activity, "Su base de datos está actualizada", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(activity, "Su base de datos ha sido actualizada", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this.activity, PrincipalActivity.class);
        this.activity.startActivity(intent);
        this.activity.finish();

        remoteServices = null;
    }
}
