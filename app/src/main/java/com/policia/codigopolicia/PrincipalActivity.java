package com.policia.codigopolicia;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.policia.codigopolicia.NavegacionCNPC.Fragment_LIBRO;
import com.policia.codigopolicia.NavegacionMULTAS.Fragment_MULTA;
import com.policia.codigopolicia.adapter.Fragment_METEDATA;
import com.policia.codigopolicia.adapter.IActualizarListadoBusqueda;
import com.policia.codigopolicia.html.HTML_Plantillas;
import com.policia.codigopolicia.showcase.ToolbarActionItemTarget;
import com.policia.codigopolicia.showcase.ViewTargets;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.remote.RemoteServices;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar toolbar;
    private Fragment fragment;

    private int counter = 0;
    private ShowcaseView showcaseView;

    Activity activity;
    MenuItem searchItem;
    SearchView searchView;
    NavigationView navigationView;
    DrawerLayout drawer;

    Seguridad sesion;

    public NavigationView getNavigation() {
        return navigationView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;
        cargaSesion();
        setContentView(R.layout.principal_activity);

        try {
            sesion = Seguridad.Sesion(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        menuCodigoPolicia(navigationView.getMenu().getItem(2));//codigo de policia

        showcaseView = new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .singleShot(R.layout.principal_activity)
                .setStyle(R.style.CustomShowcaseTheme2)
                .setContentTitle("Bienvenido")
                .setContentText("Por favor lea atentamente los tutoriales de navegación. Policía Nacional de Colombia lo invita a usar esta nueva herramienta de carácter preventivo para ayudar a generar más conciencia y buscar una mejor convivencia ciudadana en el territorio nacional. Conozca sus derechos, deberes y obligaciones de las personas naturales y jurídicas, explorando los libros, títulos y capítulos que componen la ley 1801 de 2016.")
                .setOnClickListener(this)
                .build();
        showcaseView.setButtonText(getResources().getString(R.string.showcaseSiguiente));
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

            new ShowcaseView.Builder(this)
                    .withMaterialShowcase()
                    .singleShot(R.layout.principal_activity)
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .setContentTitle("Bienvenido")
                    .setContentText("Periódicamente se estará agregando a su biblioteca vídeos o documentos de apoyo para su labor como policía. Esta información la podrá encontrar en el menú de capacitación cuando haya iniciado su sesión con las credenciales PSI.")
                    .setOnClickListener(this)
                    .build();
        }
        navigationView.getMenu().findItem(R.id.nav_login).setVisible(sesion.getUsuario().equals("1"));
        navigationView.getMenu().findItem(R.id.nav_cerrar).setVisible(!sesion.getUsuario().equals("1"));

        navigationView.getMenu().findItem(R.id.nav_capacitacion).setVisible(!sesion.getUsuario().equals("1"));
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getResources().getString(R.string.twice_backpressed), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
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
                    menuCodigoPolicia(navigationView.getMenu().getItem(2));//codigo de policia
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
        if (id == R.id.action_search) {
            return true;
        } else if (id == R.id.action_terminos) {
            WebView wv = new WebView(this);
            wv.loadData(new HTML_Plantillas(this, HTML_Plantillas.Plantilla.TERMINOS).getPlantilla(), "text/html", "utf-8");
            new AlertDialog.Builder(this)
                    .setView(wv)
                    .show();
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
            menuIdioma(item);
        } else if (id == R.id.nav_actualizar) {
            RemoteServices.newInstance(this).execute();
        } else if (id == R.id.nav_funcionario) {
            menuConsultaComparendo(item);
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
            menuAutoridades(item);
        } else if (id == R.id.nav_login) {
            menuLogin(item);
        } else if (id == R.id.nav_capacitacion) {
            menuCapacitacion(item);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == 0) {
                if (resultCode == RESULT_OK) {
                    String contents = data.getStringExtra("SCAN_RESULT");
                    String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                    Log.d("SCAN", format);
                    Log.d("SCAN", contents);
                    // Handle successful scan
                } else if (resultCode == RESULT_CANCELED) {
                    // Handle cancel
                }
            } else if (requestCode == 1) {
                if (resultCode == Activity.RESULT_OK) {
                    finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void menuIdioma(MenuItem item) {
        Intent intent = new Intent(this, IdiomaActivity.class);
        startActivityForResult(intent, 1);
    }

    private void menuLogin(MenuItem item) {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void menuCapacitacion(MenuItem item) {

        Intent intent = new Intent(this, CapacitacionActivity.class);
        startActivity(intent);
    }

    private void menuAutoridades(MenuItem item) {

        Intent intent = new Intent(PrincipalActivity.this, AutoridadesActivity.class);
        startActivity(intent);
    }

    private void menuConsultaComparendo(MenuItem item) {

        Intent intent = new Intent(PrincipalActivity.this, ComparendosActivity.class);
        startActivity(intent);
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

        getSupportActionBar().setTitle(item.getTitle());
    }

    private void busquedaCodigoPolicia() {

        fragment = new Fragment_METEDATA();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    private void menuPSC(MenuItem item) {

        Intent intent = new Intent(this, PortalActivity.class);
        startActivity(intent);
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

    @Override
    public void onClick(View view) {
        switch (counter) {
            case 0:
                showcaseView.setContentTitle("Convivencia");
                showcaseView.setContentText("Es la interacción pacífica, respetuosa y armónica entre las personas, con los bienes y con el ambiente, en el marco del ordenamiento jurídico.");
                break;
            case 1:
                showcaseView.setContentTitle("Deberes de convivencia");
                showcaseView.setContentText("Es deber de todas las personas en el territorio nacional comportase de manera favorable a la convivencia. Para ello, además de evitar actividades o comportamientos contrarios a la misma, se deben autorregular el ejercicio de derechos y libertades, para no transgredir los de los demás.");
                break;
            case 2:
                showcaseView.setContentTitle("Aplicaciones preinstaladas");
                showcaseView.setContentText("Para una experiencia de navegación completa se sugiere tener instaladas la suite de JUEGOS CNPC y las aplicaciónes Google MAPS, teclado de Google (Gboard), POLIS y !A Denunciar¡");
                break;
            case 3:

                ToolbarActionItemTarget menuTarget = new ToolbarActionItemTarget(toolbar, R.id.action_search);
                showcaseView.setTarget(menuTarget);
                showcaseView.setContentTitle("Búsquedas por voz y texto");
                showcaseView.setContentText("Utilice este botón cuando quiera realizar una búsqueda tanto por texto como por voz relacionada con el nuevo Código Nacional de Policía y Convivencia. Recomendamos instalar el teclado de google para realizar búsquedas por voz.");
                break;
            case 4:

                ViewTarget toolbarTarget = null;
                try {
                    toolbarTarget = ViewTargets.navigationButtonViewTarget(toolbar);

                    showcaseView.setTarget(toolbarTarget);
                    showcaseView.setContentTitle("Menú principal");
                    showcaseView.setContentText("Además de dar a conocer el nuevo Código Nacional de Policía y Convivencia esta aplicación contiene varias utilidades como poder cambiar el idioma (español e inglés), identificar el policía, consultar sus antecedentes disciplinarios, medidas correctivas ó para dirigirse a las autoridades de policía más cercanas entre otras.");
                    showcaseView.setButtonText(getResources().getString(R.string.showcaseEntendido));
                } catch (ViewTargets.MissingViewException e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                showcaseView.hide();
                break;
        }
        counter++;
    }
}
