package com.policia.remote;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.policia.codigopolicia.PrincipalActivity;
import com.policia.negocio.logica.Negocio_ENCUESTA;
import com.policia.remote.request.RequestRESPUESTA;
import com.policia.remote.response.RESPUESTAENCUESTAResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 1085253556 on 29/12/2017.
 */

public class RemoteRESPUESTA extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private RequestRESPUESTA respuesta;
    private RemoteClient remoteClient;

    private Negocio_ENCUESTA negocioEncuesta;

    private RESPUESTAENCUESTAResponse responseRespuesta;

    public RemoteRESPUESTA(Activity activity, RequestRESPUESTA Respuesta) {

        this.activity = activity;
        this.respuesta = Respuesta;
        this.remoteClient = new RemoteClient(activity);
        try {
            this.negocioEncuesta = new Negocio_ENCUESTA(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            responseRespuesta = remoteClient.responderEncuenta(
                    this.respuesta.ID,
                    this.respuesta.DEPARTAMENTO,
                    new SimpleDateFormat("dd.MM.yyyy", new Locale("es", "CO")).format(new Date()),
                    this.respuesta.RESPUESTA);

            negocioEncuesta.inactivar(this.respuesta.ID);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(activity, "Gracias por participar", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this.activity, PrincipalActivity.class);
        this.activity.startActivity(intent);
        this.activity.finish();
    }
}