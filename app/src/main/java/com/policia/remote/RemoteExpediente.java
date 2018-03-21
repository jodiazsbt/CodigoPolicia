package com.policia.remote;

import android.app.Activity;
import android.os.AsyncTask;

import com.policia.codigopolicia.Comparendos.IComparendoExpediente;
import com.policia.codigopolicia.R;
import com.policia.remote.response.RNMCGENERAL2Response;
import com.policia.remote.response.RNMCGENERALResponse;

/**
 * Created by 1085253556 on 1/02/2018.
 */

public class RemoteExpediente extends AsyncTask<Void, Void, Void> {

    private Activity activity;

    private boolean terminado;

    private String Token;
    private String Expedicion;
    private String TipoDocumento;
    private String Identificacion;

    private IComparendoExpediente IComparendoExpediente;
    private RNMCGENERALResponse response;
    private RNMCGENERAL2Response response2;

    private static RemoteExpediente remoteExpediente;

    private RemoteExpediente(Activity activity, String TipoDocumento, String Identificacion, String Expedicion, IComparendoExpediente IComparendoExpediente) {

        this.terminado = false;
        this.activity = activity;
        this.IComparendoExpediente = IComparendoExpediente;
        this.TipoDocumento = TipoDocumento;
        this.Identificacion = Identificacion;
        this.Expedicion = Expedicion;
        this.Token = activity.getResources().getString(R.string.token);
    }

    public static RemoteExpediente newInstance(Activity activity, String TipoDocumento, String Identificacion, String Expedicion, IComparendoExpediente IComparendoExpediente) {

        if (remoteExpediente == null)
            remoteExpediente = new RemoteExpediente(activity, TipoDocumento, Identificacion, Expedicion, IComparendoExpediente);
        return remoteExpediente;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            if (!TipoDocumento.equals("55"))
                response = RemoteClient.connect(activity).RNMC_GENERAL(TipoDocumento, Identificacion);
            else
                response2 = RemoteClient.connect(activity).RNMC_GENERAL(TipoDocumento, Identificacion, Token, Expedicion);

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

            if (!TipoDocumento.equals("55"))
                IComparendoExpediente.consultar(response, TipoDocumento, Identificacion);
            else
                IComparendoExpediente.consultarCedula(response2, TipoDocumento, Identificacion);
        }
        remoteExpediente = null;
    }
}
