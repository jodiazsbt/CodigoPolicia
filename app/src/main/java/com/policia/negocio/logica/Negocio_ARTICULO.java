package com.policia.negocio.logica;

import android.content.Context;

import com.policia.negocio.modelo.Modelo_ARTICULO;
import com.policia.negocio.seguridad.Seguridad;
import com.policia.persistencia.rutinas.Rutinas_ARTICULO;
import com.policia.persistencia.tablas.Tabla_ARTICULO;
import com.policia.remote.RemoteClient;
import com.policia.remote.response.ArticulosyParagrafosResponse;
import com.policia.remote.response.ArticulosyParagrafosResult;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 30/11/2017.
 */

public class Negocio_ARTICULO {

    private Rutinas_ARTICULO rutinasArticulo;
    private Seguridad sesion;

    private Context context;
    private RemoteClient remoteClient;

    public Negocio_ARTICULO(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasArticulo = new Rutinas_ARTICULO(context);
    }

    public int sincronizar() {

        remoteClient = new RemoteClient(context);
        ArticulosyParagrafosResponse articulos = null;
        try {

            articulos = remoteClient.sincronizarARTICULO(rutinasArticulo.maxFecha());

            for (ArticulosyParagrafosResult articulo : articulos.articulosyParagrafosResult) {
                Tabla_ARTICULO tablaArticulo = new Tabla_ARTICULO();
                tablaArticulo.ID = String.valueOf(articulo.iDArticuloyparagrafo);
                tablaArticulo.TITULO_ESP = String.valueOf(articulo.nombreArticuloyparagrafoESP);
                tablaArticulo.ARTICULO_ESP = articulo.descripcionArticuloyparagrafoESP;
                tablaArticulo.VIGENTE = articulo.vigenteArticulo;
                tablaArticulo.NIVEL_ID = String.valueOf(articulo.iDNivelArticuloyparagrafo);
                tablaArticulo.CAPITULO_ID = String.valueOf(articulo.iDCapituloArticuloyparagrafo);
                tablaArticulo.FECHA = String.valueOf(articulo.fechaArticuloyparagrafo);
                tablaArticulo.ARTICULO_ENG = String.valueOf(articulo.descripcionArticuloyparagrafoENG);
                tablaArticulo.TITULO_ENG = String.valueOf(articulo.nombreArticuloyparagrafoENG);
                rutinasArticulo.update(tablaArticulo);
            }
            return articulos.articulosyParagrafosResult.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int CantidadArticulosPorCapitulo(String Capitulo) {

        return rutinasArticulo.CantidadArticulosPorCapitulo(Capitulo);
    }

    public ArrayList<Modelo_ARTICULO> ArticulosPorCapitulo(String Capitulo, int position) {

        return rutinasArticulo.ArticulosPorCapitulo(sesion.getIdiomaCodigo(), Capitulo, position);
    }

    public int CantidadArticulosPorMultaCategoria(String Multa, String Categoria) {

        return rutinasArticulo.CantidadArticulosPorMultaCategoria(Multa, Categoria);
    }

    public ArrayList<Modelo_ARTICULO> ArticulosPorMultaCategoria(String Multa, String Categoria, int position) {

        return rutinasArticulo.ArticulosPorMultaCategoria(sesion.getIdiomaCodigo(), Multa, Categoria, position);
    }
}
