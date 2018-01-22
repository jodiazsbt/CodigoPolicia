package com.policia.codigopolicia;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.policia.remote.RemoteGEO;
import com.policia.remote.request.RequestGEO;

public class ArcGISActivity extends Activity {

    private MapView mMapView;

    private final Activity activity = this;

    private static final String TAG = ArcGISActivity.class.getSimpleName();

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    private ListView listView;

    private String tipoCompetencia;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 10;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private ArcGISMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Retrieve the content view that renders the map.
        setContentView(R.layout.puntos_activity);

        mMapView = (MapView) findViewById(R.id.map);
        map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, mDefaultLocation.latitude, mDefaultLocation.longitude, DEFAULT_ZOOM);
        mMapView.setMap(map);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

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
                                Toast.makeText(ArcGISActivity.this, "No se puede conocer la última ubicación", Toast.LENGTH_SHORT).show();
                            } else {
                                mMapView.setMap(new ArcGISMap(Basemap.Type.TOPOGRAPHIC,
                                        mLastKnownLocation.getLatitude(),
                                        mLastKnownLocation.getLongitude(), DEFAULT_ZOOM));

                                RequestGEO geo = new RequestGEO();
                                geo.Latitud = String.valueOf(mLastKnownLocation.getLatitude()).replace(".", ",");
                                geo.Longitud = String.valueOf(mLastKnownLocation.getLongitude()).replace(".", ",");
                                geo.Tipo = tipoCompetencia;//Buscar todos los sitios que se puedan encontrar

                                //new RemoteGEO(activity, listView, mMapView).execute(geo);
                            }
                        } else {
                            /*
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
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

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        mMapView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.resume();
    }

}
