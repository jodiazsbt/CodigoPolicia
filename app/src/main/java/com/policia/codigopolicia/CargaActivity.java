package com.policia.codigopolicia;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.policia.negocio.logica.Negocio_DOCUMENTO;
import com.policia.remote.RemoteENCUESTA;

public class CargaActivity extends Activity {
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    private Negocio_DOCUMENTO negocioDocumento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carga_activity);
        /*

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //new RestGetUpdates(getApplicationContext()).execute("LIBROS");

                Intent mainIntent = new Intent(CargaActivity.this, PrincipalActivity.class);
                CargaActivity.this.startActivity(mainIntent);
                CargaActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

         */

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
