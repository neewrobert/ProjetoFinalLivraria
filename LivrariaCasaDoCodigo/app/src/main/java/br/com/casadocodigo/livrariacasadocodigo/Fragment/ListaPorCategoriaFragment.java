package br.com.casadocodigo.livrariacasadocodigo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.casadocodigo.livrariacasadocodigo.R;

/**
 * Created by nrdossantos on 04/11/2017.
 */

public class ListaPorCategoriaFragment extends android.app.Fragment {

    View viewListaPorCategoria;


    public ListaPorCategoriaFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewListaPorCategoria = inflater.inflate(R.layout.lista_por_categoria_layout, container, false);
        return viewListaPorCategoria;
    }
}
