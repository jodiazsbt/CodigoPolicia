package com.policia.codigopolicia.Comparendos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.policia.codigopolicia.ComparendosActivity;
import com.policia.codigopolicia.R;
import com.policia.negocio.logica.Negocio_AVATAR;
import com.policia.negocio.logica.Negocio_TIPO_DOCUMENTO;
import com.policia.negocio.modelo.Modelo_TIPO_DOCUMENTO;
import com.policia.remote.RemoteExpediente;
import com.policia.remote.response.RNMCGENERALResponse;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 1/02/2018.
 */

public class FragmentComparendoConsulta extends Fragment implements View.OnClickListener {

    private Activity activity;

    private Spinner spinnerTipoDocumento;
    private EditText edittextIdentificacion;
    private Button buttonAceptar;

    private ArrayList<Modelo_TIPO_DOCUMENTO> documentos;

    public static FragmentComparendoConsulta newInstance(Activity activity) {

        Bundle args = new Bundle();

        FragmentComparendoConsulta fragment = new FragmentComparendoConsulta();
        fragment.setArguments(args);
        fragment.activity = activity;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.comparendos_consulta, container, false);

        try {
            new Negocio_AVATAR(getContext()).drawAVATAR(Negocio_AVATAR.AVATAR.SCREEN_COMPARENDO,
                    (ImageView) fragment.findViewById(R.id.imageViewCaricatura));

            edittextIdentificacion = fragment.findViewById(R.id.edittextIdentificacion);
            spinnerTipoDocumento = fragment.findViewById(R.id.spinnerTipoDocumento);

            buttonAceptar = fragment.findViewById(R.id.buttonAceptar);
            buttonAceptar.setOnClickListener(this);

            ArrayAdapter adapter = null;
            documentos = new Negocio_TIPO_DOCUMENTO(activity).TiposDocumento();
            adapter = new ArrayAdapter<Modelo_TIPO_DOCUMENTO>(activity.getBaseContext(), android.R.layout.simple_spinner_item, documentos);

            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            spinnerTipoDocumento.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        RemoteDocumentos.newInstance(activity, new IComparendoConsulta() {

            private ArrayAdapter adapter;

            @Override
            public void consultaTipoDocumento(RNMCTIPOSDOCResponse response) {
                documentos = response;
                if (documentos == null) {
                    activity.finish();
                    Toast.makeText(activity, "Se necesita estar conectado a internet para realizar esta consulta", Toast.LENGTH_SHORT).show();
                } else {

                    adapter = new ArrayAdapter<RNMCTIPOSDOCResult>(activity.getBaseContext(), android.R.layout.simple_spinner_item, response.rNMCTIPOSDOCResult);
                    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                    spinnerTipoDocumento.setAdapter(adapter);
                }
            }
        }).execute((Void) null);*/
        return fragment;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.buttonAceptar) {

            ((ComparendosActivity) activity).inflarFragmentoEsperando();

            Modelo_TIPO_DOCUMENTO documento = documentos.get(spinnerTipoDocumento.getSelectedItemPosition());

            RemoteExpediente.newInstance(activity, String.valueOf(documento.ID), edittextIdentificacion.getText().toString(), new IComparendoExpediente() {

                @Override
                public void consultar(RNMCGENERALResponse expediente, String TipoDocumento, String Identificacion) {

                    if (expediente.rNMCGENERALResult.size() == 0) {

                        ((ComparendosActivity) activity).inflarFragmentoConsulta();

                        new AlertDialog.Builder(activity)
                                .setTitle("La Policía Nacional de Colombia hace constar")
                                .setMessage("Que el número de identificación No. " + Identificacion + ", no se encuentra vinculado en el sistema Registro Nacional de Medidas Correctivas RNMC de la Policía Nacional de Colombia como infractor de la Ley 1801 de 2016 Código Nacional de Policía y Convivencia.")
                                .show();
                    } else {

                        ((ComparendosActivity) activity).inflarFragmentoExpediente(expediente);
                    }
                }
            }).execute();
        }
    }
}
