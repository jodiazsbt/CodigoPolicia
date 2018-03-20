package com.policia.remote;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by 1085253556 on 14/03/2018.
 */

public class RemoteRENEC extends AsyncTask<Void, Void, RemoteRENEC.RENECEstado> {

    private Activity activity;
    private String Documento;
    private String FechaExpedicion;
    private IConsultaRENEC iConsultaRENEC;

    private static RemoteRENEC remoteRENEC;

    private RemoteRENEC(Activity activity, String Documento, IConsultaRENEC iConsultaRENEC) {

        this.activity = activity;
        this.Documento = Documento;
        this.iConsultaRENEC = iConsultaRENEC;
    }

    public static RemoteRENEC newInstance(Activity activity, String Documento, IConsultaRENEC iConsultaRENEC) {

        if (remoteRENEC == null)
            remoteRENEC = new RemoteRENEC(activity, Documento, iConsultaRENEC);
        return remoteRENEC;
    }

    @Override
    protected RENECEstado doInBackground(Void... voids) {

        try {

            RemoteClient.connect(activity).consultarRENEC(Documento);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(RENECEstado renecEstado) {
        super.onPostExecute(renecEstado);

        if (renecEstado == RENECEstado.SI_COINCIDE_FECHA)
            iConsultaRENEC.consultarExpediente();
        else if (renecEstado == RENECEstado.NO_COINCIDE_FECHA)
            Toast.makeText(activity, "La fecha de expedición no coincide con el documento", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(activity, "Lo sentimos, se necesita que esté conectado a internet", Toast.LENGTH_SHORT).show();

        remoteRENEC = null;
    }

    public interface IConsultaRENEC {
        void consultarExpediente();
    }

    public enum RENECEstado {
        SI_COINCIDE_FECHA,
        NO_COINCIDE_FECHA,
        ERROR_CONEXION;
    }
}
