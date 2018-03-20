package com.policia.remote;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Base64;

import com.google.gson.Gson;
import com.policia.codigopolicia.R;
import com.policia.negocio.modelo.Capitulos.CapitulosOutput;
import com.policia.negocio.modelo.Libros.LibrosOutput;
import com.policia.negocio.modelo.Titulos.TitulosOutput;
import com.policia.remote.response.ACCIONESCIUDADANOYPOLICIACNCPResult;
import com.policia.remote.response.ArticulosyParagrafosResponse;
import com.policia.remote.response.CATEGORIASMULTASCNPCResponse;
import com.policia.remote.response.COMPETENCIASCNPCResponse;
import com.policia.remote.response.CONSULTAPOLICIAResponse;
import com.policia.remote.response.DOCUMENTOSELECCIONADOResponse;
import com.policia.remote.response.DOCUMENTOSINSTRUCTIVOSCNPCNResponse;
import com.policia.remote.response.ENCUESTASCNPCResponse;
import com.policia.remote.response.GEOPOCICIONCNPCResponse;
import com.policia.remote.response.ImagenesAvatarInfoResponse;
import com.policia.remote.response.LoginPoliciaNal;
import com.policia.remote.response.LoginPoliciaNalResult;
import com.policia.remote.response.MULTAAARTICULOYPARAGRAFOResponse;
import com.policia.remote.response.MedidasCNPCResponse;
import com.policia.remote.response.MetadataResponse;
import com.policia.remote.response.NivelesResponse;
import com.policia.remote.response.NumeralesResponse;
import com.policia.remote.response.PoliciaLoginResponse;
import com.policia.remote.response.PoliciaPerfilResponse;
import com.policia.remote.response.RELACIONCOMPETENCIANUMERALMEDIDACNPCResponse;
import com.policia.remote.response.RESPUESTAENCUESTAResponse;
import com.policia.remote.response.RNMCDETALLECOMPORTAMIENTOResponse;
import com.policia.remote.response.RNMCGENERALResponse;
import com.policia.remote.response.RNMCMEDIDACORRECTIVAResponse;
import com.policia.remote.response.RNMCTIPOSDOCResponse;
import com.policia.remote.response.TIPOSARCHIVOSResponse;
import com.policia.remote.response.UVTCNCPResult;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1085253556 on 12/12/2017.
 */

public class RemoteClient {

    private final Context context;
    private DefaultHttpClient client;
    private static RemoteClient instancia;

    private String direccionApi;
    private String direccionRENEC;
    private String direccionServicio;

    private RemoteClient(Context context) {
        this.context = context;
        this.client = new DefaultHttpClient();
        this.direccionRENEC = context.getResources().getString(R.string.srvrenec);
        this.direccionApi = context.getResources().getString(R.string.srvapplogin);
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

    private StringBuilder postLogin(String uri, String usuario, String contrasena) throws Exception {

        StringBuilder builder = new StringBuilder();
        try {

            if (!isServiceOnline())
                return null;

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
            nameValuePairs.add(new BasicNameValuePair("password", contrasena));
            nameValuePairs.add(new BasicNameValuePair("username", usuario));
            nameValuePairs.add(new BasicNameValuePair("scope", "rnmc"));

            HttpPost request = new HttpPost(uri);
            request.setHeader("accept", "application/json");
            request.setHeader("content-type", "application/x-www-form-urlencoded");
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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
            e.printStackTrace();
        }

        return builder;

    }

    private StringBuilder getPerfil(String uri, PoliciaLoginResponse login) throws Exception {

        StringBuilder builder = new StringBuilder();
        try {

            if (!isServiceOnline())
                return null;

            HttpGet request = new HttpGet(uri);
            request.setHeader("accept", "application/json");
            request.setHeader("authorization", "@type @token".replace("@type", login.tokenType).replace("@token", login.accessToken));

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
            e.printStackTrace();
        }

        return builder;
    }

    private StringBuilder getRENEC(String uri, String Documento) throws Exception {

        StringBuilder builder = new StringBuilder();
        try {

            if (!isServiceOnline())
                return null;

            final String NAMESPACE = "https://srvpqrs.policia.gov.co/wsCnp/";
            final String URL = "http://srvbiometria4.policia.gov.co/ws_renec/ServiciosRenec.asmx";
            final String SOAP_ACTION = "https://srvpqrs.policia.gov.co/wsCnp/ConsultaPersonasRenec";

            HttpPost request = new HttpPost(URL);
            request.addHeader("SOAPAction", SOAP_ACTION);
            request.addHeader("Content-Type", "text/xml");
            //request.addHeader("Host", "srvbiometria4.policia.gov.co");

            request.setEntity(new StringEntity("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "  <soap:Body>\n" +
                    "    <ConsultaPersonasRenec xmlns=" + NAMESPACE + ">\n" +
                    "      <Documento>" + Documento + "</Documento>\n" +
                    "    </ConsultaPersonasRenec>\n" +
                    "  </soap:Body>\n" +
                    "</soap:Envelope>"));

            RequestWrapper requestWrapper = new RequestWrapper(request);
            requestWrapper.setMethod("POST");requestWrapper.removeHeaders("Host");
            requestWrapper.removeHeaders("Host");
            requestWrapper.setHeader("Host", "srvbiometria4.policia.gov.co");

            HttpResponse response = client.execute(requestWrapper);
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
            e.printStackTrace();
        }

        return builder;
    }

    public LoginPoliciaNalResult LoginPoliciaNal(String usuario, String contrasena) throws Exception {
        String body = "LoginPoliciaNal/" + usuario + "," + Base64.encodeToString(contrasena.getBytes(), Base64.DEFAULT).replace("\n", "") + ",172.28.3.23,CNPC";
        StringBuilder builder = postInvoke(direccionServicio + body);
        LoginPoliciaNal result = new Gson().fromJson(builder.toString(), LoginPoliciaNal.class);
        if (result.LoginPoliciaNalResult.size() == 0) {
            return null;
        }
        return result.LoginPoliciaNalResult.get(0);
    }

    public PoliciaLoginResponse consultarRENEC(String documento) throws Exception {
        StringBuilder builder = getRENEC(Uri.parse(direccionRENEC).toString(), documento);
        PoliciaLoginResponse result = new Gson().fromJson(builder.toString(), PoliciaLoginResponse.class);
        return result;
    }

    public PoliciaLoginResponse LoginOID(String usuario, String contrasena) throws Exception {
        String uri = "ciudadano/token";
        StringBuilder builder = postLogin(Uri.parse(direccionApi + uri).toString(), usuario, contrasena);
        PoliciaLoginResponse result = new Gson().fromJson(builder.toString(), PoliciaLoginResponse.class);
        return result;
    }

    public PoliciaPerfilResponse PerfilOID(PoliciaLoginResponse login) throws Exception {
        String uri = "policia/perfil";
        StringBuilder builder = getPerfil(Uri.parse(direccionApi + uri).toString(), login);
        PoliciaPerfilResponse result = new Gson().fromJson(builder.toString(), PoliciaPerfilResponse.class);
        return result;
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

    public MULTAAARTICULOYPARAGRAFOResponse sincronizarMULTA(String fecha) throws Exception {
        String body = "MULTA_A_ARTICULOYPARAGRAFO/" + fecha;
        StringBuilder builder = getInvoke(direccionServicio + body);
        MULTAAARTICULOYPARAGRAFOResponse result = new Gson().fromJson(builder.toString(), MULTAAARTICULOYPARAGRAFOResponse.class);
        return result;
    }

    public TIPOSARCHIVOSResponse sincronizarTIPO_ARCHIVO(String fecha) throws Exception {
        String body = "TIPOS_ARCHIVOS/" + fecha;
        StringBuilder builder = getInvoke(direccionServicio + body);
        TIPOSARCHIVOSResponse result = new Gson().fromJson(builder.toString(), TIPOSARCHIVOSResponse.class);
        return result;
    }

    public DOCUMENTOSINSTRUCTIVOSCNPCNResponse sincronizarDOCUMENTO(String tipo_documento) throws Exception {
        String body = "DOCUMENTOS_INSTRUCTIVOS_CNPCN/" + tipo_documento;
        StringBuilder builder = getInvoke(direccionServicio + body);
        DOCUMENTOSINSTRUCTIVOSCNPCNResponse result = new Gson().fromJson(builder.toString(), DOCUMENTOSINSTRUCTIVOSCNPCNResponse.class);
        return result;
    }

    public CATEGORIASMULTASCNPCResponse sincronizarCATEGORIA(String fecha) throws Exception {
        String body = "CATEGORIAS_MULTAS_CNPC/" + fecha;
        StringBuilder builder = getInvoke(direccionServicio + body);
        CATEGORIASMULTASCNPCResponse result = new Gson().fromJson(builder.toString(), CATEGORIASMULTASCNPCResponse.class);
        return result;
    }

    public COMPETENCIASCNPCResponse sincronizarCOMPETENCIA(String fecha) throws Exception {
        String body = "COMPETENCIAS_CNPC/" + fecha;
        StringBuilder builder = getInvoke(direccionServicio + body);
        COMPETENCIASCNPCResponse result = new Gson().fromJson(builder.toString(), COMPETENCIASCNPCResponse.class);
        return result;
    }

    public RELACIONCOMPETENCIANUMERALMEDIDACNPCResponse sincronizarCOMPETENCIA_NUMERAL(String fecha) throws Exception {
        String body = "RELACION_COMPETENCIA_NUMERAL_MEDIDA_CNPC/" + fecha;
        StringBuilder builder = getInvoke(direccionServicio + body);
        RELACIONCOMPETENCIANUMERALMEDIDACNPCResponse result = new Gson().fromJson(builder.toString(), RELACIONCOMPETENCIANUMERALMEDIDACNPCResponse.class);
        return result;
    }

    public ACCIONESCIUDADANOYPOLICIACNCPResult sincronizarACCION(String fecha) throws Exception {
        String body = "ACCIONESCIUDADANOYPOLICIA_CNCP/" + fecha;
        StringBuilder builder = getInvoke(direccionServicio + body);
        ACCIONESCIUDADANOYPOLICIACNCPResult result = new Gson().fromJson(builder.toString(), ACCIONESCIUDADANOYPOLICIACNCPResult.class);
        return result;
    }

    public UVTCNCPResult sincronizarUVT(String fecha) throws Exception {
        String body = "UVT_CNCP/" + fecha;
        StringBuilder builder = getInvoke(direccionServicio + body);
        UVTCNCPResult result = new Gson().fromJson(builder.toString(), UVTCNCPResult.class);
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
        String body = "RESPUESTAENCUESTA/" + ID + "," + Departamento.replaceAll(" ", "%20") + "," + Fecha + "," + Opcion + "";
        StringBuilder builder = postInvoke(direccionServicio + body);
        RESPUESTAENCUESTAResponse result = new Gson().fromJson(builder.toString(), RESPUESTAENCUESTAResponse.class);
        return result;
    }

    public ImagenesAvatarInfoResponse sincronizarAVATAR(String fecha) throws Exception {
        String body = "ImagenesAvatarInfo/" + fecha;
        StringBuilder builder = postInvoke(direccionServicio + body);
        ImagenesAvatarInfoResponse result = new Gson().fromJson(builder.toString(), ImagenesAvatarInfoResponse.class);
        return result;
    }

    public RNMCTIPOSDOCResponse RNMC_TIPOS_DOC() throws Exception {
        String body = "RNMC_TIPOS_DOC";
        StringBuilder builder = getInvoke(direccionServicio + body);
        RNMCTIPOSDOCResponse result = new Gson().fromJson(builder.toString(), RNMCTIPOSDOCResponse.class);
        return result;
    }

    public RNMCGENERALResponse RNMC_GENERAL(String TipoDocumento, String Identificacion) throws Exception {
        String body = "RNMC_GENERAL/" + TipoDocumento + "," + Identificacion;
        StringBuilder builder = getInvoke(direccionServicio + body);
        RNMCGENERALResponse result = new Gson().fromJson(builder.toString(), RNMCGENERALResponse.class);
        return result;
    }

    public RNMCMEDIDACORRECTIVAResponse RNMC_MEDIDA_CORRECTIVA(String Comportamiento) throws Exception {
        String body = "RNMC_MEDIDA_CORRECTIVA/" + Comportamiento;
        StringBuilder builder = getInvoke(direccionServicio + body);
        RNMCMEDIDACORRECTIVAResponse result = new Gson().fromJson(builder.toString(), RNMCMEDIDACORRECTIVAResponse.class);
        return result;
    }

    public RNMCDETALLECOMPORTAMIENTOResponse RNMC_DETALLE_COMPORTAMIENTO(String Comportamiento, String Expediente) throws Exception {
        String body = "RNMC_DETALLE_COMPORTAMIENTO/" + Comportamiento + "," + Expediente;
        StringBuilder builder = getInvoke(direccionServicio + body);
        RNMCDETALLECOMPORTAMIENTOResponse result = new Gson().fromJson(builder.toString(), RNMCDETALLECOMPORTAMIENTOResponse.class);
        return result;
    }

    public DOCUMENTOSELECCIONADOResponse DOCUMENTOSELECCIONADO(String URL) throws Exception {
        String body = "RNMC_GENERAL/" + URL;
        StringBuilder builder = getInvoke(direccionServicio + body);
        DOCUMENTOSELECCIONADOResponse result = new Gson().fromJson(builder.toString(), DOCUMENTOSELECCIONADOResponse.class);
        return result;
    }
}
