package com.policia.remote;

import android.content.Context;

import com.google.gson.Gson;
import com.policia.codigopolicia.R;
import com.policia.remote.response.LoginPoliciaNal;
import com.policia.remote.response.LoginPoliciaNalResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by 1085253556 on 12/12/2017.
 */

public class RemoteClient {

    private final Context context;
    private DefaultHttpClient client;
    private static RemoteClient instancia;

    private String direccionServicio;

    private RemoteClient(Context context) {
        this.context = context;
        this.direccionServicio = context.getResources().getString(R.string.srvappmoviles);
    }

    public static RemoteClient connect(Context context) {

        if (instancia == null)
            instancia = new RemoteClient(context);

        return instancia;
    }

    private InputStream invoke(String operation) throws Exception {
        HttpPost request = new HttpPost(operation);
        request.setHeader("accept", "application/json");
        request.setHeader("content-type", "application/json; charset=utf-8");
        //request.setEntity(new StringEntity(null));
        client = new DefaultHttpClient();
        HttpResponse response = client.execute(request);
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            return entity.getContent();
        } else
            throw new Exception(statusLine.getReasonPhrase());
    }

    public LoginPoliciaNalResult login(String usuario, String contrasena) throws Exception {
        String body = "LoginPoliciaNal/" + usuario + "," + contrasena + ",172.28.3.23,CNPC";
        InputStream content = invoke(direccionServicio + body);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(content));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        content.close();
        LoginPoliciaNal result = new Gson().fromJson(builder.toString(), LoginPoliciaNal.class);
        return result.LoginPoliciaNalResult.get(0);
    }
}
