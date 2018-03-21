package com.policia.codigopolicia.Comparendos;

import android.app.Activity;
import android.content.DialogInterface;
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
import com.policia.negocio.logica.Negocio_DOCUMENTO;
import com.policia.negocio.logica.Negocio_TIPO_DOCUMENTO;
import com.policia.negocio.modelo.Modelo_TIPO_DOCUMENTO;
import com.policia.remote.RemoteExpediente;
import com.policia.remote.response.RNMCGENERAL2Response;
import com.policia.remote.response.RNMCGENERAL2Result;
import com.policia.remote.response.RNMCGENERALResponse;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by 1085253556 on 1/02/2018.
 */

public class FragmentComparendoConsulta extends Fragment implements View.OnClickListener {

    private String dia;
    private String mes;
    private String aio;

    private ArrayList<String> anios;

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
            new Negocio_DOCUMENTO(getContext()).drawAVATAR(Negocio_DOCUMENTO.AVATAR.SCREEN_COMPARENDO,
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
        return fragment;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.buttonAceptar) {

            Modelo_TIPO_DOCUMENTO documento = documentos.get(spinnerTipoDocumento.getSelectedItemPosition());

            if (String.valueOf(documento.ID).equals("55")) {

                verificarExpedicion(documento);
            } else {

                consultarComperandos(documento, "01.01.1900");
            }
        }
    }

    private void verificarExpedicion(final Modelo_TIPO_DOCUMENTO documento) {
        View comparendos = activity.getLayoutInflater().inflate(R.layout.comparendos_fecha_expedicion, null);

        final Spinner spinnerDia = comparendos.findViewById(R.id.spinnerDia);
        final Spinner spinnerMes = comparendos.findViewById(R.id.spinnerMes);
        final Spinner spinnerAio = comparendos.findViewById(R.id.spinnerAio);

        anios = new ArrayList<>();
        int _max_anio = (Calendar.getInstance().get(Calendar.YEAR) - 17);
        int _min_anio = (Calendar.getInstance().get(Calendar.YEAR) - 99);
        for (int i = _min_anio; i < _max_anio; i++)
            anios.add(String.valueOf(i));

        ArrayAdapter adapter = null;
        adapter = new ArrayAdapter<String>(activity.getBaseContext(), android.R.layout.simple_spinner_item, anios);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerAio.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18);

        dia = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        mes = String.valueOf(activity.getResources().getStringArray(R.array.comparendos_calendario_meses)[calendar.get(calendar.get(Calendar.MONTH))]);
        aio = String.valueOf(calendar.get(Calendar.YEAR));

        spinnerDia.setSelection(((ArrayAdapter<String>) spinnerDia.getAdapter()).getPosition(dia));
        spinnerMes.setSelection(((ArrayAdapter<String>) spinnerMes.getAdapter()).getPosition(mes));
        spinnerAio.setSelection(((ArrayAdapter<String>) spinnerAio.getAdapter()).getPosition(aio));

        new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setTitle("Especifique fecha expedición")
                .setMessage("Por seguridad especifique la fecha de expedición del documento")
                .setView(comparendos)
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        try {

                            dia = spinnerDia.getSelectedItem().toString();
                            mes = spinnerMes.getSelectedItem().toString();
                            aio = spinnerAio.getSelectedItem().toString();

                            consultarComperandos(documento, dia + "." + mes + "." + aio);
                            /*
                            RemoteRENEC.newInstance(activity, edittextIdentificacion.getText().toString(),
                                    new RemoteRENEC.IConsultaRENEC() {
                                        @Override
                                        public void consultarExpediente() {

                                            Modelo_TIPO_DOCUMENTO documento = documentos.get(spinnerTipoDocumento.getSelectedItemPosition());

                                            consultarComperandos(documento);
                                        }
                                    }).execute((Void) null);*/
                        } catch (Exception e) {
                        }
                    }
                })
                .show();
    }

    private void consultarComperandos(Modelo_TIPO_DOCUMENTO documento, String Expedicion) {

        ((ComparendosActivity) activity).inflarFragmentoEsperando();

        RemoteExpediente.newInstance(activity, String.valueOf(documento.ID), edittextIdentificacion.getText().toString(), Expedicion, new IComparendoExpediente() {

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

            @Override
            public void consultarCedula(RNMCGENERAL2Response expediente, String TipoDocumento, String Identificacion) {

                if (expediente.rNMCGENERAL2Result.size() == 0) {

                    ((ComparendosActivity) activity).inflarFragmentoConsulta();

                    new AlertDialog.Builder(activity)
                            .setTitle("La Policía Nacional de Colombia hace constar")
                            .setMessage("Que el número de identificación No. " + Identificacion + ", no se encuentra vinculado en el sistema Registro Nacional de Medidas Correctivas RNMC de la Policía Nacional de Colombia como infractor de la Ley 1801 de 2016 Código Nacional de Policía y Convivencia.")
                            .show();
                } else {

                    for (RNMCGENERAL2Result comportamiento : expediente.rNMCGENERAL2Result) {

                        if (comportamiento.fECHAHECHOSRNMCGENERAL.equals("00/00/0000")) {

                            ((ComparendosActivity) activity).inflarFragmentoConsulta();

                            new AlertDialog.Builder(activity)
                                    .setTitle(comportamiento.dESCRIPCIONRNMCGENERAL)
                                    .setMessage("Por favor corrija la fecha de expedición de la cédula")
                                    .show();
                        } else

                            ((ComparendosActivity) activity).inflarFragmentoCedula(expediente);
                    }
                }
            }
        }).execute();
    }
}
