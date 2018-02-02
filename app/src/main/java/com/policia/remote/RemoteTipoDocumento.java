package com.policia.remote;

import android.app.Activity;
import android.os.AsyncTask;

import com.policia.codigopolicia.Comparendos.IComparendoConsulta;
import com.policia.remote.response.RNMCTIPOSDOCResponse;

/**
 * Created by 1085253556 on 1/02/2018.
 */

public class RemoteTipoDocumento extends AsyncTask<Void, Void, Void> {

    private Activity activity;

    private boolean terminado;
    private RemoteClient remoteClient;

    private IComparendoConsulta IComparendoConsulta;

    private RNMCTIPOSDOCResponse responseTipoDocumento;

    public RemoteTipoDocumento(Activity activity, IComparendoConsulta IComparendoConsulta) {

        try {
            this.terminado = false;

            this.activity = activity;

            this.IComparendoConsulta = IComparendoConsulta;

            this.remoteClient = new RemoteClient(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            responseTipoDocumento = remoteClient.RNMC_TIPOS_DOC();
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
            IComparendoConsulta.consultaTipoDocumento(responseTipoDocumento);
        }
    }
}
