package com.policia.codigopolicia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class CargaActivity extends Activity {
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carga_activity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(CargaActivity.this, PrincipalActivity.class);
                CargaActivity.this.startActivity(mainIntent);
                CargaActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
