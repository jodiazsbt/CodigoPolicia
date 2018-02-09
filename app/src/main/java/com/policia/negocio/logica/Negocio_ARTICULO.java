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

    public Negocio_ARTICULO(Context context) throws Exception {

        this.context = context;
        this.sesion = Seguridad.Sesion(context);
        this.rutinasArticulo = new Rutinas_ARTICULO(context);
    }

    public int sincronizar() {

        ArticulosyParagrafosResponse articulos = null;
        try {

            articulos = RemoteClient.connect(context).sincronizarARTICULO(rutinasArticulo.maxFecha());

            for (ArticulosyParagrafosResult result : articulos.articulosyParagrafosResult) {
                Tabla_ARTICULO articulo = new Tabla_ARTICULO();
                articulo.ID = String.valueOf(result.iDArticuloyparagrafo);
                articulo.TITULO_ESP = String.valueOf(result.nombreArticuloyparagrafoESP);
                articulo.ARTICULO_ESP = result.descripcionArticuloyparagrafoESP;
                articulo.VIGENTE = result.vigenteArticulo;
                articulo.NIVEL_ID = String.valueOf(result.iDNivelArticuloyparagrafo);
                articulo.CAPITULO_ID = String.valueOf(result.iDCapituloArticuloyparagrafo);
                articulo.FECHA = String.valueOf(result.fechaArticuloyparagrafo);
                articulo.ARTICULO_ENG = String.valueOf(result.descripcionArticuloyparagrafoENG);
                articulo.TITULO_ENG = String.valueOf(result.nombreArticuloyparagrafoENG);

                if (rutinasArticulo.exists(articulo.ID))
                    rutinasArticulo.update(articulo);
                else
                    rutinasArticulo.create(articulo);
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

        return rutinasArticulo.ArticulosPorCapitulo(sesion.getIdiomaLargo(), Capitulo, position);
    }

    public int CantidadArticulosPorMultaCategoria(String Multa, String Categoria) {

        return rutinasArticulo.CantidadArticulosPorMultaCategoria(Multa, Categoria);
    }

    public ArrayList<Modelo_ARTICULO> ArticulosPorMultaCategoria(String Multa, String Categoria, int position) {

        return rutinasArticulo.ArticulosPorMultaCategoria(sesion.getIdiomaLargo(), Multa, Categoria, position);
    }
}
