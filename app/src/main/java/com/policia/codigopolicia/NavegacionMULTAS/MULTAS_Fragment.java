package com.policia.codigopolicia.NavegacionMULTAS;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.policia.codigopolicia.R;
import com.policia.negocio.logica.Negocio_ARTICULO;
import com.policia.negocio.modelo.Modelo_ARTICULO;
import com.policia.negocio.modelo.Modelo_ARTICULO_MULTA;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 1/12/2017.
 */

public class MULTAS_Fragment extends Fragment {

    private ListView listViewArticulo;
    private Negocio_ARTICULO negocioArticulo;

    private TextView textViewNIVEL;
    private TextView textViewTITULO;
    private TextView textViewCAPITULONIVEL;
    private TextView textViewCAPITULODESCRIPCION;
    private TextView textViewARTICULO;
    private TextView textViewARTICULOTITULO;
    private DocumentView documentViewARTICULODESCRIPCION;

    private int position;
    private String multa;
    private String categoria;

    public static MULTAS_Fragment newInstance(int position, String multa, String categoria) {
        MULTAS_Fragment _cnpcFragment = new MULTAS_Fragment();
        Bundle args = new Bundle();
        args.putString("multa", multa);
        args.putString("categoria", categoria);
        args.putInt("position", position);
        _cnpcFragment.setArguments(args);

        _cnpcFragment.position = position;
        _cnpcFragment.multa = multa;
        _cnpcFragment.categoria = categoria;

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

        ArrayList<String> items = new ArrayList<String>();
        try {
            negocioArticulo = new Negocio_ARTICULO(context);

            ArrayList<Modelo_ARTICULO> articulos = null;
            articulos = negocioArticulo.ArticulosPorMultaCategoria(multa, categoria, position + 1);

            for (Modelo_ARTICULO articulo : articulos) {

                items.add(articulo.ID);
                items.add(articulo.Nivel);
                items.add(articulo.Titulo);
                items.add(articulo.Capitulo_Nivel);
                items.add(articulo.Capitulo_Descripcion);
                items.add(articulo.Articulo_Nivel);
                items.add(articulo.Articulo_Titulo);
                items.add(articulo.Articulo_Descripcion);

                break;
            }

            textViewNIVEL = view.findViewById(R.id.textViewNIVEL);
            textViewTITULO = view.findViewById(R.id.textViewTITULO);
            textViewCAPITULONIVEL = view.findViewById(R.id.textViewCAPITULONIVEL);
            textViewCAPITULODESCRIPCION = view.findViewById(R.id.textViewCAPITULODESCRIPCION);
            textViewARTICULO = view.findViewById(R.id.textViewARTICULO);
            textViewARTICULOTITULO = view.findViewById(R.id.textViewARTICULOTITULO);
            documentViewARTICULODESCRIPCION = view.findViewById(R.id.documentViewARTICULODESCRIPCION);

            textViewNIVEL.setText(items.get(1));
            textViewTITULO.setText(items.get(2));
            textViewCAPITULONIVEL.setText(items.get(3));
            textViewCAPITULODESCRIPCION.setText(items.get(4));
            textViewARTICULO.setText(items.get(5));
            textViewARTICULOTITULO.setText(items.get(6));
            documentViewARTICULODESCRIPCION.setText(items.get(7));

            return view;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
