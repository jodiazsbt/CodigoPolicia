package com.policia.codigopolicia;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.policia.codigopolicia.NavegacionCNPC.Fragment_LIBRO;
import com.policia.codigopolicia.NavegacionMULTAS.Fragment_MULTA;
import com.policia.codigopolicia.adapter.Fragment_METEDATA;
import com.policia.codigopolicia.adapter.IActualizarListadoBusqueda;
import com.policia.codigopolicia.idioma.Idioma_Configuracion;
import com.policia.negocio.seguridad.Seguridad;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment fragment;

    Activity activity;
    MenuItem searchItem;
    SearchView searchView;
    NavigationView navigationView;
    DrawerLayout drawer;

    Seguridad sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Seguridad sesion = Seguridad.Sesion(getBaseContext());
            Idioma_Configuracion.updateResources(this, sesion.getIdiomaCodigo());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentView(R.layout.principal_activity);

        activity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        cargaSesion();
        menuCodigoPolicia(navigationView.getMenu().getItem(1));//codigo de policia
    }

    private void cargaSesion() {

        try {
            sesion = Seguridad.Sesion(activity);

            if (!sesion.getUsuario().equals("1")) {

                String funcionario = sesion.getFuncionario();
                Toast.makeText(activity, getResources().getString(R.string.login_welcome) + "\r\n" + funcionario, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cerrarSesionPolicia() {
        try {
            sesion.cerrarSesionPolicia();
            finish();
            startActivity(getIntent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!sesion.getUsuario().equals("1")) {

            TextView textViewFuncionario = navigationView.getHeaderView(0).findViewById(R.id.textViewFuncionario);
            TextView textViewFisica = navigationView.getHeaderView(0).findViewById(R.id.textViewFisica);

            textViewFuncionario.setText(sesion.getFuncionario());
            textViewFisica.setText(sesion.getFisica());
        }
        navigationView.getMenu().findItem(R.id.nav_login).setVisible(sesion.getUsuario().equals("1"));
        navigationView.getMenu().findItem(R.id.nav_cerrar).setVisible(!sesion.getUsuario().equals("1"));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);

        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    busquedaCodigoPolicia();
                } else {
                    menuCodigoPolicia(navigationView.getMenu().getItem(0));//codigo de policia
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (fragment instanceof IActualizarListadoBusqueda) {
                    ((IActualizarListadoBusqueda) fragment).actualizar(query);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (fragment instanceof IActualizarListadoBusqueda) {
                    ((IActualizarListadoBusqueda) fragment).actualizar(newText);
                }

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.nav_language) {
            intent = new Intent(this, IdiomaActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_funcionario) {
            menuIdentificarFuncionario(item);
        } else if (id == R.id.nav_policia) {
            scanPolicia();
        } else if (id == R.id.nav_codigo_policia) {
            menuCodigoPolicia(item);
        } else if (id == R.id.nav_multa) {
            menuMultas(item);
        } else if (id == R.id.nav_polis) {
            menuPOLIS(item);
        } else if (id == R.id.nav_psc) {
            menuPSC(item);
        } else if (id == R.id.nav_cerrar) {
            cerrarSesionPolicia();
        } else if (id == R.id.nav_games) {
            menuJuegos();
        } else if (id == R.id.nav_puntos) {
            menuPuntos(item);
        } else if (id == R.id.nav_login) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void menuPuntos(MenuItem item) {

        Intent intent = new Intent(PrincipalActivity.this, PuntosActivity.class);
        startActivity(intent);
    }

    private void menuIdentificarFuncionario(MenuItem item) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /*
            fragment = new NavegadorApp();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            //item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
            */

            Intent intent = new Intent(PrincipalActivity.this, GenericWebviewActivity.class);
            Bundle b = new Bundle();
            b.putString("url", "https://srvpsi.policia.gov.co/PSC/frm_cnp_consulta.aspx"); //Your id
            intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
        } else {

            Uri uri = Uri.parse("https://srvpsi.policia.gov.co/PSC/frm_cnp_consulta.aspx");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    private void menuJuegos() {

        PackageManager pm = getPackageManager();
        String pn = "com.ponal.cnpcjuegos";
        if (this.isPackageInstalled(pn, pm)) {
            this.openApp(pn);
        } else {
            final String appPackageName = pn; // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }
    }

    private void menuCodigoPolicia(MenuItem item) {

        fragment = new Fragment_LIBRO();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        //item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
    }

    private void busquedaCodigoPolicia() {

        fragment = new Fragment_METEDATA();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    private void menuPSC(MenuItem item) {

        Intent intent = new Intent(this, PortalCiudadano.class);
        startActivity(intent);

        //item.setChecked(true);
        //getSupportActionBar().setTitle(item.getTitle());
    }

    private void menuPOLIS(MenuItem item) {
        //item.setChecked(true);

        PackageManager pm = getPackageManager();
        String pn = "com.policia.polis";
        if (this.isPackageInstalled(pn, pm)) {
            this.openApp(pn);
        } else {
            final String appPackageName = pn; // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }
    }

    private void menuMultas(MenuItem item) {
        fragment = new Fragment_MULTA();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        //item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());

    }

    private boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void openApp(String packageName) {

        startActivity(getPackageManager().getLaunchIntentForPackage(packageName));

    }

    private void scanPolicia() {

        Intent intent = new Intent(this, PoliciaActivity.class);
        startActivity(intent);

    }
}
