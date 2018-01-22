package com.policia.remote;

import android.annotation.TargetApi;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.policia.codigopolicia.IdentificacionPolicia.Fragment_Identificacion;
import com.policia.codigopolicia.IdentificacionPolicia.IdentificacionAdapter;
import com.policia.codigopolicia.R;
import com.policia.codigopolicia.parser.DocumentoPolicia;
import com.policia.negocio.modelo.ValuePar;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.remote.response.CONSULTAPOLICIAResponse;
import com.policia.remote.response.CONSULTAPOLICIAResult;

import org.json.JSONException;
import org.json.XML;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 29/12/2017.
 */

public class RemotePolicia extends AsyncTask<Void, Void, RemotePolicia.MotivoErrorLectura> {

    private String barcode;
    private Fragment fragment;
    private ListView listView;
    private DocumentoPolicia document;
    private RemoteClient remoteClient;
    private CONSULTAPOLICIAResponse consulta;

    private Seguridad seguridad;

    protected Fragment_Identificacion.IDisparadorLectura lectura;

    public RemotePolicia(Fragment fragment, ListView listView, String barcode, Fragment_Identificacion.IDisparadorLectura lectura) throws Exception {

        this.barcode = barcode;
        this.fragment = fragment;
        this.listView = listView;
        this.lectura = lectura;
        this.seguridad = Seguridad.Sesion(fragment.getContext());
        this.remoteClient = new RemoteClient(fragment.getActivity());
    }

    @Override
    protected RemotePolicia.MotivoErrorLectura doInBackground(Void... voids) {

        try {
            document = new Gson().fromJson(String.valueOf(XML.toJSONObject(barcode)), DocumentoPolicia.class);

            if (document.getDocumentElement() == null)
                throw new JSONException("No se reconoce documento de policia");

            consulta = remoteClient.identificacionPolicia(
                    String.valueOf(document.getDocumentElement().getTable().getE21()),
                    "10182780",
                    //String.valueOf(document.getDocumentElement().getTable().getE2()),
                    seguridad.getUsuario().equals("1") ? "0" : "1");
            return MotivoErrorLectura.NINGUNO;
        } catch (JSONException e) {
            e.printStackTrace();
            return MotivoErrorLectura.TRAMA;
        } catch (Exception e) {
            e.printStackTrace();
            return MotivoErrorLectura.INTERNET;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(RemotePolicia.MotivoErrorLectura error) {

        lectura.realizarLectura(error);
        ArrayList<ValuePar> valores = new ArrayList<ValuePar>();

        if (MotivoErrorLectura.INTERNET == error) {
            Toast.makeText(fragment.getContext(), "Para identificar al policía se necesita una conexión a internet", Toast.LENGTH_SHORT).show();
        } else if (MotivoErrorLectura.TRAMA == error) {
            Toast.makeText(fragment.getContext(), "El documento que está leyendo no corresponde con un carnet vigente", Toast.LENGTH_SHORT).show();
        } else {
            for (CONSULTAPOLICIAResult identificacion : consulta.cONSULTAPOLICIAResult) {

                if (seguridad.getUsuario().equals("1")) {//es ciudadano

                    valores.add(new ValuePar("", identificacion.mensaje));
                    valores.add(new ValuePar("CEDULA", String.valueOf(document.getDocumentElement().getTable().getE2())));
                    valores.add(new ValuePar("APELLIDO", String.valueOf(document.getDocumentElement().getTable().getE4())));
                    valores.add(new ValuePar("NOMBRE", String.valueOf(document.getDocumentElement().getTable().getE5())));
                    valores.add(new ValuePar("GRADO", String.valueOf(document.getDocumentElement().getTable().getE11())));
                    valores.add(new ValuePar("CARNET", String.valueOf(document.getDocumentElement().getTable().getE15())));

                    //listView.addHeaderView(fragment.getLayoutInflater().inflate(R.layout.policia_identificacion_header, null), null, false);
                } else {//es policia

                    valores.add(new ValuePar("ESTADO", identificacion.estado));
                    valores.add(new ValuePar("UNIDAD", identificacion.unidad));
                    valores.add(new ValuePar("CEDULA", String.valueOf(document.getDocumentElement().getTable().getE2())));
                    valores.add(new ValuePar("APELLIDO", String.valueOf(document.getDocumentElement().getTable().getE4())));
                    valores.add(new ValuePar("NOMBRE", String.valueOf(document.getDocumentElement().getTable().getE5())));
                    valores.add(new ValuePar("GRADO", String.valueOf(document.getDocumentElement().getTable().getE11())));
                    valores.add(new ValuePar("CARNET", String.valueOf(document.getDocumentElement().getTable().getE15())));

                }

                View header = fragment.getLayoutInflater().inflate(R.layout.policia_identificacion_header, null);
                ImageView image = header.findViewById(R.id.imageViewCaricatura);
                image.setImageBitmap(BitmapFactory.decodeByteArray(identificacion.imagen, 0, identificacion.imagen.length));
                image.setCropToPadding(true);
                listView.addHeaderView(header, null, false);

                listView.setAdapter(new IdentificacionAdapter(fragment, valores));

            }
        }
        super.onPostExecute(error);
    }

    public enum MotivoErrorLectura {
        TRAMA,
        INTERNET,
        NINGUNO;
    }
}
