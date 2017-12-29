package com.policia.remote;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.policia.codigopolicia.R;
import com.policia.negocio.modelo.Capitulos.CapitulosOutput;
import com.policia.negocio.modelo.Libros.LibrosOutput;
import com.policia.negocio.modelo.Niveles.NivelesOutput;
import com.policia.negocio.modelo.Titulos.TitulosOutput;
import com.policia.remote.response.CONSULTAPOLICIAResponse;
import com.policia.remote.response.ENCUESTASCNPCResponse;
import com.policia.remote.response.GEOPOCICIONCNPCResponse;
import com.policia.remote.response.LoginPoliciaNal;
import com.policia.remote.response.LoginPoliciaNalResult;
import com.policia.remote.response.RESPUESTAENCUESTAResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
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

    public RemoteClient(Context context) {
        this.context = context;
        this.client = new DefaultHttpClient();
        this.direccionServicio = context.getResources().getString(R.string.srvappmoviles);
    }

    public static RemoteClient connect(Context context) {

        if (instancia == null)
            instancia = new RemoteClient(context);

        return instancia;
    }

    private StringBuilder postInvoke(String operation) throws Exception {
        HttpPost request = new HttpPost(operation);
        request.setHeader("accept", "application/json");
        request.setHeader("content-type", "application/json; charset=utf-8");
        StringBuilder builder = new StringBuilder();
        try {
            HttpResponse response = client.execute(request);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                InputStream content = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                content.close();
            } else
                throw new Exception(statusLine.getReasonPhrase());
        } catch (Exception e) {
            //Toast.makeText(this.context, "No tienes acceso a Internet!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return builder;

    }

    private StringBuilder getInvoke(String operation) throws Exception {
        HttpGet request = new HttpGet(operation);
        request.setHeader("accept", "application/json");
        request.setHeader("content-type", "application/json; charset=utf-8");
        StringBuilder builder = new StringBuilder();
        try {
            HttpResponse response = client.execute(request);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                InputStream content = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                content.close();
            } else
                throw new Exception(statusLine.getReasonPhrase());
        } catch (Exception e) {
            //Toast.makeText(this.context, "No tienes acceso a Internet!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return builder;

    }

    public LoginPoliciaNalResult login(String usuario, String contrasena) throws Exception {
        String body = "LoginPoliciaNal/" + usuario + "," + contrasena + ",172.28.3.23,CNPC";
        StringBuilder builder = postInvoke(direccionServicio + body);
        LoginPoliciaNal result = new Gson().fromJson(builder.toString(), LoginPoliciaNal.class);
        if (result.LoginPoliciaNalResult.size() == 0) {
            return null;
        }
        return result.LoginPoliciaNalResult.get(0);
    }

    public NivelesOutput sincronizarNIVEL(String fecha) throws Exception {
        String body = "Niveles/" + fecha;
        StringBuilder builder = getInvoke(direccionServicio + body);
        NivelesOutput result = new Gson().fromJson(builder.toString(), NivelesOutput.class);
        return result;
    }

    public LibrosOutput sincronizarLIBRO(String fecha) throws Exception {
        String body = "Libros/" + fecha;
        StringBuilder builder = getInvoke(direccionServicio + body);
        LibrosOutput result = new Gson().fromJson(builder.toString(), LibrosOutput.class);
        return result;
    }

    public TitulosOutput sincronizarTITULO(String fecha) throws Exception {
        String body = "Titulos/" + fecha;
        StringBuilder builder = getInvoke(direccionServicio + body);
        TitulosOutput result = new Gson().fromJson(builder.toString(), TitulosOutput.class);
        return result;
    }

    public CapitulosOutput sincronizarCAPITULO(String fecha) throws Exception {
        String body = "Capitulos/" + fecha;
        StringBuilder builder = getInvoke(direccionServicio + body);
        CapitulosOutput result = new Gson().fromJson(builder.toString(), CapitulosOutput.class);
        return result;
    }

    public GEOPOCICIONCNPCResponse localizarCompetencias(String latitud, String longitud, String tipo, String distancia) throws Exception {
        String body = "GEOPOCICION_CNPC/" + latitud + "&" + longitud + "&" + tipo + "&" + distancia;
        StringBuilder builder = postInvoke(direccionServicio + body);
        GEOPOCICIONCNPCResponse result = new Gson().fromJson(builder.toString(), GEOPOCICIONCNPCResponse.class);
        return result;
    }

    public CONSULTAPOLICIAResponse identificacionPolicia(String tipoDocumento, String nroDocumento, String esPolicia) throws Exception {
        String body = "CONSULTAPOLICIA/" + tipoDocumento + "," + nroDocumento + "," + esPolicia;
        StringBuilder builder = postInvoke(direccionServicio + body);
        CONSULTAPOLICIAResponse result = new Gson().fromJson(builder.toString(), CONSULTAPOLICIAResponse.class);
        return result;
    }

    public ENCUESTASCNPCResponse consultarEncuentas() throws Exception {
        String body = "ENCUESTAS_CNPC";
        StringBuilder builder = getInvoke(direccionServicio + body);
        ENCUESTASCNPCResponse result = new Gson().fromJson(builder.toString(), ENCUESTASCNPCResponse.class);
        return result;
    }

    public RESPUESTAENCUESTAResponse responderEncuenta(String ID, String Departamento, String Fecha, String Opcion) throws Exception {
        String body = "RESPUESTAENCUESTA/" + ID + "," + Departamento + "," + Fecha + "," + Opcion + "";
        StringBuilder builder = postInvoke(direccionServicio + body);
        RESPUESTAENCUESTAResponse result = new Gson().fromJson(builder.toString(), RESPUESTAENCUESTAResponse.class);
        return result;
    }
}
