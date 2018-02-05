package com.policia.remote.response;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;

import com.policia.codigopolicia.Comparendos.AdapterComparendos;
import com.policia.remote.RemoteClient;

/**
 * Created by 1085253556 on 2/02/2018.
 */

public class RemoteComparendos extends AsyncTask<Void, Void, Boolean> {

    private Activity activity;
    private String Comportamiento;
    private RemoteClient remoteClient;
    private RNMCMEDIDACORRECTIVAResponse response;

    public RemoteComparendos(Activity activity, String Comportamiento) {

        this.activity = activity;
        this.Comportamiento = Comportamiento;
        this.remoteClient = new RemoteClient(activity);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        try {
            response = remoteClient.RNMC_MEDIDA_CORRECTIVA(Comportamiento);
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
                    .setTitle("Comparendos")
                    .setAdapter(new AdapterComparendos(activity, response.rNMCMEDIDACORRECTIVAResult), null)
                    .show();
        }

        super.onPostExecute(aBoolean);
    }
}
