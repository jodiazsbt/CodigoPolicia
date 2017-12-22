package com.policia.codigopolicia.IdentificacionPolicia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.policia.codigopolicia.R;
import com.policia.codigopolicia.parser.DocumentoPolicia;

import org.json.JSONException;
import org.json.XML;

/**
 * Created by 1085253556 on 21/12/2017.
 */

public class Fragment_Identificacion extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.policia_fragment_identificacion, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void mostrarCarnet(String barcode) {

        DocumentoPolicia document = null;
        try {
            document = new Gson().fromJson(String.valueOf(XML.toJSONObject(barcode)), DocumentoPolicia.class);

            String[] values = new String[]{
                    String.valueOf(document.getDocumentElement().getTable().getE2()),//CEDULA
                    String.valueOf(document.getDocumentElement().getTable().getE4()),//APELLIDO
                    String.valueOf(document.getDocumentElement().getTable().getE5()),//NOMBRE
                    String.valueOf(document.getDocumentElement().getTable().getE11()),//GRADO
                    String.valueOf(document.getDocumentElement().getTable().getE15())//CARNET
            };

            ListView listviewIdentificacion = getView().findViewById(R.id.listviewIdentificacion);
            listviewIdentificacion.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, values));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
