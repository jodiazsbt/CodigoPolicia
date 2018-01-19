package com.policia.codigopolicia.html;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.policia.codigopolicia.PuntosActivity;
import com.policia.codigopolicia.R;
import com.policia.codigopolicia.adapter.Comparendo_Adapter;
import com.policia.codigopolicia.adapter.Competencia_Adapter;
import com.policia.negocio.logica.Negocio_ACCION;
import com.policia.negocio.logica.Negocio_COMPETENCIA;
import com.policia.negocio.logica.Negocio_MEDIDA;
import com.policia.negocio.modelo.Modelo_COMPENTENCIA;
import com.policia.negocio.modelo.Modelo_MEDIDA;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 9/01/2018.
 */

public class WebViewInterface {
    Activity mActivity;

    Negocio_ACCION negocioAccion;
    Negocio_COMPETENCIA negocioCompetencia;
    Negocio_MEDIDA negocioMedida;

    /**
     * Instantiate the interface and set the context
     */
    public WebViewInterface(Activity a) {
        mActivity = a;
        try {
            negocioAccion = new Negocio_ACCION(a);
            negocioMedida = new Negocio_MEDIDA(a);
            negocioCompetencia = new Negocio_COMPETENCIA(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mActivity, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void dialogNumeral(final String ID) {
        final String[] items = mActivity.getResources().getStringArray(R.array.listaMedidas);
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("RESULTADO DE LA SOLICITUD");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, final int item) {
                // Do something with the selection
                dialog.dismiss();
                switch (item) {
                    case 0:
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                String html_plantilla_procedimientos = new HTML_Plantillas(mActivity, HTML_Plantillas.Plantilla.PROCEDIMIENTOS).getPlantilla();

                                final WebView webView = new WebView(mActivity);
                                webView.loadData(html_plantilla_procedimientos.replace("@accion", negocioAccion.accionCiudadano()), "text/html", "utf-8");
                                new AlertDialog.Builder(mActivity)
                                        .setTitle(items[item])
                                        .setView(webView)
                                        .show();
                            }
                        });
                        break;
                    case 1:
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final ArrayList<Modelo_MEDIDA> medidas = negocioMedida.ComparendosNumeral(ID);
                                new AlertDialog.Builder(mActivity)
                                        .setTitle(items[item])
                                        .setAdapter(new Comparendo_Adapter(mActivity, medidas), null)
                                        .show();
                            }
                        });
                        break;
                    case 2:
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final ArrayList<Modelo_COMPENTENCIA> competencias = negocioCompetencia.competenciasPorNumeral(ID);
                                new AlertDialog.Builder(mActivity)
                                        .setTitle(items[item])
                                        .setAdapter(new Competencia_Adapter(mActivity, competencias), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int item) {

                                                Bundle b = new Bundle();
                                                b.putString("tipo_competencia", competencias.get(item).ID);

                                                Intent intent = new Intent(mActivity, PuntosActivity.class);
                                                intent.putExtras(b); //Put your id to your next Intent
                                                mActivity.startActivity(intent);
                                            }
                                        }).show();
                            }
                        });
                        break;
                }
            }
        }).show();
    }
}
