package com.policia.remote;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.policia.codigopolicia.EncuestaActivity;
import com.policia.negocio.logica.Negocio_ENCUESTA;
import com.policia.remote.response.ENCUESTASCNPCResponse;
import com.policia.remote.response.ENCUESTASCNPCResult;

/**
 * Created by 1085253556 on 29/12/2017.
 */

public class RemoteENCUESTA extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private static RemoteENCUESTA remoteENCUESTA;

    private ENCUESTASCNPCResponse responseEncuesta;

    private Negocio_ENCUESTA negocioEncuesta;

    private RemoteENCUESTA(Activity activity) {

        this.activity = activity;
        try {
            this.negocioEncuesta = new Negocio_ENCUESTA(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RemoteENCUESTA newInstance(Activity activity) {
        if (remoteENCUESTA == null)
            remoteENCUESTA = new RemoteENCUESTA(activity);

        return remoteENCUESTA;
    }

    private boolean existe;

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            existe = false;
            responseEncuesta = RemoteClient.connect(activity).consultarEncuentas();
            int pos = 0;
            String[] encuestas = new String[responseEncuesta.eNCUESTASCNPCResult.size()];
            for (ENCUESTASCNPCResult encuesta : responseEncuesta.eNCUESTASCNPCResult) {

                encuestas[pos] = encuesta.iDENCUESTA + "";

            }

            for (Long ID : negocioEncuesta.existeEncuesta(encuestas)) {
                for (ENCUESTASCNPCResult encuesta : responseEncuesta.eNCUESTASCNPCResult) {

                    if (encuesta.iDENCUESTA == ID) {
                        existe = true;
                        negocioEncuesta.create(encuesta);

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        if (existe) {

            Intent intent = new Intent(this.activity, EncuestaActivity.class);
            this.activity.startActivity(intent);
            this.activity.finish();

        } else {

            RemoteServices.newInstance(this.activity).execute();
        }

        remoteENCUESTA = null;
        super.onPostExecute(aVoid);
    }
}
