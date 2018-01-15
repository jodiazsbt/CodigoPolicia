package com.policia.remote;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.policia.codigopolicia.R;
import com.policia.negocio.modelo.Capitulos.CapitulosOutput;
import com.policia.negocio.modelo.Libros.LibrosOutput;
import com.policia.negocio.modelo.Titulos.TitulosOutput;
import com.policia.remote.response.ArticulosyParagrafosResponse;
import com.policia.remote.response.CONSULTAPOLICIAResponse;
import com.policia.remote.response.ENCUESTASCNPCResponse;
import com.policia.remote.response.GEOPOCICIONCNPCResponse;
import com.policia.remote.response.LoginPoliciaNal;
import com.policia.remote.response.LoginPoliciaNalResult;
import com.policia.remote.response.MULTAAARTICULOYPARAGRAFOResponse;
import com.policia.remote.response.MedidasCNPCResponse;
import com.policia.remote.response.MetadataResponse;
import com.policia.remote.response.NivelesResponse;
import com.policia.remote.response.NumeralesResponse;
import com.policia.remote.response.RESPUESTAENCUESTAResponse;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public boolean isServiceOnline() {
        try {

            if (!isOnline())
                return false;

            URL _url = new URL(direccionServicio);
            HttpURLConnection urlc = (HttpURLConnection) _url.openConnection();
            urlc.setReadTimeout(5000);
            urlc.setRequestMethod("HEAD");
            urlc.connect();
            return true;//SERVICIO ENCENDIDO
        } catch (java.net.SocketTimeoutException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isOnline() {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private StringBuilder postInvoke(String operation) throws Exception {

        StringBuilder builder = new StringBuilder();
        try {

            if (!isServiceOnline())
                return null;

            HttpPost request = new HttpPost(operation);
            request.setHeader("accept", "application/json");
            request.setHeader("content-type", "application/json; charset=utf-8");

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
        StringBuilder builder = new StringBuilder();
        try {

            if (!isServiceOnline())
                return null;

            HttpGet request = new HttpGet(operation);
            request.setHeader("accept", "application/json");
            request.setHeader("content-type", "application/json; charset=utf-8");

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

    public LoginPoliciaNalResult LoginPoliciaNal(String usuario, String contrasena) throws Exception {
        String body = "LoginPoliciaNal/" + usuario + "," + contrasena + ",172.28.3.23,CNPC";
        StringBuilder builder = postInvoke(direccionServicio + body);
        LoginPoliciaNal result = new Gson().fromJson(builder.toString(), LoginPoliciaNal.class);
        if (result.LoginPoliciaNalResult.size() == 0) {
            return null;
        }
        return result.LoginPoliciaNalResult.get(0);
    }

    public NivelesResponse sincronizarNIVEL(String fecha) throws Exception {
        String body = "Niveles/" + fecha;
        StringBuilder builder = getInvoke(direccionServicio + body);
        NivelesResponse result = new Gson().fromJson(builder.toString(), NivelesResponse.class);
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

    public ArticulosyParagrafosResponse sincronizarARTICULO(String fecha) throws Exception {
        String body = "ArticulosyParagrafos/" + fecha;
        StringBuilder builder = getInvoke(direccionServicio + body);
        ArticulosyParagrafosResponse result = new Gson().fromJson(builder.toString(), ArticulosyParagrafosResponse.class);
        return result;
    }

    public MetadataResponse sincronizarMETADATA(String fecha) throws Exception {
        String body = "Metadata/" + fecha;
        StringBuilder builder = getInvoke(direccionServicio + body);
        MetadataResponse result = new Gson().fromJson(builder.toString(), MetadataResponse.class);
        return result;
    }

    public NumeralesResponse sincronizarNUMERAL(String fecha) throws Exception {
        String body = "Numerales/" + fecha;
        StringBuilder builder = getInvoke(direccionServicio + body);
        NumeralesResponse result = new Gson().fromJson(builder.toString(), NumeralesResponse.class);
        return result;
    }

    public MedidasCNPCResponse sincronizarMEDIDA(String fecha) throws Exception {
        String body = "MedidasCNPC/" + fecha;
        StringBuilder builder = getInvoke(direccionServicio + body);
        MedidasCNPCResponse result = new Gson().fromJson(builder.toString(), MedidasCNPCResponse.class);
        return result;
    }

    public MULTAAARTICULOYPARAGRAFOResponse sincronizarCOMPETENCIA_NUMERAL(String fecha) throws Exception {
        String body = "MULTA_A_ARTICULOYPARAGRAFO/" + fecha;
        StringBuilder builder = getInvoke(direccionServicio + body);
        MULTAAARTICULOYPARAGRAFOResponse result = new Gson().fromJson(builder.toString(), MULTAAARTICULOYPARAGRAFOResponse.class);
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
