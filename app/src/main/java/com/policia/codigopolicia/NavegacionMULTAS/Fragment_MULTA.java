package com.policia.codigopolicia.NavegacionMULTAS;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.policia.codigopolicia.ArticuloMultaActivity;
import com.policia.codigopolicia.R;
import com.policia.codigopolicia.html.HTML_Plantillas;
import com.policia.negocio.logica.Negocio_ACCION;
import com.policia.negocio.logica.Negocio_CATEGORIA;
import com.policia.negocio.logica.Negocio_DOCUMENTO;
import com.policia.negocio.logica.Negocio_MULTA;
import com.policia.negocio.modelo.Modelo_CATEGORIA;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 24/11/2017.
 */

public class Fragment_MULTA extends Fragment implements View.OnClickListener {

    private FloatingActionButton fab;
    private ListView listviewMulta;
    //private ExpandableListView expandableMulta;

    private Negocio_CATEGORIA negocio_categoria;

    private int counter = 0;
    private ShowcaseView showcaseView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.multa_fragment, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle state) {
        super.onActivityCreated(state);

        try {
            View header = getLayoutInflater().inflate(R.layout.multa_expandable_header, null);
            fab = header.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Negocio_ACCION negocioAccion = null;
                    try {

                        negocioAccion = new Negocio_ACCION(getActivity());

                        String html_plantilla_procedimientos = new HTML_Plantillas(getActivity(), HTML_Plantillas.Plantilla.PROCEDIMIENTOS).getPlantilla();

                        final WebView webView = new WebView(getActivity());
                        webView.loadData(html_plantilla_procedimientos.replace("@accion", negocioAccion.accionCiudadano()), "text/html", "utf-8");
                        new AlertDialog.Builder(getActivity())
                                .setTitle("PROCEDIMIENTO CIUDADANO")
                                .setView(webView)
                                .show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            /*
            expandableMulta = getView().findViewById(R.id.expandableMulta);
            expandableMulta.addHeaderView(header);
            expandableMulta.setAdapter(new Multa_BaseExpandableListAdapter(this, new Negocio_MULTA(getContext()).Multas()));
            expandableMulta.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {

                    Intent intent = new Intent(view.getContext(), ArticuloMultaActivity.class);
                    intent.putExtra("multa", ((Multa_BaseExpandableListAdapter) parent.getExpandableListAdapter()).MultaID(groupPosition));
                    intent.putExtra("categoria", id);
                    intent.putExtra("posicion", 0);//primera página
                    getContext().startActivity(intent);

                    return true;
                }
            });*/

            negocio_categoria = new Negocio_CATEGORIA(getContext());

            new Negocio_DOCUMENTO(getContext()).drawAVATAR(Negocio_DOCUMENTO.AVATAR.SCREEN_MULTA,
                    (ImageView) header.findViewById(R.id.imageViewCaricatura));

            listviewMulta = getView().findViewById(R.id.listviewMulta);
            listviewMulta.addHeaderView(header);
            listviewMulta.setAdapter(new Multa_Adapter(getActivity(), new Negocio_MULTA(getContext()).Multas()));
            listviewMulta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, final View view, int childPosition, final long id) {

                    final ArrayList<Modelo_CATEGORIA> categorias = negocio_categoria.CategoriaPorTipoMulta(String.valueOf(id));

                    if (categorias.size() == 1) {

                        mostrarArticulos(id, categorias.get(0).ID, 0);
                    } else if (categorias.size() > 1) {

                        new AlertDialog.Builder(getActivity())
                                .setTitle(getResources().getString(R.string.titulo_multa_categoria))
                                .setAdapter(new Categoria_Adapter(getActivity(), categorias), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int pos) {

                                        dialog.dismiss();
                                        mostrarArticulos(id, categorias.get(pos).ID, 0);
                                    }
                                })
                                .show();
                    }
                }
            });

            showcaseView = new ShowcaseView.Builder(getActivity())
                    .withMaterialShowcase()
                    .singleShot(R.layout.multa_fragment)
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .setContentTitle("Multas")
                    .setContentText("El Código Nacional de Policía y Convivencia “Para Vivir en Paz”. Establece en que comportamientos contrarios a la convivencia un ciudadano debe ser sancionado.")
                    .setOnClickListener(this)
                    .build();
            showcaseView.setButtonText(getResources().getString(R.string.showcaseSiguiente));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarArticulos(long multa, String categoria, int posicion) {

        Intent intent = new Intent(getActivity(), ArticuloMultaActivity.class);
        intent.putExtra("multa", multa);
        intent.putExtra("categoria", categoria);
        intent.putExtra("posicion", posicion);//primera página
        getContext().startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (counter) {
            case 0:

                ViewTarget viewTarget = new ViewTarget(fab);
                showcaseView.setTarget(viewTarget);
                showcaseView.setContentTitle("Procedimiento ciudadano");
                showcaseView.setContentText("Conozca las herramientas que tiene como ciudadano si ha sido sancionado por el Código Nacional de Policía.");
                showcaseView.setButtonText(getResources().getString(R.string.showcaseEntendido));
                break;
            case 1:

                showcaseView.hide();
                break;
        }
        counter++;
    }
}
