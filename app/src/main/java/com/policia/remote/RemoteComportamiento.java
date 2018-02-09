package com.policia.remote;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;

import com.policia.codigopolicia.Comparendos.AdapterComportamiento;
import com.policia.remote.response.RNMCDETALLECOMPORTAMIENTOResponse;

/**
 * Created by 1085253556 on 2/02/2018.
 */

public class RemoteComportamiento extends AsyncTask<Void, Void, Boolean> {

    private Activity activity;
    private String Comportamiento, Expediente;
    private RNMCDETALLECOMPORTAMIENTOResponse response;

    private static RemoteComportamiento remoteComportamiento;

    private RemoteComportamiento(Activity activity, String Comportamiento, String Expediente) {

        this.activity = activity;
        this.Expediente = Expediente;
        this.Comportamiento = Comportamiento;
    }

    public static RemoteComportamiento newInstance(Activity activity, String Comportamiento, String Expediente) {

        if (remoteComportamiento == null)
            remoteComportamiento = new RemoteComportamiento(activity, Comportamiento, Expediente);
        return remoteComportamiento;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        try {
            response = RemoteClient.connect(activity).RNMC_DETALLE_COMPORTAMIENTO(Comportamiento, Expediente);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {

        if (aBoolean) {

            new AlertDialog.Builder(activity)
                    .setTitle("Comportamiento")
                    .setAdapter(new AdapterComportamiento(activity, response.rNMCDETALLECOMPORTAMIENTOResult), null)
                    .show();
        }
        remoteComportamiento = null;
        super.onPostExecute(aBoolean);
    }
}
