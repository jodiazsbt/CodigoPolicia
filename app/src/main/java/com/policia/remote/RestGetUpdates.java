package com.policia.remote;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.policia.codigopolicia.R;
import com.policia.negocio.modelo.Capitulos.CapitulosOutput;
import com.policia.negocio.modelo.Libros.LibrosOutput;
import com.policia.negocio.modelo.Titulos.TitulosOutput;
import com.policia.persistencia.rutinas.Rutinas_CAPITULO;
import com.policia.persistencia.rutinas.Rutinas_LIBRO;
import com.policia.persistencia.rutinas.Rutinas_TITULO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ppronovost on 17/12/17.
 */

public class RestGetUpdates extends AsyncTask<String, Void, Boolean> {
    private DefaultHttpClient client;
    private String direccionServicio;
    private final Context context;

    public RestGetUpdates(Context context) {
        this.context = context;
        this.client = new DefaultHttpClient();
        this.direccionServicio = context.getResources().getString(R.string.srvappmoviles);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        //now call Auth
        return UpdateRecords(params[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {

        super.onPostExecute(result);

        if(result){

            //successful login. Display a Toast or take the user to next screen by using intent.
        }
        else {

            //Display Authentication failed error message.

        }
    }

    private InputStream invoke(String operation) throws Exception {
        HttpGet request = new HttpGet(operation);
        request.setHeader("accept", "application/json");
        request.setHeader("content-type", "application/json; charset=utf-8");
        HttpEntity entity = null;
        try {
            HttpResponse response = client.execute(request);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                entity = response.getEntity();
            } else
                throw new Exception(statusLine.getReasonPhrase());
        } catch (Exception e) {
            Toast.makeText(this.context, "No tienes acceso a Internet!", Toast.LENGTH_SHORT).show();
        }


        return entity.getContent();

    }

    public StringBuilder getUpdates(String body) throws Exception {
        InputStream content = invoke(direccionServicio + body);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(content));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        content.close();

        return builder;
    }



    public boolean UpdateRecords(String mod)
    {
        String lastUpdate = "";
        String body = "";
        StringBuilder sb = null;
        switch(mod) {
            case "LIBROS":
                Rutinas_LIBRO rl = new Rutinas_LIBRO(this.context);
                lastUpdate = rl.maxFecha();
                body = mod + "/ENG," + lastUpdate;

                try {
                    sb = getUpdates(body);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                LibrosOutput resultLibros = new Gson().fromJson(sb.toString(), LibrosOutput.class);

                if (resultLibros.LibrosResult.size() > 0) {
                    for(int i = 0; i < resultLibros.LibrosResult.size(); i++){
                        boolean res = false;//rl.updateRecord(resultLibros.LibrosResult.get(i));
                    }
                }

                break;
            case "NIVELES":
                /*
                Rutinas_NIVELES rn = new Rutinas_NIVELES(this.context);
                lastUpdate = rn.maxFecha();
                body = mod + "/ENG," + lastUpdate;

                try {
                    sb = getUpdates(body);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                NivelesOutput result = new Gson().fromJson(sb.toString(), NivelesOutput.class);

                if (result.NivelesResult.size() > 0) {
                    for(int i = 0; i < result.NivelesResult.size(); i++){
                        boolean res = rn.updateRecord(result.NivelesResult.get(i));
                    }
                }
                */
                break;
            case "TITULOS":
                Rutinas_TITULO rt = new Rutinas_TITULO(this.context);
                lastUpdate = rt.maxFecha();
                body = mod + "/ENG," + lastUpdate;

                try {
                    sb = getUpdates(body);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                TitulosOutput resultTitulos = new Gson().fromJson(sb.toString(), TitulosOutput.class);

                if (resultTitulos.TitulosResult.size() > 0) {
                    for(int i = 0; i < resultTitulos.TitulosResult.size(); i++){
                        boolean res = false;//rt.updateRecord(resultTitulos.TitulosResult.get(i));
                    }
                }
                break;
            case "CAPITULOS":
                Rutinas_CAPITULO rc = new Rutinas_CAPITULO(this.context);
                lastUpdate = rc.maxFecha();
                body = mod + "/ENG," + lastUpdate;

                try {
                    sb = getUpdates(body);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                CapitulosOutput resultCapitulos = new Gson().fromJson(sb.toString(), CapitulosOutput.class);

                if (resultCapitulos.CapitulosResult.size() > 0) {
                    for(int i = 0; i < resultCapitulos.CapitulosResult.size(); i++){
                        boolean res = false;//rc.updateRecord(resultCapitulos.CapitulosResult.get(i));
                    }
                }
                break;
            case "ARTICULOS":


                break;
            default:

        }
        // your logic.
        return true;
    }
}
