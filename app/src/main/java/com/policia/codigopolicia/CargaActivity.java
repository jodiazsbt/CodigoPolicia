package com.policia.codigopolicia;

import android.app.Activity;
import android.os.Bundle;

import com.policia.remote.RemoteENCUESTA;

public class CargaActivity extends Activity {
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

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
        RemoteENCUESTA.newInstance(this).execute();
    }
}
