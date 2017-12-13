package com.policia.codigopolicia.NavegacionMULTAS;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.policia.negocio.modelo.Modelo_CAPITULO_MULTA;
import com.policia.negocio.modelo.Modelo_TITULO_MULTA;
import com.policia.negocio.modelo.ValuePar;

import java.util.ArrayList;

/**
 * Created by 1085253556 on 29/11/2017.
 */

public class Detalle_CAPITULO {

    private Activity activity;
    private Modelo_TITULO_MULTA titulo;
    private ArrayList<ValuePar> detalles;
    private ArrayList<Modelo_CAPITULO_MULTA> capitulos;

    public Detalle_CAPITULO(Fragment context, Modelo_TITULO_MULTA titulo, ArrayList<Modelo_CAPITULO_MULTA> capitulos) {

        activity = context.getActivity();

        this.titulo = titulo;
        this.capitulos = capitulos;
        detalles = new ArrayList<ValuePar>();
        if (capitulos != null) {
            for (Modelo_CAPITULO_MULTA capitulo : capitulos) {
                detalles.add(new ValuePar(capitulo.Nivel, capitulo.Capitulo));
            }
        }
    }

    public ArrayList<Modelo_CAPITULO_MULTA> getCapitulos() {
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
