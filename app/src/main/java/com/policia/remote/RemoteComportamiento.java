package com.policia.remote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.ExpandableListView;

import com.policia.codigopolicia.Comparendos.AdapterComportamiento;
import com.policia.remote.response.RNMCDETALLECOMPORTAMIENTOResponse;

/**
 * Created by 1085253556 on 2/02/2018.
 */

public class RemoteComportamiento extends AsyncTask<Void, Void, Boolean> {

    private Activity activity;
    private RemoteClient remoteClient;
    private String Comportamiento, Expediente;
    private RNMCDETALLECOMPORTAMIENTOResponse response;

    public RemoteComportamiento(Activity activity, String Comportamiento, String Expediente) {

        this.activity = activity;
        this.Expediente = Expediente;
        this.Comportamiento = Comportamiento;
        this.remoteClient = new RemoteClient(activity);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        try {
            response = remoteClient.RNMC_DETALLE_COMPORTAMIENTO(Comportamiento, Expediente);
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

        super.onPostExecute(aBoolean);
    }
}
