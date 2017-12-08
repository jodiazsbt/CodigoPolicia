package com.policia.codigopolicia.NavegacionCNPC;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.policia.negocio.modelo.Modelo_CAPITULO;
import com.policia.negocio.modelo.Modelo_TITULO;
import com.policia.negocio.modelo.ValuePar;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 29/11/2017.
 */

public class Detalle_CAPITULO {

    private Activity activity;
    private Modelo_TITULO titulo;
    private ArrayList<ValuePar> detalles;
    private ArrayList<Modelo_CAPITULO> capitulos;

    public Detalle_CAPITULO(Fragment context, Modelo_TITULO titulo, ArrayList<Modelo_CAPITULO> capitulos) {

        activity = context.getActivity();

        this.titulo = titulo;
        this.capitulos = capitulos;
        detalles = new ArrayList<ValuePar>();
        if (capitulos != null) {
            for (Modelo_CAPITULO capitulo : capitulos) {
                detalles.add(new ValuePar(capitulo.Nivel, capitulo.Capitulo));
            }
        }
    }

    public ArrayList<Modelo_CAPITULO> getCapitulos() {
        return capitulos;
    }

    public String getTitulo() {
        return titulo.Titulo;
    }

    public String getNivel() {
        return titulo.Nivel;
    }

    public int getCount() {
        return detalles.size();
    }

    public ValuePar getItem(int index) {
        return detalles.get(index);
    }
}
