package com.policia.codigopolicia.NavegacionCNPC;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.SpannableDocumentLayout;
import com.bluejamesbond.text.hyphen.SqueezeHyphenator;
import com.bluejamesbond.text.style.TextAlignment;
import com.bluejamesbond.text.style.TextAlignmentSpan;
import com.policia.codigopolicia.IdiomaActivity;
import com.policia.codigopolicia.R;
import com.policia.codigopolicia.html.HTML_Plantillas;
import com.policia.negocio.logica.Negocio_ARTICULO;
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

    private WebView webViewArticulo;

    private int position;
    private String capitulo;

    public static CNPC_Fragment newInstance(int position, String capitulo) {
        CNPC_Fragment _cnpcFragment = new CNPC_Fragment();
        Bundle args = new Bundle();
        args.putString("capitulo", capitulo);
        args.putInt("position", position);
        _cnpcFragment.setArguments(args);

        _cnpcFragment.position = position;
        _cnpcFragment.capitulo = capitulo;

        return _cnpcFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("position", 0);
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

            ArrayList<Modelo_ARTICULO> articulos = null;
            articulos = negocioArticulo.ArticulosPorCapitulo(capitulo, position + 1);

            String html_articulo_capitulo = new HTML_Plantillas(getActivity(), HTML_Plantillas.Plantilla.ARTICULO_CAPITULO).getPlantilla();

            for (Modelo_ARTICULO articulo : articulos) {

                html_articulo_capitulo = html_articulo_capitulo.replace("@Nivel", articulo.Nivel);
                html_articulo_capitulo = html_articulo_capitulo.replace("@Titulo", articulo.Titulo);
                html_articulo_capitulo = html_articulo_capitulo.replace("@Capitulo_Nivel", articulo.Capitulo_Nivel);
                html_articulo_capitulo = html_articulo_capitulo.replace("@Capitulo_Descripcion", articulo.Capitulo_Descripcion);
                html_articulo_capitulo = html_articulo_capitulo.replace("@Articulo_Nivel", articulo.Articulo_Nivel);
                html_articulo_capitulo = html_articulo_capitulo.replace("@Articulo_Titulo", articulo.Articulo_Titulo);
                html_articulo_capitulo = html_articulo_capitulo.replace("@Articulo_Descripcion", articulo.Articulo_Descripcion);

                ArrayList<Modelo_NUMERAL> numerales = negocioNumeral.NumeralesPorArticulo(articulo.ID);

                String parrafo_numeral = "<p align='justify'>@Nivel &#09; @Numeral</p>";
                if (!(numerales.size() == 0)) {

                    int pos = 0;
                    do {
                        Modelo_NUMERAL numeral = numerales.get(pos++);
                        html_articulo_capitulo = html_articulo_capitulo.replace("@Numerales", parrafo_numeral.replace("@Nivel", numeral.Nivel).replace("@Numeral", numeral.Numeral) + "@Numerales");
                    } while (pos < numerales.size());
                }

                html_articulo_capitulo = html_articulo_capitulo.replace("@Numerales", "");

                ArrayList<Modelo_MEDIDA> medidas = negocioMedida.MedidasPorParagrafo(articulo.ID);

                String filas_medidas = "<tr><td>@Nivel @Comportamiento</td><td style='text-align:justify'>@Medida</td></tr>";
                String tabla_medidas = "<table><thead><tr><th>COMPORTAMIENTOS</th><th>MEDIDAS CORRECTIVAS</th></tr></thead><tbody>@tr</tbody></table>";
                if (!(medidas.size() == 0)) {

                    int pos = 0;
                    do {
                        Modelo_MEDIDA medida = medidas.get(pos++);
                        tabla_medidas = tabla_medidas.replace("@tr", filas_medidas.replace("@Nivel", medida.Nivel).replace("@Comportamiento", medida.Comportamiento).replace("@Medida", medida.Medida) + "@tr");
                    } while (pos < medidas.size());

                    html_articulo_capitulo = html_articulo_capitulo.replace("@Paragrafos", tabla_medidas);
                    html_articulo_capitulo = html_articulo_capitulo.replace("@tr", "");
                }

                html_articulo_capitulo = html_articulo_capitulo.replace("@Paragrafos", "");

                break;
            }
            webViewArticulo = view.findViewById(R.id.webViewArticulo);
            webViewArticulo.loadData(html_articulo_capitulo, "text/html; charset=utf-8", null);
            return view;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
