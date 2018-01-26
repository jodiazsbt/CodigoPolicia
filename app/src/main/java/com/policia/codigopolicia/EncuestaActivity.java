package com.policia.codigopolicia;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.policia.negocio.logica.Negocio_ENCUESTA;
import com.policia.negocio.modelo.Modelo_ENCUESTA;
import com.policia.remote.RemoteRESPUESTA;
import com.policia.remote.request.RequestRESPUESTA;

public class EncuestaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonCierto;
    private Modelo_ENCUESTA encuesta;
    private Negocio_ENCUESTA negocioEncuesta;

    private final Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encuesta_activity);

        try {
            negocioEncuesta = new Negocio_ENCUESTA(getBaseContext());
            encuesta = negocioEncuesta.ultimaEncuesta();
            TextView textViewPregunta = findViewById(R.id.textViewPregunta);
            textViewPregunta.setText(encuesta.Pregunta);

            buttonCierto = findViewById(R.id.buttonAceptar);
            buttonCierto.setOnClickListener(this);

            new ShowcaseView.Builder(this)
                    .withMaterialShowcase()
                    .singleShot(R.layout.encuesta_activity)
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .setContentTitle("Encuesta")
                    .setContentText("Lanzamos encuestas de una sola pregunta periódicamente como parte de nuestro plan de mejoramiento continuo. Estas encuestas son totalmente anónimas y usted tiene la libertad de responderla o no responderla; para más información consulte nuestra política de privacidad.")
                    .build()
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void finish() {
        super.finish();

        //RemoteServices.newInstance(this).execute();
    }

    @Override
    public void onClick(View view) {
        final String[] departamentos = getResources().getStringArray(R.array.listaDepartamentos);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Indíquenos su departamento");
        builder.setItems(departamentos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new RemoteRESPUESTA(activity, new RequestRESPUESTA(
                        encuesta.ID,
                        departamentos[which],
                        "1"
                )).execute();
            }
        });
        builder.show();
    }

    public void clickCancelar(View view) {
        final String[] departamentos = getResources().getStringArray(R.array.listaDepartamentos);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Indíquenos su departamento");
        builder.setItems(departamentos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new RemoteRESPUESTA(activity, new RequestRESPUESTA(
                        encuesta.ID,
                        departamentos[which],
                        "0"
                )).execute();
            }
        });
        builder.show();
    }
}