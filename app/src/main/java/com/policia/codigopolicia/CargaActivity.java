package com.policia.codigopolicia;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.policia.negocio.logica.Negocio_DOCUMENTO;
import com.policia.remote.RemoteENCUESTA;

/**
* CargaActivity:
* Esta actividad es la encargada de iniciar la aplicacion
* */
public class CargaActivity extends Activity {

    private Negocio_DOCUMENTO negocioDocumento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carga_activity);

        try {

            RemoteENCUESTA.newInstance(this).execute();

            negocioDocumento = new Negocio_DOCUMENTO(this);
            negocioDocumento.drawAVATAR(Negocio_DOCUMENTO.AVATAR.SCREEN_SPLASH
                    , (ImageView) findViewById(R.id.imageViewCaricatura));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
