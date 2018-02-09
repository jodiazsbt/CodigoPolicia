package com.policia.codigopolicia.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.policia.codigopolicia.R;
import com.policia.negocio.modelo.Modelo_DOCUMENTO;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 15/01/2018.
 */

public class Capacitacion_Adapter extends BaseAdapter {

    private Activity context;
    private ArrayList<Modelo_DOCUMENTO> documentos;

    public Capacitacion_Adapter(Activity context, ArrayList<Modelo_DOCUMENTO> documentos) {

        this.context = context;
        this.documentos = documentos;
    }

    @Override
    public int getCount() {
        return documentos.size();
    }

    @Override
    public Object getItem(int position) {
        return documentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = context.getLayoutInflater();
        view = inflater.inflate(R.layout.capacitacion_item, null);

        TextView textviewDocumento = view.findViewById(R.id.textviewDocumento);
        //TextView textviewURL = view.findViewById(R.id.textviewURL);

        Modelo_DOCUMENTO documento = documentos.get(position);

        textviewDocumento.setText(documento.Documento);
        //textviewURL.setText(documento.URL);

        if (!(documento.RecursoID == null)) {
            ImageView imageviewTipoArchivo = view.findViewById(R.id.imageviewTipoArchivo);
            imageviewTipoArchivo.setImageDrawable(
                    context.getResources().getDrawable(
                            context.getResources().getIdentifier(
                                    documento.RecursoID,
                                    null,
                                    context.getPackageName())));
        }

        return view;
    }
}
