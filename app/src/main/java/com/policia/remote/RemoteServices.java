package com.policia.remote;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.policia.codigopolicia.PrincipalActivity;
import com.policia.negocio.logica.Negocio_ARTICULO;
import com.policia.negocio.logica.Negocio_CAPITULO;
import com.policia.negocio.logica.Negocio_LIBRO;
import com.policia.negocio.logica.Negocio_MEDIDA;
import com.policia.negocio.logica.Negocio_METADATA;
import com.policia.negocio.logica.Negocio_NIVEL;
import com.policia.negocio.logica.Negocio_NUMERAL;
import com.policia.negocio.logica.Negocio_TITULO;

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

    long sincronizar = 0;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Long doInBackground(Void... voids) {

        try {

            return Long.valueOf(0);

            /*
            sincronizar += negocioNivel.sincronizar();
            sincronizar += negocioLibro.sincronizar();
            sincronizar += negocioTitulo.sincronizar();
            sincronizar += negocioCapitulo.sincronizar();
            sincronizar += negocioArticulo.sincronizar();
            sincronizar += negocioMetadata.sincronizar();
            sincronizar += negocioNumeral.sincronizar();
            sincronizar += negocioMedida.sincronizar();
            */
        } catch (Exception e) {
            e.printStackTrace();
            sincronizar = -1;
        }
        return sincronizar;
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);

        if (!(sincronizar == 0)) {
            Toast.makeText(activity, "Su base de datos ha sido actualizada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "Su base de datos está actualizada", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this.activity, PrincipalActivity.class);
        this.activity.startActivity(intent);
        this.activity.finish();

        remoteServices = null;
    }
}
