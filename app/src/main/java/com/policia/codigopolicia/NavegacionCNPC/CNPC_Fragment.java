package com.policia.codigopolicia.NavegacionCNPC;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.policia.codigopolicia.R;
import com.policia.codigopolicia.html.HTML_Plantillas;
import com.policia.codigopolicia.html.WebViewInterface;
import com.policia.negocio.logica.Negocio_ARTICULO;
import com.policia.negocio.logica.Negocio_COMPETENCIA;
import com.policia.negocio.logica.Negocio_MEDIDA;
import com.policia.negocio.logica.Negocio_NUMERAL;
import com.policia.negocio.modelo.Modelo_ARTICULO;
import com.policia.negocio.modelo.Modelo_MEDIDA;
import com.policia.negocio.modelo.Modelo_NUMERAL;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 1/12/2017.
 */

public class CNPC_Fragment extends Fragment {

    private Negocio_ARTICULO negocioArticulo;
    private Negocio_NUMERAL negocioNumeral;
    private Negocio_MEDIDA negocioMedida;
    private Negocio_COMPETENCIA negocioCompetencia;

    private WebView webViewArticulo;

    private int position;
    private String termino;
    private String capitulo;

    public static CNPC_Fragment newInstance(int position, String capitulo, String termino) {
        CNPC_Fragment _cnpcFragment = new CNPC_Fragment();
        Bundle args = new Bundle();
        args.putString("capitulo", capitulo);
        args.putInt("position", position);
        _cnpcFragment.setArguments(args);

        _cnpcFragment.position = position;
        _cnpcFragment.termino = termino;
        _cnpcFragment.capitulo = capitulo;

        return _cnpcFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //position = getArguments().getInt("position", 0);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context context = inflater.getContext();

        View view = inflater.inflate(R.layout.articulo_pager, container, false);

        try {
            negocioArticulo = new Negocio_ARTICULO(context);
            negocioNumeral = new Negocio_NUMERAL(context);
            negocioMedida = new Negocio_MEDIDA(context);
            negocioCompetencia = new Negocio_COMPETENCIA(context);

            ArrayList<Modelo_ARTICULO> articulos = null;
            articulos = negocioArticulo.ArticulosPorCapitulo(capitulo, position + 1);

            String busqueda = "<span style='background:green;color:white;'>@termino</span>";
            String html_share = new HTML_Plantillas(getActivity(), HTML_Plantillas.Plantilla.SHARE).getPlantilla();
            String html_plantilla_articulos = new HTML_Plantillas(getActivity(), HTML_Plantillas.Plantilla.ARTICULO).getPlantilla();

            for (Modelo_ARTICULO articulo : articulos) {

                html_plantilla_articulos = html_plantilla_articulos
                        .replace("@Nivel", termino.equals("") ? articulo.Nivel : articulo.Nivel.replace(termino, busqueda.replace("@termino", termino)))
                        .replace("@Titulo", termino.equals("") ? articulo.Titulo : articulo.Titulo.replace(termino, busqueda.replace("@termino", termino)))
                        .replace("@Capitulo_Nivel", termino.equals("") ? articulo.Capitulo_Nivel : articulo.Capitulo_Nivel.replace(termino, busqueda.replace("@termino", termino)))
                        .replace("@Capitulo_Descripcion", termino.equals("") ? articulo.Capitulo_Descripcion : articulo.Capitulo_Descripcion.replace(termino, busqueda.replace("@termino", termino)))
                        .replace("@Articulo_Nivel", termino.equals("") ? articulo.Articulo_Nivel : articulo.Articulo_Nivel.replace(termino, busqueda.replace("@termino", termino)))
                        .replace("@Articulo_Titulo", termino.equals("") ? articulo.Articulo_Titulo : articulo.Articulo_Titulo.replace(termino, busqueda.replace("@termino", termino)))
                        .replace("@Articulo_Descripcion", termino.equals("") ? articulo.Articulo_Descripcion : articulo.Articulo_Descripcion.replace(termino, busqueda.replace("@termino", termino)));

                html_share = html_share
                        .replace("@Nivel", termino.equals("") ? articulo.Nivel : articulo.Nivel.replace(termino, busqueda.replace("@termino", termino)))
                        .replace("@Titulo", termino.equals("") ? articulo.Titulo : articulo.Titulo.replace(termino, busqueda.replace("@termino", termino)))
                        .replace("@Capitulo_Nivel", termino.equals("") ? articulo.Capitulo_Nivel : articulo.Capitulo_Nivel.replace(termino, busqueda.replace("@termino", termino)))
                        .replace("@Capitulo_Descripcion", termino.equals("") ? articulo.Capitulo_Descripcion : articulo.Capitulo_Descripcion.replace(termino, busqueda.replace("@termino", termino)))
                        .replace("@Articulo_Nivel", termino.equals("") ? articulo.Articulo_Nivel : articulo.Articulo_Nivel.replace(termino, busqueda.replace("@termino", termino)))
                        .replace("@Articulo_Titulo", termino.equals("") ? articulo.Articulo_Titulo : articulo.Articulo_Titulo.replace(termino, busqueda.replace("@termino", termino)))
                        .replace("@Articulo_Descripcion", termino.equals("") ? articulo.Articulo_Descripcion : articulo.Articulo_Descripcion.replace(termino, busqueda.replace("@termino", termino)));

                ArrayList<Modelo_NUMERAL> numerales = negocioNumeral.NumeralesPorArticulo(articulo.ID);

                String parrafo_numeral = "<p align='justify'>@Nivel &#09; @Numeral@TAG</p>";
                String tag = " <small><a href='javascript:dialogNumeral(@ID)'>(" + getString(R.string.ver_procedimiento) + ")</a></small>";
                if (!(numerales.size() == 0)) {

                    int medidas = 0;
                    int competencias = 0;

                    int pos = 0;
                    do {
                        Modelo_NUMERAL numeral = numerales.get(pos++);

                        medidas = negocioMedida.countComparendosNumeral(numeral.ID);
                        competencias = negocioCompetencia.countCompetenciasPorNumeral(numeral.ID);

                        if ((competencias + medidas) == 0) {
                            parrafo_numeral = parrafo_numeral.replace("@TAG", "");
                        } else {
                            parrafo_numeral = parrafo_numeral.replace("@TAG", tag.replace("@ID", numeral.ID));
                        }

                        html_plantilla_articulos = html_plantilla_articulos
                                .replace("@Numerales", parrafo_numeral
                                        .replace("@Nivel", termino.equals("") ? numeral.Nivel : numeral.Nivel.replace(termino, busqueda.replace("@termino", termino)))
                                        .replace("@Numeral", termino.equals("") ? numeral.Numeral : numeral.Numeral.replace(termino, busqueda.replace("@termino", termino))) + "@Numerales");
                    } while (pos < numerales.size());
                }

                html_plantilla_articulos = html_plantilla_articulos.replace("@Numerales", "");

                ArrayList<Modelo_MEDIDA> medidas = negocioMedida.MedidasPorParagrafo(articulo.ID);

                String articlo_medidas = context.getResources().getString(R.string.articlo_medidas);
                String articlo_comportamiento = context.getResources().getString(R.string.articlo_comportamiento);

                String filas_medidas = "<tr><td>@Nivel @Comportamiento</td><td style='text-align:justify'>@Medida</td></tr>";
                String tabla_medidas = "<table><thead><tr><th>" + articlo_comportamiento + "</th><th>" + articlo_medidas + "</th></tr></thead><tbody>@tr</tbody></table>";
                if (!(medidas.size() == 0)) {

                    int pos = 0;
                    do {
                        Modelo_MEDIDA medida = medidas.get(pos++);
                        tabla_medidas = tabla_medidas.replace("@tr", filas_medidas
                                .replace("@Nivel", termino.equals("") ? medida.Nivel : medida.Nivel.replace(termino, busqueda.replace("@termino", termino)))
                                .replace("@Comportamiento", termino.equals("") ? medida.Comportamiento : medida.Comportamiento.replace(termino, busqueda.replace("@termino", termino)))
                                .replace("@Medida", termino.equals("") ? medida.Medida : medida.Medida.replace(termino, busqueda.replace("@termino", termino))) + "@tr");
                    } while (pos < medidas.size());

                    html_plantilla_articulos = html_plantilla_articulos.replace("@Paragrafos", tabla_medidas);
                    html_plantilla_articulos = html_plantilla_articulos.replace("@tr", "");
                }

                html_plantilla_articulos = html_plantilla_articulos
                        .replace("@Paragrafos", "");

                break;
            }

            html_plantilla_articulos = html_plantilla_articulos
                    .replace("@share", html_share);
            webViewArticulo = view.findViewById(R.id.webViewArticulo);
            webViewArticulo.getSettings().setJavaScriptEnabled(true);
            webViewArticulo.loadData(html_plantilla_articulos, "text/html; charset=utf-8", null);
            webViewArticulo.addJavascriptInterface(new WebViewInterface(getActivity()), "Android");
            return view;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
