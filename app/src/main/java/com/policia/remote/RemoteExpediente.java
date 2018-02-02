package com.policia.remote;

import android.app.Activity;
import android.os.AsyncTask;

import com.policia.codigopolicia.Comparendos.IComparendoExpediente;
import com.policia.remote.response.RNMCGENERALResponse;

/**
 * Created by 1085253556 on 1/02/2018.
 */

public class RemoteExpediente extends AsyncTask<Void, Void, Void> {

    private Activity activity;

    private boolean terminado;
    private RemoteClient remoteClient;

    private String TipoDocumento;
    private String Identificacion;

    private IComparendoExpediente IComparendoExpediente;
    private RNMCGENERALResponse response;

    public RemoteExpediente(Activity activity, String TipoDocumento, String Identificacion, IComparendoExpediente IComparendoExpediente) {

        this.terminado = false;
        this.activity = activity;
        this.IComparendoExpediente = IComparendoExpediente;
        this.TipoDocumento = TipoDocumento;
        this.Identificacion = Identificacion;
        this.remoteClient = new RemoteClient(activity);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            response = remoteClient.RNMC_GENERAL(TipoDocumento, Identificacion);

            terminado = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (terminado) {

            IComparendoExpediente.consultar(response, TipoDocumento, Identificacion);
        }

    }
}
