/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.policia.codigopolicia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.policia.codigopolicia.IdentificacionPolicia.BarcodeCaptureActivity;
import com.policia.codigopolicia.IdentificacionPolicia.Fragment_Identificacion;
import com.policia.codigopolicia.IdentificacionPolicia.Fragment_Opciones;
import com.policia.codigopolicia.IdentificacionPolicia.IClickScan;

/**
 * Main activity demonstrating how to pass extra parameters to an activity that
 * reads barcodes.
 */
public class PoliciaActivity extends AppCompatActivity {

    private Fragment fragment;
    private final Activity activity = this;

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.policia_activity);

        inflarFragmentOpciones();
    }

    private void inflarFragmentOpciones() {

        fragment = Fragment_Opciones.newInstance(new IClickScan() {
            @Override
            public void ClickScan(View view, boolean autoFocus, boolean useFlash) {

                inflarFragmentIdentificacion(autoFocus, useFlash);
            }
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        getSupportActionBar().setTitle(getResources().getString(R.string.menu_policia));
    }

    private void abrirCamara(boolean autoFocus, boolean useFlash) {

        Intent intent = new Intent(activity, BarcodeCaptureActivity.class);
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus);
        intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash);

        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    private void inflarFragmentIdentificacion(boolean autoFocus, boolean useFlash) {

        fragment = new Fragment_Identificacion();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        getSupportActionBar().setTitle(getResources().getString(R.string.menu_policia));

        abrirCamara(autoFocus, useFlash);
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    //statusMessage.setText(R.string.barcode_success);

                    //inflarFragmentIdentificacion(barcode.displayValue);

                    if (fragment instanceof Fragment_Identificacion) {
                        ((Fragment_Identificacion) fragment).mostrarCarnet(barcode.displayValue);
                    }

                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    //statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                //statusMessage.setText(String.format(getString(R.string.barcode_error),
                //       CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void clickCancelar(View v) {

        finish();
    }

    @Override
    public void onBackPressed() {
        if (fragment instanceof Fragment_Identificacion) {
            inflarFragmentOpciones();
        } else {

            super.onBackPressed();
        }
    }
}