package com.policia.codigopolicia;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.policia.remote.RemoteGEO;
import com.policia.remote.request.RequestGEO;

public class AutoridadesActivity extends AppCompatActivity {

    private final Activity activity = this;

    private ListView listView;
    private WebView webviewMap;

    private String tipoCompetencia;
    private static final String TAG = AutoridadesActivity.class.getSimpleName();

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private static final String KEY_LOCATION = "location";
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private boolean mLocationPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autoridades_activity);

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }


        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        webviewMap = findViewById(R.id.webviewMap);
        listView = findViewById(R.id.listviewAutoridades);

        Bundle bundle = getIntent().getExtras();

        if (bundle == null)
            tipoCompetencia = "001";
        else
            tipoCompetencia = getIntent().getExtras().getString("tipo_competencia", "001");
        /*
        webviewMap = findViewById(R.id.webviewMap);
        webviewMap.loadUrl("https://policia.maps.arcgis.com/apps/webappviewer/index.html?id=6016fa41f3a64c3d9ffcee984626dd62&amp;center=4.646748,-74.097120,,,,&amp;level=8");
        webviewMap.getSettings().setJavaScriptEnabled(true);
        webviewMap.setWebChromeClient(new WebChromeClient());*/

        // Prompt the user for permission.
        getLocationPermission();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .singleShot(R.layout.autoridades_activity)
                .setStyle(R.style.CustomShowcaseTheme2)
                .setContentTitle("Autoridades competentes")
                .setContentText("Consulte el listado de autoridades competentes que estén a un radio de distancia de su ubicación.")
                .build();
    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
        super.onSaveInstanceState(outState);
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation == null) {
                                Log.d(TAG, "No se puede conocer la última ubicación");
                                Toast.makeText(AutoridadesActivity.this, "No se puede conocer la última ubicación", Toast.LENGTH_SHORT).show();
                            } else {
                                /*
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(mLastKnownLocation.getLatitude(),
                                                mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));*/

                                webviewMap.loadUrl("https://policia.maps.arcgis.com/apps/webappviewer/index.html?id=6016fa41f3a64c3d9ffcee984626dd62&marker=" + mLastKnownLocation.getLongitude() + "," + mLastKnownLocation.getLatitude() + ",,,,&level=10");
                                webviewMap.getSettings().setJavaScriptEnabled(true);
                                webviewMap.setWebViewClient(new WebViewClient() {

                                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                                        view.loadUrl(request.getUrl().toString());
                                        return super.shouldOverrideUrlLoading(view, request);
                                    }
                                });

                                RequestGEO geo = new RequestGEO();
                                geo.Latitud = String.valueOf(mLastKnownLocation.getLatitude()).replace(".", ",");
                                geo.Longitud = String.valueOf(mLastKnownLocation.getLongitude()).replace(".", ",");
                                geo.Tipo = tipoCompetencia;//Buscar todos los sitios que se puedan encontrar

                                RemoteGEO.newInstance(activity, listView, webviewMap).execute(geo);
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            /*
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);*/
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

}
