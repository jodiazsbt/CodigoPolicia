package com.policia.remote;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.policia.codigopolicia.Puntos.PuntosAdapter;
import com.policia.codigopolicia.Puntos.PuntosCercanos;
import com.policia.remote.request.RequestGEO;
import com.policia.remote.response.GEOPOCICIONCNPCResponse;
import com.policia.remote.response.GEOPOCICIONCNPCResult;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 29/12/2017.
 */

public class RemoteGEO extends AsyncTask<RequestGEO, Void, Void> {

    private ListView view;
    private GoogleMap mMap;
    private Activity activity;
    private RemoteClient remoteClient;
    private WebView webviewMap;

    private GEOPOCICIONCNPCResponse responseGEO;

    private static RemoteGEO remoteGEO;

    private RemoteGEO(Activity activity, ListView view, WebView webviewMap) {//GoogleMap mMap) {

        this.view = view;
        //this.mMap = mMap;
        this.activity = activity;
        this.webviewMap = webviewMap;
        this.remoteClient = new RemoteClient(activity);
    }

    public static RemoteGEO newInstance(Activity activity, ListView view, WebView webviewMap) {

        if (remoteGEO == null)
            remoteGEO = new RemoteGEO(activity, view, webviewMap);
        return remoteGEO;
    }

    @Override
    protected Void doInBackground(RequestGEO... geo) {

        String[] distancias = new String[]{
                "1000",  //si no encuentra puntos en 1mil metros
                "2000",  //si no encuentra puntos en 2mil metros
                "5000",  //si no encuentra puntos en 5mil metros
                "9999"}; //si no encuentra puntos en 9mil metros

        try {

            if (geo.length == 1) {

                for (String distancia : distancias) {

                    responseGEO = remoteClient.localizarCompetencias(
                            geo[0].Latitud,
                            geo[0].Longitud,
                            geo[0].Tipo,
                            distancia);

                    if (!(responseGEO.gEOPOCICIONCNPCResult.size() == 0))
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        if (responseGEO == null) {
            activity.finish();
            Toast.makeText(activity, "Para realizar esta búsqueda se necesita una conexión a internet", Toast.LENGTH_SHORT).show();
        } else if (responseGEO.gEOPOCICIONCNPCResult.size() == 0) {
            Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
        } else {
            final ArrayList<PuntosCercanos> puntos = new ArrayList<PuntosCercanos>();

            for (GEOPOCICIONCNPCResult punto : responseGEO.gEOPOCICIONCNPCResult) {
                puntos.add(new PuntosCercanos(
                        punto.nombreCoordenada,
                        punto.direccionCoordanada,
                        Double.parseDouble(punto.latitud.replace(",", ".")),
                        Double.parseDouble(punto.longitud.replace(",", ".")),
                        punto.distancia));
            }

            //ListView listviewPuntos = view.findViewById(R.id.listviewPuntos);
            view.setAdapter(new PuntosAdapter(activity, puntos));
            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    PuntosCercanos punto = puntos.get(i);
                    /*
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions()
                            .title(punto.Nombre)
                            .position(new LatLng(punto.Latitud, punto.Longitud))
                            .snippet(punto.Direccion));

                    webviewMap.loadUrl("https://policia.maps.arcgis.com/apps/webappviewer/index.html?id=6016fa41f3a64c3d9ffcee984626dd62&amp;center=" + punto.Latitud + "," + punto.Longitud + ",,,,&amp;level=12");
                    webviewMap.getSettings().setJavaScriptEnabled(true);
                    webviewMap.setWebChromeClient(new WebChromeClient());*/

                    Uri gmmIntentUri = Uri.parse("geo:" + punto.Latitud + "," + punto.Longitud + "?q=" + punto.Nombre);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    activity.startActivity(mapIntent);
                }
            });

        }
        remoteGEO = null;
        super.onPostExecute(aVoid);
    }
}